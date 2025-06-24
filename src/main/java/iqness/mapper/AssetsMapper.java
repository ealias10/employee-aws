package iqness.mapper;

import iqness.model.Assets;
import iqness.model.AssetsImage;
import iqness.request.AssetsCreateRequest;
import iqness.vo.AssetsVO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AssetsMapper {

    public static Assets createAssets(AssetsCreateRequest request,Long warranty,Long purchaseDay)
    {
        return Assets.builder()
                .serialNo(request.getSerialNo())
                .brand(request.getBrand())
                .warrantyEndDate(warranty)
                .type(request.getType())
                .active(true)
                .purchaseDay(purchaseDay)
                .activeAllocations(false)
                .model(request.getModel())
                .build();
    }

    public static AssetsVO createAssetsVO(Assets assets)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date warranty = new Date(assets.getWarrantyEndDate());
        Date purchaseDay = new Date(assets.getPurchaseDay());
        return AssetsVO.builder()
                .assetsId(assets.getId())
                .serialNo(assets.getSerialNo())
                .brand(assets.getBrand())
                .model(assets.getModel())
                .type(assets.getType().toString())
                .warrantyEndDate(sdf.format(warranty))
                .purchaseDay(sdf.format(purchaseDay))
                .build();
    }
    public static AssetsVO createAssetsVO(Assets assets,List<String> assetsImage,String toDate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date warranty = new Date(assets.getWarrantyEndDate());
        Date purchaseDay = new Date(assets.getPurchaseDay());
        return AssetsVO.builder()
                .assetsId(assets.getId())
                .serialNo(assets.getSerialNo())
                .brand(assets.getBrand())
                .model(assets.getModel())
                .type(assets.getType().toString())
                .warrantyEndDate(sdf.format(warranty))
                .purchaseDay(sdf.format(purchaseDay))
                .assetsList(assetsImage)
                .allocatedTo(toDate)
                .build();
    }
    public static Assets updateAssets(Assets assets,AssetsCreateRequest request,Long warranty,Long purchaseDay)
    {
        assets.setBrand(request.getBrand());
        assets.setModel(request.getModel());
        assets.setSerialNo(request.getSerialNo());
        assets.setType(request.getType());
        assets.setWarrantyEndDate(warranty);
        assets.setPurchaseDay(purchaseDay);
        return assets;
    }

    public static List<AssetsVO> createListOfAssetsVO(List<Assets> assetsList)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return assetsList.stream()
                .map(
                        assets ->
                                new AssetsVO(
                                        assets.getId(),
                                        sdf.format(new Date(assets.getWarrantyEndDate())),
                                        assets.getSerialNo(),
                                        assets.getModel(),
                                        assets.getBrand(),
                                        getLongConverToDate(assets.getAllocatedTo()),
                                        assets.getType().toString(),
                                        getAssetsAllocatedOrNot(assets.getActiveAllocations()),
                                        getAssetsAllocated(assets.getActiveAllocations()),
                                        null,
                                        getLongConverToDate(assets.getPurchaseDay())
                                ))
                .collect(Collectors.toList());

    }
    public static String getAssetsAllocatedOrNot(boolean value)
    {
        if(value)
        return "yes";
        else
        return "no";
    }

    public static String getLongConverToDate(Long value)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        if(value==0)
            return "";
        else
            return sdf.format(new Date(value));
    }

    public static String getAssetsAllocated(boolean value)
    {
        if(value)
            return "Remove";
        else
            return "Allocate";
    }
    public static List<AssetsVO> createAssetsVOList(List<Assets> assetsVOList) {
        return assetsVOList.stream().map(AssetsMapper::createAssetsVO).collect(Collectors.toList());
    }

    public static List<AssetsImage> createAssetsImage(List<String> imageList,Assets assets)
    {
        List<AssetsImage> assetsImages=new ArrayList<>();
        for (int i=0;i<=imageList.size()-1;i++)
        assetsImages.add(createAssetsImage(imageList.get(i),assets));
        return assetsImages;
    }
   public static AssetsImage createAssetsImage(String url,Assets assets)
   {
       return  AssetsImage.builder()
               .assets(assets)
               .url(url)
               .build();
   }
}
