package iqness.mapper;

import iqness.model.Assets;
import iqness.model.AssetsAllocations;
import iqness.model.Employee;
import iqness.vo.AssetsAllocationsVO;
import iqness.vo.AssetsVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class AssetsAllocationsMapper {

    public static AssetsAllocations createAllocationsAssets(Employee employee,Assets assets)
    {
      return AssetsAllocations.builder()
              .assets(assets)
              .employee(employee)
              .allocatedFrom(System.currentTimeMillis())
              .build();
    }
//    public static List<AssetsAllocationsVO> createAssetsAllocationsVOList(List<AssetsAllocations> assetAllocationsList) {
//        return assetAllocationsList.stream().map(AssetsAllocationsMapper::createAssetsAllocationsVO).collect(Collectors.toList());
//    }
public static List<AssetsAllocationsVO> createListOfAssetsAllocationsListVO(List<AssetsAllocations> assetsVOList) {
    return assetsVOList.stream().map(AssetsAllocationsMapper::createAssetsAllocationsVO).collect(Collectors.toList());
}

    public static AssetsAllocationsVO createAssetsAllocationsVO(AssetsAllocations assetsAllocations,String allocatedTo) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Date allocatedFrom = new Date(assetsAllocations.getAllocatedFrom());
        return AssetsAllocationsVO.builder()
                .assetsallocationsId(assetsAllocations.getId())
                .empname(assetsAllocations.getEmployee().getName())
                .empId(assetsAllocations.getEmployee().getEmpId())
                .type(assetsAllocations.getAssets().getType().toString())
                .serialNo(assetsAllocations.getAssets().getSerialNo())
                .allocatedFrom(sdf.format(allocatedFrom))
                .allocatedTo(allocatedTo)
                .build();
    }
    public static AssetsAllocationsVO createAssetsAllocationsVO(AssetsAllocations assetsAllocations) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Date allocatedFrom = new Date(assetsAllocations.getAllocatedFrom());
        return AssetsAllocationsVO.builder()
                .assetsallocationsId(assetsAllocations.getId())
                .empname(assetsAllocations.getEmployee().getName())
                .empId(assetsAllocations.getEmployee().getEmpId())
                .type(assetsAllocations.getAssets().getType().toString())
                .brand(assetsAllocations.getAssets().getBrand())
                .model(assetsAllocations.getAssets().getModel())
                .serialNo(assetsAllocations.getAssets().getSerialNo())
                .allocatedFrom(sdf.format(allocatedFrom))
                .allocatedTo(getAssetsAllocationTo(assetsAllocations.getAllocatedTo()))
                .build();
    }
    public static String getAssetsAllocationTo(Long toDate)
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

    public static List<AssetsAllocationsVO> createListOfAssetsAllocationsVO(List<AssetsAllocations> assetsList)
    {
        return assetsList.stream()
                .map(
                        assets ->
                                new AssetsAllocationsVO(
                                        assets.getId(),
                                       assets.getAssets().getSerialNo(),
                                        assets.getEmployee().getEmpId(),
                                        assets.getEmployee().getName(),
                                        assets.getAssets().getType().toString(),
                                        assets.getAssets().getModel(),
                                        assets.getAssets().getBrand(),
                                        getDate(assets.getAllocatedFrom()),
                                        getDate(assets.getAllocatedTo())

                                ))
                .collect(Collectors.toList());

    }
    private static String getDate(Long getDate)
    {
        if(getDate==0)
        {
            return "";
        }
        else
        {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
           return sdf.format(new Date(getDate));
        }
    }
}
