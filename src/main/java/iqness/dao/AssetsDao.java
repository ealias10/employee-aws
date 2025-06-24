package iqness.dao;
import iqness.model.Assets;
import iqness.model.Employee;
import iqness.repository.AssetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AssetsDao {

    @Autowired
    AssetsRepository assetsRepository;

    public Assets getAssetsBySerialNo(String serialNo)
    {
        return assetsRepository.getAssetsBySerialNo(serialNo);
    }


    public Assets saveAssets(Assets assets)
    {
        return assetsRepository.save(assets);
    }

    public Assets getAssetsById(UUID assetsId)
    {
        return assetsRepository.findByIdAndActiveTrue(assetsId).orElse(null);
    }

    public void deleteAssetsById(UUID id)
    {
        assetsRepository.deleteAssetsById(id);
    }

    public boolean doesAssetsWithSerialNoExistForUpdate(UUID id,String serialNo)
    {
        return assetsRepository.doesAssetsWithSerialNoExistForUpdate(id,serialNo);
    }

    public Page<Assets> listAssets(Pageable pageable) {
        return assetsRepository.findByActiveTrue(pageable);
    }

    public Page<Assets> listAssetsByUserId(UUID userId,Pageable pageable) {
        return assetsRepository.listAssetsByUserId(userId,pageable);
    }

    public Page<Assets> getAssetsListBySerialNo(String name, Pageable pageable) {
        return assetsRepository.getAssetsListBySerialNo(name, pageable);
    }

    public Page<Assets> getAssetsListByType(String search, Pageable pageable) {
        return assetsRepository.getAssetsListByType(search, pageable);
    }

    public Page<Assets> getAssetsListBySerialNoOrType(String search, Pageable pageable) {
        return assetsRepository.getAssetsListBySerialNoOrType(search, pageable);
    }


    public Page<Assets> getAssetsListByAssetsNameAndUserId(UUID userId,String name, Pageable pageable) {
        return assetsRepository.getAssetsListByAssetsNameAndUserId(userId,name, pageable);
    }

    public Assets getAssetsByToDate(Long date)
    {
        return assetsRepository.getAssetsByToDate(date);
    }

    public List<Assets> getAssetsList()
    {
        return assetsRepository.findAllAssets();
    }
}
