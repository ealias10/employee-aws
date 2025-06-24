package iqness.dao;

import iqness.model.Modules;
import iqness.model.Projects;
import iqness.repository.LeaveManagementRepository;
import iqness.repository.ModulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ModulesDao {
    @Autowired
    ModulesRepository modulesRepository;

    public Modules getModulesByName(String name) {
        return modulesRepository.getModulesByName(name);
    }

    public Modules saveModules(Modules modules)
    {
        return modulesRepository.save(modules);
    }

    public Modules getModulesById(UUID id)
    {
        return modulesRepository.findByIdAndActiveTrue(id).orElse(null);

    }
    public void deleteModuleById(UUID moduleId)
    {
       modulesRepository.deleteModuleById(moduleId);
    }

    public Page<Modules> listAllModule(Pageable pageable)
    {
        return modulesRepository.listAllModule(pageable);
    }
    public Page<Modules> listUserModule(UUID employeeId,Pageable pageable)
    {
        return modulesRepository.listUserModule(employeeId,pageable);
    }

    public boolean doesModuleWithNameExistForUpdate(UUID id,String name)
    {
        return modulesRepository.doesModuleWithNameExistForUpdate(id,name);
    }

}
