package iqness.dao;

import com.amazonaws.services.dynamodbv2.xspec.L;
import iqness.model.AssetsImage;
import iqness.repository.AssetsImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AssetsImageDao {
    @Autowired
    AssetsImageRepository assetsImageRepository;
    public List<AssetsImage> getAssetsListByAssetsId(UUID assetsId)
    {
        return assetsImageRepository.getAssetsListByAssetsId(assetsId);
    }
    public void deleteAssetsByIdList(List<UUID> assetsImages)
    {
        assetsImageRepository.deleteAssetsByIdList(assetsImages);
    }

    public List<AssetsImage> createAssetsImage(List<AssetsImage> assetsImage)
    {
        return assetsImageRepository.saveAll(assetsImage);
    }

    public boolean doesExistingImageByImageName(List<String> imageList)
    {
        return assetsImageRepository.doesExistingImageByImageName(imageList);
    }
}
