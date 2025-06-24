package iqness.service;

import iqness.config.AwsS3BucketConfig;
import iqness.dao.AssetsAllocationsDao;
import iqness.dao.EmployeeDao;
import iqness.exception.EmployeeExistsException;
import iqness.exception.EmployeeNotFoundException;
import iqness.exception.ImageExistsException;
import iqness.mapper.AssetsMapper;
import iqness.mapper.EmployeeMapper;
import iqness.model.Assets;
import iqness.model.AssetsAllocations;
import iqness.model.Employee;
import iqness.request.EmployeeCreateRequest;
import iqness.vo.AssetsVO;
import iqness.vo.EmployeeVO;
import iqness.vo.PaginatedResponseVOAndCount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@org.springframework.stereotype.Service
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private final AwsS3BucketConfig awsS3BucketConfig;

    private final StorageService storageService;


    @Autowired
    private AssetsAllocationsDao assetsAllocationsDao;

    private String getEmpId()
    {
        int count=employeeDao.getEmployeeCount();
        return ""+count;
    }
  public EmployeeVO createEmployee(EmployeeCreateRequest request, MultipartFile image) throws EmployeeExistsException, ParseException, IOException, ImageExistsException {
      try {
          Employee existingEmployee = employeeDao.getEmployeeByName(request.getEmployeeName());
          if (existingEmployee != null) {
              throw new EmployeeExistsException(request.getEmployeeName());
          }
          String empId=getEmpId();
          Employee employeeByFile=employeeDao.getEmployeeByFile(image.getOriginalFilename());
          if(employeeByFile != null)
          {
              throw new ImageExistsException(image.getOriginalFilename());
          }
          storageService.uploadToStorage(image, image.getOriginalFilename());
          Employee employee= EmployeeMapper.createEmployee(request,empId,image.getOriginalFilename());
          Employee savedEmployee = employeeDao.saveEmployee(employee);
          log.info("Create Employee successfully, Request: {}", request);
          return EmployeeMapper.createEmployeeVOWithOutUrl(savedEmployee);
      }
      catch (Exception e)
      {
          log.error("Error while creating Employee,  Request: {}", request);
          throw e;
      }
  }

  public String  getUrls(String  filePath)
  {
      var expiryTime = System.currentTimeMillis() + 1000 * 60 * 60;
      var url = storageService.createLink(filePath, expiryTime);
      return url.toString();
  }
    public PaginatedResponseVOAndCount<EmployeeVO> listEmployee(Integer offset, Integer limit, String search,String filter) {
        try {
            Pageable pageable = PageRequest.of(offset - 1, limit);
            Page<Employee> employeeList;
            if (search == null || search.trim() == "") {
                employeeList = employeeDao.listEmployee(pageable);
            } else {
                if(filter.equals("Name"))
                    employeeList = employeeDao.getEmployeeListByName(search.toUpperCase(), pageable);
                else if(filter.equals("Email"))
                    employeeList = employeeDao.getEmployeeListByEmail(search.toUpperCase(), pageable);
                else
                    employeeList = employeeDao.getEmployeeListByNameAndEmail(search.toUpperCase(), pageable);
            }
            log.info("Retrieved employee list successfully");
            var employeeVOList = EmployeeMapper.createListOfEmployeeVO(employeeList.toList());
            return new PaginatedResponseVOAndCount<>(
                    employeeList.getTotalElements(), employeeVOList);
        } catch (Exception e) {
            log.error("Error while Retrieved employee list");
            throw e;
        }
    }


    public void deleteEmployee(UUID employeeId) throws EmployeeNotFoundException {
        try {

            Employee existingEmployee = employeeDao.getEmployeeById(employeeId);
            if (existingEmployee == null) {
                throw new EmployeeNotFoundException(employeeId);

            }
            employeeDao.deleteEmployeeById(existingEmployee.getId());
            log.info(
                    "Deleted employee successfully, employeeId: {}", employeeId);
        } catch (Exception e) {
            log.error(
                    "Error while deleting employee,employeeId: {} ", employeeId);
            throw e;
        }
    }


    public EmployeeVO getEmployeeById(UUID employeeId) throws EmployeeNotFoundException {
        try {
            Employee existingEmployee = employeeDao.getEmployeeById(employeeId);
            if (existingEmployee == null) {
                throw new EmployeeNotFoundException(employeeId);
            }
            log.info("Retrieved employee details successfully, employeeId: {}", employeeId);
            String fileUrl=getUrls(existingEmployee.getPhotoPath());
            List<AssetsAllocations> assetsAllocations=assetsAllocationsDao.getAssetsAllocationsByEmployeeId(existingEmployee.getId());
            if(assetsAllocations==null)
            return EmployeeMapper.createEmployeeVO(existingEmployee,fileUrl);
            else
            return EmployeeMapper.createEmployeeVO(existingEmployee,fileUrl,assetsAllocations);
        } catch (Exception e) {
            log.error("Error while fetching employee details,employeeId: {}", employeeId);
            throw e;
        }
    }


    public EmployeeVO updateEmployee(EmployeeCreateRequest request, UUID employee) throws EmployeeNotFoundException, EmployeeExistsException, ParseException {
        try {
            Employee existingEmployee = employeeDao.getEmployeeById(employee);
            if (existingEmployee == null) {
                throw new EmployeeNotFoundException(employee);
            }
            boolean employeeWithNameExistForUpdate = employeeDao.doesEmployeeWithNameExistForUpdate(existingEmployee.getId(), request.getEmployeeName());
            if (employeeWithNameExistForUpdate) {
                throw new EmployeeExistsException(request.getEmployeeName());
            }
            Employee employeeUpdate = EmployeeMapper.updateEmployee(existingEmployee, request);
            Employee createEmployee = employeeDao.saveEmployee(employeeUpdate);
            log.info("Updated employee successfully, employeeId: {} Request: {}", employee, request);
            return EmployeeMapper.createEmployeeVOWithOutUrl(createEmployee);
        } catch (Exception e) {
            log.error("Error while updating employee, employeeId: {} Request: {}", employee, request);
            throw e;
        }
    }


    public List<EmployeeVO>  getEmployeeList() {
        List<Employee> employeeList = employeeDao.getEmployeeList();
        return EmployeeMapper.createEmployeeVOList(employeeList);
    }
    private  String createFileUrl(String fileName) {
        return awsS3BucketConfig.getBucketName() + "/" + fileName;
    }
}
