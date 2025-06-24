package iqness.dao;
import iqness.model.Assets;
import iqness.model.AssetsAllocations;
import iqness.repository.AssetsAllocationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AssetsAllocationsDao {

    @Autowired
    AssetsAllocationsRepository assetsAllocationsRepository;

    public AssetsAllocations saveAssetsAllocations(AssetsAllocations assetsAllocations)
    {
        return assetsAllocationsRepository.save(assetsAllocations);
    }

    public AssetsAllocations getAssetsAllocationsByAssetsId(UUID id)
    {
        return assetsAllocationsRepository.getAssetsAllocationsByAssetsId(id);
    }

    public AssetsAllocations getAssetsAllocationsById(UUID id)
    {
        return assetsAllocationsRepository.getAssetsAllocationsById(id);
    }

    public AssetsAllocations getAssetsAllocationsByTODate(Long toDate)
    {
        return assetsAllocationsRepository.getAssetsAllocationsByTODate(toDate);
    }

    public void deleteAssetsAllocationsById(UUID id)
    {
    assetsAllocationsRepository.deleteAssetsAllocationsById(id);
    }

    public AssetsAllocations getAssetsAllocationsByEmpIdAndSerialNo(String serialNo,String empId)
    {
        return assetsAllocationsRepository.getAssetsAllocationsByEmpIdAndSerialNo(serialNo,empId);
    }

    public AssetsAllocations getAassetsAllocationsByFromDate(String serialNo)
    {
        return assetsAllocationsRepository.getAassetsAllocationsByFromDate(serialNo);
    }

    public List<AssetsAllocations> getAssetsAllocationsByEmployeeId(UUID employeeId)
    {
        return assetsAllocationsRepository.getAssetsAllocationsByEmployeeId(employeeId);
    }

    public Page<AssetsAllocations> listAssetsAllocations(Pageable pageable) {
        return assetsAllocationsRepository.listAssetsAllocations(pageable);
    }
   public Page<AssetsAllocations> getAssetsAllocationsListByEmpNameOrSerialNo(Pageable pageable,String name)
   {
       return assetsAllocationsRepository.getAssetsAllocationsListByEmpNameOrSerialNo(name,pageable);
   }

    public Page<AssetsAllocations> listAssetsAllocationsByUser(UUID userId,Pageable pageable) {
        return assetsAllocationsRepository.listAssetsAllocationsByUser(userId,pageable);
    }

    public Page<AssetsAllocations> getAssetsAllocationsListByEmpNameOrSerialNoAndUserId(UUID userId,Pageable pageable,String name)
    {
        return assetsAllocationsRepository.getAssetsAllocationsListByEmpNameOrSerialNoAndUserId(name,userId,pageable);
    }

}
