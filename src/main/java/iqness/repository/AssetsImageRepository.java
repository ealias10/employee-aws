package iqness.repository;

import iqness.model.Assets;
import iqness.model.AssetsImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AssetsImageRepository  extends JpaRepository<AssetsImage, UUID> {

    @Query("select a from AssetsImage a where a.assets.id=:assetsId ")
    List<AssetsImage> getAssetsListByAssetsId(@Param("assetsId")UUID assetsId);

    @Query("select (count(a) > 0) from AssetsImage a where a.url in (:imagelist)")
    boolean doesExistingImageByImageName(@Param("imagelist")List<String> imageList);

    @Query("delete from AssetsImage a where  a.id in (:imagelist)")
    void deleteAssetsByIdList(@Param("imagelist")List<UUID> assetsImage);
}
