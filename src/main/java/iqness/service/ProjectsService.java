package iqness.service;

import iqness.dao.EmployeeDao;
import iqness.dao.ModulesDao;
import iqness.dao.ProjectsDao;
import iqness.exception.*;
import iqness.mapper.EmployeeMapper;
import iqness.mapper.ModulesMapper;
import iqness.mapper.ProjectMapper;
import iqness.model.Employee;
import iqness.model.Modules;
import iqness.model.Projects;
import iqness.request.ModulesAdminUpdateRequest;
import iqness.request.ModulesCreateRequest;
import iqness.request.ModulesUserUpdateRequest;
import iqness.request.ProjectsCreateRequest;
import iqness.utils.Utility;
import iqness.vo.EmployeeVO;
import iqness.vo.ModulesVO;
import iqness.vo.PaginatedResponseVOAndCount;
import iqness.vo.ProjectsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class ProjectsService {

    @Autowired
    private ProjectsDao projectsDao;

    @Autowired
    private ModulesDao modulesDao;

    @Autowired
    private EmployeeDao employeeDao;

    public ProjectsVO createProject(ProjectsCreateRequest request) throws ProjectsExistsException, ParseException, EmployeeNotFoundException {
        try {
            Projects existingProjects = projectsDao.getProjectsByName(request.getProjectName());
            if (existingProjects != null) {
                throw new ProjectsExistsException(request.getProjectName());
            }
            Employee existingEmployee = employeeDao.getEmployeeByEmpId(request.getEmployeeId());
            if (existingEmployee == null) {
                throw new EmployeeNotFoundException(request.getEmployeeId());
            }
            String projectId=getProjectId();
            long eta=getDateConvertToLong(request.getEtaDate());
            Projects project= ProjectMapper.createProject(request,projectId,eta,existingEmployee);
            Projects savedProjects = projectsDao.saveProjects(project);
            log.info("Create Project successfully, Request: {}", request);
            return ProjectMapper.createProjectsVO(savedProjects);
        }
        catch (Exception e)
        {
            log.error("Error while creating Project,  Request: {}", request);
            throw e;
        }
    }


    public ModulesVO createModules(ModulesCreateRequest request) throws ProjectsExistsException, ParseException, EmployeeNotFoundException, ModulesExistsException, ProjectsNotFoundException {
        try {
            Modules existingModules = modulesDao.getModulesByName(request.getModulesName());
            if (existingModules != null) {
                throw new ModulesExistsException(request.getModulesName());
            }
            Employee existingEmployee = employeeDao.getEmployeeByEmpId(request.getEmployeeId());
            if (existingEmployee == null) {
                throw new EmployeeNotFoundException(request.getEmployeeId());
            }
            Projects existingProjects=projectsDao.getProjectsByProjectId(request.getProjectId());
            if (existingProjects == null) {
                throw new ProjectsNotFoundException(request.getProjectId());
            }
            long eta=getDateConvertToLong(request.getEtaDate());
            Modules modules= ModulesMapper.createModules(request,eta,existingEmployee,existingProjects);
            Modules savedModules = modulesDao.saveModules(modules);
            log.info("Create Modules successfully, Request: {}", request);
            return ModulesMapper.createModulesVO(savedModules);
        }
        catch (Exception e)
        {
            log.error("Error while creating Modules,  Request: {}", request);
            throw e;
        }
    }

    public ProjectsVO getProjectById(UUID projectId) throws ProjectsNotFoundException {
        try {
            Projects existingProjects = projectsDao.getProjectsById(projectId);
            if (existingProjects == null) {
                throw new ProjectsNotFoundException(projectId);
            }
            log.info("Retrieved project details successfully, projectId: {}", projectId);
            return ProjectMapper.createProjectsVO(existingProjects);
        }
        catch (Exception e)
        {
            log.error("Error while fetching project details,projectId: {}",projectId);
            throw e;
        }

    }

    public ModulesVO getModulesById(UUID moduleId) throws ModulesNotFoundException {
        try {
            Modules existingModules = modulesDao.getModulesById(moduleId);
            if (existingModules == null) {
                throw new ModulesNotFoundException(moduleId);
            }
            log.info("Retrieved module details successfully, moduleId: {}", moduleId);
            return ModulesMapper.createModulesVO(existingModules);
        }
        catch (Exception e)
        {
            log.error("Error while fetching module details,moduleId: {}",moduleId);
            throw e;
        }
    }

    public PaginatedResponseVOAndCount<ProjectsVO> listProjectByAdmin(Integer offset, Integer limit, String search
    ) {
        try {
            Pageable pageable = PageRequest.of(offset - 1, limit);
            Page<Projects> projectList;
            if (search == null || search.trim() == "") {
                projectList = projectsDao.listProject(pageable);
            } else {
                projectList = projectsDao.listProject(pageable);
            }
            log.info("Retrieved project list successfully");
            var projectVOList = ProjectMapper.createListOfProjectsVO(projectList.toList());
            return new PaginatedResponseVOAndCount<>(
                    projectList.getTotalElements(), projectVOList);
        } catch (Exception e) {
            log.error("Error while Retrieved project list");
            throw e;
        }
    }


    public PaginatedResponseVOAndCount<ModulesVO> listModulesByAdmin(Integer offset, Integer limit, String search
    ) {
        try {
            Pageable pageable = PageRequest.of(offset - 1, limit);
            Page<Modules> moduleList;
            if (search == null || search.trim() == "") {
                moduleList = modulesDao.listAllModule(pageable);
            } else {
                moduleList = modulesDao.listAllModule(pageable);
            }
            log.info("Retrieved module list successfully");
            var moduleVOList = ModulesMapper.createListOfModulesVO(moduleList.toList());
            return new PaginatedResponseVOAndCount<>(
                    moduleList.getTotalElements(), moduleVOList);
        } catch (Exception e) {
            log.error("Error while Retrieved module list");
            throw e;
        }
    }
    public PaginatedResponseVOAndCount<ModulesVO>  listModulesByUser(Integer offset, Integer limit, String search
    ) throws EmployeeNotFoundException {
        String userIdFromToken = null;
        try {
            userIdFromToken = Utility.getUserId();
            UUID userId= UUID.fromString(userIdFromToken);
            Pageable pageable = PageRequest.of(offset - 1, limit);
            Page<Modules> moduleList;
            Employee existingEmployee=employeeDao.getEmployeeByUserId(userId);
            if (existingEmployee == null) {
                throw new EmployeeNotFoundException(existingEmployee.getId());
            }
            if (search == null || search.trim() == "") {
                moduleList = modulesDao.listUserModule(existingEmployee.getId(),pageable);
            } else {
                moduleList = modulesDao.listUserModule(existingEmployee.getId(),pageable);
            }
            log.info("Retrieved module list successfully");
            var moduleVOList = ModulesMapper.createListOfModulesVO(moduleList.toList());
            return new PaginatedResponseVOAndCount<>(
                    moduleList.getTotalElements(), moduleVOList);
        } catch (Exception e) {
            log.error("Error while Retrieved module list");
            throw e;
        }
    }

    public void deleteModule(UUID moduleId) throws ModulesNotFoundException {
        try {
            Modules existingModules = modulesDao.getModulesById(moduleId);
            if (existingModules == null) {
                throw new ModulesNotFoundException(moduleId);
            }
            modulesDao.deleteModuleById(moduleId);
            log.info(
                    "Deleted module successfully, moduleId: {}", moduleId);
        } catch (Exception e) {
            log.error(
                    "Error while deleting module,moduleId: {} ", moduleId);
            throw e;
        }
    }


   public ModulesVO updateModuleByUser(ModulesUserUpdateRequest request,UUID moduleId) throws ModulesNotFoundException, ParseException {
       try
       {
           Modules existingModules = modulesDao.getModulesById(moduleId);
           if (existingModules == null) {
               throw new ModulesNotFoundException(moduleId);
           }
           long eta=getDateConvertToLong(request.getEtaDate());
           Modules modulesUpdate = ModulesMapper.updateByUserModules(request,eta,existingModules);
           Modules updateModules = modulesDao.saveModules(modulesUpdate);
           log.info("Updated module successfully, moduleId: {} Request: {}", moduleId, request);
           return ModulesMapper.createModulesVO(updateModules);
       } catch (Exception e) {
           log.error(
                   "Error while update module,moduleId: {} ", moduleId);
           throw e;
       }
   }
    public ModulesVO updateModuleByAdmin(ModulesAdminUpdateRequest request,UUID moduleId) throws ModulesNotFoundException, ParseException, ModulesExistsException, ProjectsNotFoundException, EmployeeNotFoundException {
        try
        {
            Modules existingModules = modulesDao.getModulesById(moduleId);
            if (existingModules == null) {
                throw new ModulesNotFoundException(moduleId);
            }
            boolean moduleWithNameExistForUpdate = modulesDao.doesModuleWithNameExistForUpdate(moduleId,request.getModulesName());
            if (moduleWithNameExistForUpdate) {
                throw new ModulesExistsException(request.getModulesName());
            }
            Employee existingEmployee = employeeDao.getEmployeeByEmpId(request.getEmployeeId());
            if (existingEmployee == null) {
                throw new EmployeeNotFoundException(request.getEmployeeId());
            }
            Projects existingProjects=projectsDao.getProjectsByProjectId(request.getProjectId());
            if (existingProjects == null) {
                throw new ProjectsNotFoundException(request.getProjectId());
            }
            Modules modulesUpdate = ModulesMapper.updateByAdminModules(request,existingModules,existingEmployee,existingProjects);
            Modules updateModules = modulesDao.saveModules(modulesUpdate);
            log.info("Updated module successfully, moduleId: {} Request: {}", moduleId, request);
            return ModulesMapper.createModulesVO(updateModules);
        } catch (Exception e) {
            log.error(
                    "Error while update module,moduleId: {} ", moduleId);
            throw e;
        }
    }
    public void deleteProject(UUID projectId) throws ProjectsNotFoundException {
        try {
            Projects existingProjects = projectsDao.getProjectsById(projectId);
            if (existingProjects == null) {
                throw new ProjectsNotFoundException(projectId);
            }
            projectsDao.deleteProjectById(projectId);
            log.info(
                    "Deleted project successfully, projectId: {}", projectId);
        } catch (Exception e) {
            log.error(
                    "Error while deleting project,projectId: {} ", projectId);
            throw e;
        }
    }

    public ProjectsVO updateProject(ProjectsCreateRequest request,UUID projectId) throws ProjectsExistsException, ProjectsNotFoundException, ParseException, EmployeeNotFoundException {
        try
        {
            Projects existingProjects = projectsDao.getProjectsById(projectId);
            if (existingProjects == null) {
                throw new ProjectsNotFoundException(projectId);
            }
            boolean projectWithNameExistForUpdate = projectsDao.doesProjectWithNameExistForUpdate(projectId, request.getProjectName());
            if (projectWithNameExistForUpdate) {
                throw new ProjectsExistsException(request.getProjectName());
            }
            Employee existingEmployee = employeeDao.getEmployeeByEmpId(request.getEmployeeId());
            if (existingEmployee == null) {
                throw new EmployeeNotFoundException(request.getEmployeeId());
            }
            long eta=getDateConvertToLong(request.getEtaDate());
            Projects projectsUpdate = ProjectMapper.updateProjects(existingProjects, request,eta,existingEmployee);
            Projects createProjects = projectsDao.saveProjects(projectsUpdate);
            log.info("Updated project successfully, projectId: {} Request: {}", projectId, request);
            return ProjectMapper.createProjectsVO(createProjects );
        } catch (Exception e) {
            log.error(
                    "Error while update project,projectId: {} ", projectId);
            throw e;
        }
    }

    public List<ProjectsVO> getAllProject() {
        List<Projects> projectsList = projectsDao.getAllProjects();
        return ProjectMapper.createProjectsVOList(projectsList);
    }
    private String getProjectId()
    {
        int count=projectsDao.getProjectCount();
        return ""+count;
    }
    private long getDateConvertToLong(String eta) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(eta);
        return  date.getTime();
    }
}
