package iqness.repository;

import iqness.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ModulesRepository extends JpaRepository<Modules, UUID> {
    @Query("select m from Modules m where m.modulesName=:name")
    Modules getModulesByName(@Param("name") String name);

    @Query("select m from Modules m  where  m.active='true' and m.projects.active='true' order by m.projects.etaDate DESC")
    Page<Modules> listAllModule(Pageable pageable);
    Optional<Modules> findByIdAndActiveTrue(UUID id);
    @Modifying
    @Query("update from Modules m set m.active='false' where m.id=:id")
    void deleteModuleById(@Param("id")UUID moduleId);
    @Query("select m from Modules m  where m.employee.id=:id and m.active='true' and m.projects.active='true' order by m.projects.etaDate DESC")
    Page<Modules> listUserModule(@Param("id")UUID employeeId, Pageable pageable);
    @Query("select (count(m) > 0) from Modules m where  upper(m.modulesName) = upper(:name) and m.id!=:id and m.active='true' ")
    boolean doesModuleWithNameExistForUpdate(@Param("id")UUID id,@Param("name")String name);
}
