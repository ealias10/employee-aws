package iqness.mapper;

import iqness.model.Assets;
import iqness.model.AssetsAllocations;
import iqness.model.Employee;
import iqness.request.EmployeeCreateRequest;
import iqness.vo.AssetsVO;
import iqness.vo.EmployeeVO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {
    public static Employee createEmployee(EmployeeCreateRequest request, String empId, String filePathImage)
    {

        return Employee.builder()
                .active(true)
                .gender(request.getGender())
                .email(request.getEmail())
                .name(request.getEmployeeName())
                .empId(empId)
                .age(request.getAge())
                .qualification(request.getQualification())
                .experience(request.getExperience())
                .photoPath(filePathImage)
                .phoneNumber(request.getPhone_number())
                .bloodGroup(request.getBloodGroup())
                .address(request.getAddress())
                .pin(request.getPin())
                .build();
    }
    public static Employee updateEmployee(Employee employee,EmployeeCreateRequest request)
    {
        employee.setName(request.getEmployeeName());
        employee.setQualification(request.getQualification());
        employee.setExperience(request.getExperience());
        employee.setPhoneNumber(request.getPhone_number());
        employee.setEmail(request.getEmail());
        employee.setBloodGroup(request.getBloodGroup());
        employee.setAddress(request.getAddress());
        employee.setGender(request.getGender());
        employee.setPin(request.getPin());
        employee.setAge(request.getAge());
        return employee;
    }
    public static EmployeeVO createEmployeeVO(Employee employee,String url)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Date updateDate = new Date(employee.getUpdatedAt());
        Date createDate = new Date(employee.getCreatedAt());
        return EmployeeVO.builder()
                .gender(employee.getGender().name())
                .name(employee.getName())
                .employeeId(employee.getId())
                .empId(employee.getEmpId())
                .qualification(employee.getQualification())
                .experience(employee.getExperience())
                .photoUrl(url)
                .phoneNumber(employee.getPhoneNumber())
                .email(employee.getEmail())
                .bloodGroup(employee.getBloodGroup())
                .address(employee.getAddress())
                .pin(employee.getPin())
                .age(employee.getAge())
                .createdBy(employee.getCreatedBy())
                .createdAt(sdf.format(createDate))
                .modifiedAt(sdf.format(updateDate))
                .modifiedBy(employee.getUpdatedBy())
                .build();
    }
    public static EmployeeVO createEmployeeVO(Employee employee,String url,List<AssetsAllocations> assetsAllocations)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Date updateDate = new Date(employee.getUpdatedAt());
        Date createDate = new Date(employee.getCreatedAt());
        return EmployeeVO.builder()
                .gender(employee.getGender().name())
                .name(employee.getName())
                .employeeId(employee.getId())
                .empId(employee.getEmpId())
                .qualification(employee.getQualification())
                .experience(employee.getExperience())
                .photoUrl(url)
                .phoneNumber(employee.getPhoneNumber())
                .email(employee.getEmail())
                .bloodGroup(employee.getBloodGroup())
                .address(employee.getAddress())
                .pin(employee.getPin())
                .age(employee.getAge())
                .createdBy(employee.getCreatedBy())
                .createdAt(sdf.format(createDate))
                .modifiedAt(sdf.format(updateDate))
                .modifiedBy(employee.getUpdatedBy())
                .assetsAllocated(AssetsAllocationsMapper.createListOfAssetsAllocationsListVO(assetsAllocations))
                .build();
    }
    public static List<EmployeeVO> createListOfEmployeeVO(List<Employee> employeeList) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        return employeeList.stream()
                .map(
                        employee ->
                                new EmployeeVO(
                                        employee.getName(),
                                        employee.getId(),
                                        employee.getEmpId(),
                                        employee.getQualification(),
                                        employee.getExperience(),
                                        employee.getPhotoPath(),
                                        employee.getPhoneNumber(),
                                        employee.getEmail(),
                                        employee.getBloodGroup(),
                                        employee.getAddress(),
                                        employee.getPin(),
                                        employee.getAge(),
                                        employee.getGender().name(),
                                        null,
                                        sdf.format(new Date(employee.getCreatedAt())),
                                        employee.getCreatedBy(),
                                        sdf.format(new Date(employee.getUpdatedAt())),
                                        employee.getUpdatedBy()

                                      ))
                .collect(Collectors.toList());
    }
    public static EmployeeVO createEmployeeVOWithOutUrl(Employee employee)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy, HH:mm:ss");
        Date updateDate = new Date(employee.getUpdatedAt());
        Date createDate = new Date(employee.getCreatedAt());
        return EmployeeVO.builder()
                .gender(employee.getGender().name())
                .name(employee.getName())
                .employeeId(employee.getId())
                .empId(employee.getEmpId())
                .qualification(employee.getQualification())
                .experience(employee.getExperience())
                .phoneNumber(employee.getPhoneNumber())
                .email(employee.getEmail())
                .bloodGroup(employee.getBloodGroup())
                .address(employee.getAddress())
                .pin(employee.getPin())
                .age(employee.getAge())
                .createdBy(employee.getCreatedBy())
                .createdAt(sdf.format(createDate))
                .modifiedAt(sdf.format(updateDate))
                .modifiedBy(employee.getUpdatedBy())
                .build();
    }
    public static List<EmployeeVO> createEmployeeVOList(List<Employee> employeeVOList) {
        return employeeVOList.stream().map(EmployeeMapper::createEmployeeVOWithOutUrl).collect(Collectors.toList());
    }

}
