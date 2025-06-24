package iqness.service;
import iqness.config.AwsS3BucketConfig;
import iqness.dao.AssetsAllocationsDao;
import iqness.dao.AssetsDao;
import iqness.dao.AssetsImageDao;
import iqness.dao.EmployeeDao;
import iqness.exception.*;
import iqness.mapper.AssetsAllocationsMapper;
import iqness.mapper.AssetsMapper;
import iqness.model.Assets;
import iqness.model.AssetsAllocations;
import iqness.model.AssetsImage;
import iqness.model.Employee;
import iqness.request.*;
import iqness.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import iqness.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class AssetsService {

    @Autowired
    private AssetsDao assetsDao;

    @Autowired
    private final AwsS3BucketConfig awsS3BucketConfig;

    @Autowired
    private AssetsAllocationsDao assetsAllocationsDao;

    @Autowired
    private EmployeeDao employeeDao;

    private final StorageService storageService;
    @Autowired
    private AssetsImageDao assetsImageDao;

    public AssetsVO createAssets(AssetsCreateRequest request,List<MultipartFile> image) throws AssetsExistsException, ParseException, IOException, ImageExistsException {
        try {
            Assets existingAssets = assetsDao.getAssetsBySerialNo(request.getSerialNo());
            if (existingAssets != null) {
                throw new AssetsExistsException(request.getSerialNo());
            }
            List<String> imageList=new ArrayList<>();
            for(int i=0;i<=image.size()-1;i++)
            {
               imageList.add(image.get(i).getOriginalFilename());
            }
            boolean doesExistingImage=assetsImageDao.doesExistingImageByImageName(imageList);
            if (doesExistingImage) {
                throw new ImageExistsException();
            }
            for(int i=0;i<=image.size()-1;i++)
            {
                storageService.uploadToStorage(image.get(i), image.get(i).getOriginalFilename());
            }
            long warranty= getDateConvertToLong(request.getWarrantyEndDate());
            long  purchaseDay= getDateConvertToLong(request.getPurchaseDay());
            Assets assets= AssetsMapper.createAssets(request,warranty,purchaseDay);
            Assets savedAssets = assetsDao.saveAssets(assets);
            List<AssetsImage> assetsImage=AssetsMapper.createAssetsImage(imageList,savedAssets);
            List<AssetsImage> create=assetsImageDao.createAssetsImage(assetsImage);

            log.info("Create Assets successfully, Request: {}", request);

            return AssetsMapper.createAssetsVO(savedAssets);
        }
        catch (Exception e)
        {
            log.error("Error while creating Assets,  Request: {}", request);
            throw e;
        }
    }

    public AssetsVO updateAssets(AssetsCreateRequest request,UUID assetsId) throws AssetsExistsException, AssetsNotFoundException, ParseException {
        try {
            Assets existingAssets = assetsDao.getAssetsById(assetsId);
            if (existingAssets == null) {
                throw new AssetsNotFoundException(assetsId);

            }
            boolean assetsWithSerialNoExistForUpdate = assetsDao.doesAssetsWithSerialNoExistForUpdate(existingAssets.getId(), request.getSerialNo());
            if (assetsWithSerialNoExistForUpdate) {
                throw new AssetsExistsException(request.getSerialNo());
            }
            long warranty= getDateConvertToLong(request.getWarrantyEndDate());
            long  purchaseDay= getDateConvertToLong(request.getPurchaseDay());
            Assets assetsUpdate = AssetsMapper.updateAssets(existingAssets, request,warranty,purchaseDay);
            Assets savedAssets =  assetsDao.saveAssets(assetsUpdate);
            log.info("Updated Assets successfully, employeeId: {} Request: {}", request, request);
            return AssetsMapper.createAssetsVO(savedAssets);
        } catch (Exception e) {
            log.error("Error while updating Assets, employeeId: {} Request: {}", request, request);
            throw e;
        }
    }
   public void deleteAssets(UUID assetsId) throws AssetsNotFoundException, AssetsExistsException {
       try {

           Assets existingAssets = assetsDao.getAssetsById(assetsId);
           List<UUID> asstsImageIdList=new ArrayList<>();
           if (existingAssets == null) {
               throw new AssetsNotFoundException(assetsId);

           }
           if(existingAssets.getActiveAllocations()==true)
           {
               throw new AssetsExistsException();
           }
           existingAssets.setActive(false);
           Assets assets=assetsDao.saveAssets(existingAssets);
           List<AssetsImage> assetsImages=assetsImageDao.getAssetsListByAssetsId(assetsId);
           for(int i=0;i<=assetsImages.size()-1;i++)
           {
               asstsImageIdList.add(assetsImages.get(i).getId());
           }
        //   assetsImageDao.deleteAssetsByIdList(asstsImageIdList);
           log.info(
                   "Deleted Assets successfully, assetsId: {}", assetsId);
       } catch (Exception e) {
           log.error(
                   "Error while deleting Assets,assetsId: {} ", assetsId);
           throw e;
       }
   }

   public AssetsVO getAssetsById(UUID assetsId) throws AssetsNotFoundException {
       try {

           Assets existingAssets = assetsDao.getAssetsById(assetsId);
           if (existingAssets == null) {
               throw new AssetsNotFoundException(assetsId);

           }
           List<AssetsImage> assetsImage=assetsImageDao.getAssetsListByAssetsId(assetsId);
           log.info("Retrieved Assets details successfully, assetsId: {}", assetsId);
           List<String> imageList=new ArrayList<>();
           String fileUrl;
           for(int i=0;i<=assetsImage.size()-1;i++)
               {
                   fileUrl=getUrls(assetsImage.get(i).getUrl());
                   imageList.add(fileUrl);
               }
           String todate=getAssetsToDate(existingAssets.getAllocatedTo());
           return AssetsMapper.createAssetsVO(existingAssets,imageList,todate);

       } catch (Exception e) {
           log.error("Error while fetching Assets details,assetsId: {}", assetsId);
           throw e;
       }
   }

    public PaginatedResponseVOAndCount<AssetsVO> listAssets(Integer offset, Integer limit, String search,String filter) {
        try {
            Pageable pageable = PageRequest.of(offset - 1, limit);
            Page<Assets> assetsList;
            if (search == null || search.trim() == "") {
                assetsList = assetsDao.listAssets(pageable);
            } else {
                if(filter.equals("Serial No"))
                assetsList = assetsDao.getAssetsListBySerialNo(search.toUpperCase(), pageable);
                else if (filter.equals("Type"))
                assetsList = assetsDao.getAssetsListByType(search.toUpperCase(), pageable);
                else
                assetsList = assetsDao.getAssetsListBySerialNoOrType(search.toUpperCase(), pageable);
            }
            log.info("Retrieved Assets list successfully");
            var assetsVOList = AssetsMapper.createListOfAssetsVO(assetsList.toList());
            return new PaginatedResponseVOAndCount<>(
                    assetsList.getTotalElements(), assetsVOList);
        } catch (Exception e) {
            log.error("Error while Retrieved Assets list");
            throw e;
        }
    }

    public PaginatedResponseVOAndCount<AssetsVO> listAssetsByEmployee(Integer offset, Integer limit, String search) {
        String userIdFromToken = null;
        try {
            userIdFromToken = Utility.getUserId();
            UUID userId= UUID.fromString(userIdFromToken);
            Pageable pageable = PageRequest.of(offset - 1, limit);
            Page<Assets> assetsList;
            if (search == null || search.trim() == "") {
                assetsList = assetsDao.listAssetsByUserId(userId,pageable);
            } else {
                assetsList = assetsDao.getAssetsListByAssetsNameAndUserId(userId,search.toUpperCase(), pageable);
            }
            log.info("Retrieved Assets list successfully");
            var assetsVOList = AssetsMapper.createListOfAssetsVO(assetsList.toList());
            return new PaginatedResponseVOAndCount<>(
                    assetsList.getTotalElements(), assetsVOList);
        } catch (Exception e) {
            log.error("Error while Retrieved Assets list");
            throw e;
        }
    }

    public AssetsAllocationsVO createAssetsAllocations(AssetsAllocationsCreateRequest request) throws AssetsNotFoundException, EmployeeNotFoundException, AssetsAllocationsExistsException, ParseException {
        try {
            Employee existingEmployee = employeeDao.getEmployeeByEmpId(request.getEmployeeId());
            if (existingEmployee == null) {
                throw new EmployeeNotFoundException(request.getEmployeeId());
            }
            Assets existingAssets = assetsDao.getAssetsBySerialNo(request.getSerialNo());
            if (existingAssets == null) {
                throw new AssetsNotFoundException(request.getSerialNo());

            }
            if (existingAssets.getActiveAllocations()==true) {
                throw new AssetsAllocationsExistsException();
            }
            AssetsAllocations assetsAllocations= AssetsAllocationsMapper.createAllocationsAssets(existingEmployee,existingAssets);
            AssetsAllocations savedAssetsAllocations = assetsAllocationsDao.saveAssetsAllocations(assetsAllocations);
            existingAssets.setAllocatedTo(savedAssetsAllocations.getAllocatedFrom());
            existingAssets.setActiveAllocations(true);
            Assets assetsUpdate=assetsDao.saveAssets(existingAssets);
            log.info("Create AssetsAllocations successfully, Request: {}", request);
            String allocatedTo="";
            return AssetsAllocationsMapper.createAssetsAllocationsVO(savedAssetsAllocations,allocatedTo);
        }
        catch (Exception e)
        {
            log.error("Error while creating AssetsAllocations,  Request: {}", request);
            throw e;
        }
    }



    public AssetsAllocationsVO updateAssetsAllocations(UUID assetsId) throws AssetsNotFoundException, EmployeeNotFoundException, AssetsAllocationsExistsException, ParseException, AssetsAllocationsNotFoundException {
        try {
            AssetsAllocations assetsAllocations=assetsAllocationsDao.getAssetsAllocationsByAssetsId(assetsId);
            if(assetsAllocations==null)
            {
                throw new AssetsAllocationsNotFoundException(assetsId);
            }
            Assets assets=assetsAllocations.getAssets();
            assets.setActiveAllocations(false);
            assets.setAllocatedTo(0);
            Assets savedAssets=assetsDao.saveAssets(assets);
            assetsAllocations.setAllocatedTo(System.currentTimeMillis());
            AssetsAllocations savedAssetsAllocations=assetsAllocationsDao.saveAssetsAllocations(assetsAllocations);
            String allocatedTo="";
            log.info("Update AssetsAllocations successfully, id: {}", assetsId);
            return AssetsAllocationsMapper.createAssetsAllocationsVO(savedAssetsAllocations,allocatedTo);
        }
        catch (Exception e)
        {
            log.error("Error while update AssetsAllocations,  id: {}", assetsId);
            throw e;
        }
    }

    public PaginatedResponseVOAndCount<AssetsAllocationsVO> listAssetsAllocations(Integer offset, Integer limit, String search) {
        try {
            Pageable pageable = PageRequest.of(offset - 1, limit);
            Page<AssetsAllocations> assetsList;
            if (search == null || search.trim() == "") {
                assetsList = assetsAllocationsDao.listAssetsAllocations(pageable);
            } else {
                assetsList =assetsAllocationsDao.getAssetsAllocationsListByEmpNameOrSerialNo(pageable,search);
            }
            log.info("Retrieved AssetsAllocations list successfully");
            var assetsVOList = AssetsAllocationsMapper.createListOfAssetsAllocationsVO(assetsList.toList());
            return new PaginatedResponseVOAndCount<>(
                    assetsList.getTotalElements(), assetsVOList);
        } catch (Exception e) {
            log.error("Error while Retrieved AssetsAllocations list");
            throw e;
        }
    }

    public PaginatedResponseVOAndCount<AssetsAllocationsVO> listAssetsAllocationsByUser(Integer offset, Integer limit, String search) {
        String userIdFromToken = null;
        try {
            userIdFromToken = Utility.getUserId();
            UUID userId= UUID.fromString(userIdFromToken);
            Pageable pageable = PageRequest.of(offset - 1, limit);
            Page<AssetsAllocations> assetsList;
            if (search == null || search.trim() == "") {
                assetsList = assetsAllocationsDao.listAssetsAllocationsByUser(userId,pageable);
            } else {
                assetsList =assetsAllocationsDao.getAssetsAllocationsListByEmpNameOrSerialNoAndUserId(userId,pageable,search);
            }
            log.info("Retrieved AssetsAllocations list successfully");
            var assetsVOList = AssetsAllocationsMapper.createListOfAssetsAllocationsVO(assetsList.toList());
            return new PaginatedResponseVOAndCount<>(
                    assetsList.getTotalElements(), assetsVOList);
        } catch (Exception e) {
            log.error("Error while Retrieved AssetsAllocations list");
            throw e;
        }
    }


    public AssetsAllocationsVO getAssetsAllocationsById(UUID assetsId) throws AssetsAllocationsNotFoundException, ParseException {
        try {

            AssetsAllocations existingAssetsAllocations = assetsAllocationsDao.getAssetsAllocationsById(assetsId);
            if (existingAssetsAllocations == null) {
                throw new AssetsAllocationsNotFoundException(assetsId);

            }
            log.info("Retrieved AssetsAllocations details successfully, assetsId: {}", assetsId);
            String allocatedTo;
            if(existingAssetsAllocations.getAllocatedTo()==0)
            {
                allocatedTo="";
            }
            else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                Date ToDate = new Date(existingAssetsAllocations.getAllocatedTo());
                allocatedTo = sdf.format(ToDate);
            }
            return AssetsAllocationsMapper.createAssetsAllocationsVO(existingAssetsAllocations,allocatedTo);

        } catch (Exception e) {
            log.error("Error while fetching AssetsAllocations details,assetsId: {}", assetsId);
            throw e;
        }
    }

    public static String getAssetsToDate(Long toDate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        if(toDate==0)
        {
            return "";
        }
        else {
            Date allocated = new Date(toDate);
            return sdf.format(allocated);
        }

    }

    private Long getDateConvertToLong(String warranty) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(warranty);
       return  date.getTime();
    }

    public List<AssetsVO> getAssetsList() {
        List<Assets> assetsList = assetsDao.getAssetsList();
        return AssetsMapper.createAssetsVOList(assetsList);
    }

    public String  getUrls(String  filePath)
    {
        var expiryTime = System.currentTimeMillis() + 1000 * 60 * 60;
        var url = storageService.createLink(filePath, expiryTime);
        return url.toString();
    }
}
