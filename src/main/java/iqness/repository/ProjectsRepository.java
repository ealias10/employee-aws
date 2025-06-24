package iqness.repository;

import iqness.model.Employee;
import iqness.model.Projects;
import iqness.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectsRepository extends JpaRepository<Projects, UUID> {
    @Query("select count(p) from Projects p")
    Integer getProjectCount();

    @Query("select p from Projects p where p.projectName=:name")
    Projects getProjectsByName(@Param("name") String name);
    @Query("select p from Projects p where p.projId=:id")
    Projects getProjectsByProjectId(@Param("id")String projectId);

    Optional<Projects> findByIdAndActiveTrue(UUID id);

      Page<Projects> findByActiveTrue(Pageable pageable);

       @Modifying
      @Query("update from Projects p set p.active='false' where p.id=:id")
      void deleteProjectById(@Param("id") UUID projectId);

       @Query("select (count(p) > 0) from Projects p where  upper(p.projectName) = upper(:name) and p.id!=:id and p.active='true' ")
       boolean doesProjectWithNameExistForUpdate(@Param("id")UUID id,@Param("name")String name);

    @Query("select p from Projects p where p.active='true'")
    List<Projects> getAllProjects();
}
