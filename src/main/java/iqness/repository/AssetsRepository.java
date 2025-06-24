package iqness.repository;

import iqness.model.Assets;
import iqness.model.Employee;
import iqness.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetsRepository extends JpaRepository<Assets, UUID> {

    @Query("select a from Assets a where a.serialNo=:serialNo and a.active='true' ")
    Assets getAssetsBySerialNo(@Param("serialNo") String serialNo);


    Optional<Assets> findByIdAndActiveTrue(UUID id);

    Page<Assets> findByActiveTrue(Pageable pageable);

    @Modifying
    @Query("update Assets a  set a.active='false' where a.id=:id")
    void deleteAssetsById(@Param("id")UUID id);



    @Query("select (count(a) > 0) from Assets a where a.serialNo=:serialNo and a.active='true' and a.id<>:id ")
    boolean doesAssetsWithSerialNoExistForUpdate(@Param("id")UUID id,@Param("serialNo")String serialNo);


    @Query("select a from Assets a where (upper(a.serialNo) like %:name%)  and a.active='true'")
    Page<Assets> getAssetsListBySerialNo(@Param("name") String name, Pageable pageable);

    @Query(value = "select a.* from assets a where (upper(a.type) like %:search%)  and a.active='true'",nativeQuery = true)
    Page<Assets> getAssetsListByType(@Param("search") String search, Pageable pageable);


    @Query(value = "select a.* from assets a where (upper(a.type) like %:search%) or (upper(a.serial_no) like %:search%)   and a.active='true'",nativeQuery = true)
    Page<Assets> getAssetsListBySerialNoOrType(@Param("search") String search, Pageable pageable);

    @Query("select a from Assets a where a.allocatedTo=:date")
    Assets getAssetsByToDate(@Param("date")Long date);

    @Query("select a from Assets a where a.active='true'")
    List<Assets> findAllAssets();
    @Query("select a from Assets a  inner join AssetsAllocations aa  on(a.id=aa.assets.id) inner join Employee e on(e.id=aa.employee.id) inner join Users u on (u.employee.id=e.id) where u.id=:userId and aa.allocatedTo='0' and  a.active='true'")
    Page<Assets> listAssetsByUserId(@Param("userId") UUID userId,Pageable pageable);
    @Query("select a from Assets a  inner join AssetsAllocations aa  on(a.id=aa.assets.id) inner join Employee e on(e.id=aa.employee.id) inner join Users u on (u.employee.id=e.id) where (upper(a.serialNo) like %:name%) and u.id=:userId and  a.active='true'")
    Page<Assets> getAssetsListByAssetsNameAndUserId(@Param("userId")UUID userId,@Param("name")String name, Pageable pageable);

}
