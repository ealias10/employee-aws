package iqness.repository;

import iqness.model.AssetsAllocations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetsAllocationsRepository extends JpaRepository<AssetsAllocations, UUID> {

    @Query("select a from AssetsAllocations a where a.assets.serialNo=:serialNo and a.employee.empId=:empId and a.allocatedFrom=(select max(b.allocatedFrom) from AssetsAllocations b where b.assets.serialNo=:serialNo) ")
    AssetsAllocations getAssetsAllocationsByEmpIdAndSerialNo(@Param("serialNo")String serialNo,@Param("empId")String empId);
    @Query("select a from AssetsAllocations a where a.assets.serialNo=:serialNo  and  a.allocatedFrom=(select max(b.allocatedFrom) from AssetsAllocations b where b.assets.serialNo=:serialNo ) ")
    AssetsAllocations getAassetsAllocationsByFromDate(@Param("serialNo")String serialNo);

    @Query("select a from AssetsAllocations a where a.id=:id ")
    AssetsAllocations getAssetsAllocationsById(@Param("id")UUID id);

    @Query("select a from AssetsAllocations a where a.assets.id=:id and a.allocatedTo=0 ")
    AssetsAllocations getAssetsAllocationsByAssetsId(@Param("id")UUID id);


    @Modifying
    @Query("delete from AssetsAllocations a where a.id=:id  ")
    void deleteAssetsAllocationsById(@Param("id")UUID id);
    @Query("select a from AssetsAllocations a where a.employee.id=:id and a.allocatedTo='0' order by a.allocatedFrom desc")
    List<AssetsAllocations> getAssetsAllocationsByEmployeeId(@Param("id")UUID id);

    @Query("select a from AssetsAllocations a where a.allocatedTo=:toDate ")
    AssetsAllocations getAssetsAllocationsByTODate(@Param("toDate")Long toDate);

    @Query("select a from AssetsAllocations a order by a.allocatedFrom desc")
    Page<AssetsAllocations> listAssetsAllocations(Pageable pageable);
    @Query("select a from AssetsAllocations a where a.assets.serialNo like %:name% or a.employee.empId like %:name% order by a.allocatedFrom desc")
    Page<AssetsAllocations> getAssetsAllocationsListByEmpNameOrSerialNo(@Param("name")String name,Pageable pageable);


    @Query("select a from AssetsAllocations a join a.employee e  inner join Users u on (e.id=u.employee.id) where a.assets.serialNo like %:name% or a.employee.empId like %:name% and u.id=:userId and u.active='true' and e.active='true' order by a.allocatedFrom desc")
    Page<AssetsAllocations> getAssetsAllocationsListByEmpNameOrSerialNoAndUserId(@Param("name")String name,@Param("userId")UUID userId,Pageable pageable);



    @Query("select a from AssetsAllocations a join a.employee e  inner join Users u on (e.id=u.employee.id) where u.active='true' and e.active='true' and u.id=:userId order by a.allocatedFrom desc")
    Page<AssetsAllocations> listAssetsAllocationsByUser(@Param("userId")UUID userId,Pageable pageable);
}
