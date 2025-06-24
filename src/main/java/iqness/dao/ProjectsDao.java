package iqness.dao;

import iqness.model.Employee;
import iqness.model.Projects;
import org.springframework.data.domain.Page;
import iqness.repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ProjectsDao {

    @Autowired
    ProjectsRepository projectsRepository;

    public Projects getProjectsByName(String name) {
        return projectsRepository.getProjectsByName(name);
    }

    public Integer getProjectCount()
    {
        return projectsRepository.getProjectCount();
    }

    public Projects saveProjects(Projects projects)
    {
        return projectsRepository.save(projects);
    }

    public Projects getProjectsByProjectId(String projectId)
    {
        return projectsRepository.getProjectsByProjectId(projectId);
    }

    public Projects getProjectsById(UUID id)
    {
       return projectsRepository.findByIdAndActiveTrue(id).orElse(null);
    }

    public Page<Projects> listProject(Pageable pageable)
    {
        return projectsRepository.findByActiveTrue(pageable);
    }


    public void deleteProjectById(UUID projectId)
    {
        projectsRepository.deleteProjectById(projectId);
    }
    public boolean doesProjectWithNameExistForUpdate(UUID id,String name)
    {
       return projectsRepository.doesProjectWithNameExistForUpdate(id,name);
    }
    public List<Projects> getAllProjects()
    {
        return projectsRepository.getAllProjects();
    }
}
