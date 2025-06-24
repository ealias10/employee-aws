package iqness.mapper;

import com.amazonaws.services.cloudwatchevidently.model.Project;
import com.amazonaws.services.dynamodbv2.xspec.S;
import iqness.model.Assets;
import iqness.model.Employee;
import iqness.model.LeaveManagement;
import iqness.model.Projects;
import iqness.request.LeaveManagementRequest;
import iqness.request.ProjectsCreateRequest;
import iqness.vo.AssetsVO;
import iqness.vo.EmployeeVO;
import iqness.vo.ProjectsVO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.currentTimeMillis;

public class ProjectMapper {



    public static Projects createProject(ProjectsCreateRequest request,String projectId,long eta,Employee employee)
    {
        return
                Projects.builder()
                        .projectName(request.getProjectName())
                        .status("create")
                        .projId(projectId)
                        .employee(employee)
                        .active(true)
                        .language(request.getLanguage())
                        .etaDate(eta)
                        .build();
    }

    public static ProjectsVO createProjectsVO(Projects request)
    {
        return
                ProjectsVO.builder()
                        .projectName(request.getProjectName())
                        .projectId(request.getId())
                        .projId(request.getProjId())
                        .status(request.getStatus())
                        .etaDate(getLongConverToDate(request.getEtaDate()))
                        .empname(request.getEmployee().getName())
                        .language(request.getLanguage())
                        .email(request.getEmployee().getEmail())
                        .empId(request.getEmployee().getEmpId())
                        .build();
    }

    public static List<ProjectsVO> createListOfProjectsVO(List<Projects> projectList)
    {
        return projectList.stream()
                .map(
                        projects ->
                                new ProjectsVO(
                                        projects.getId(),
                                        projects.getProjId(),
                                        projects.getProjectName(),
                                        projects.getStatus(),
                                        getLongConverToDate(projects.getEtaDate()),
                                        projects.getEmployee().getName(),
                                        projects.getLanguage(),
                                        null,
                                        null
                                ))
                .collect(Collectors.toList());

    }
    public static Projects updateProjects(Projects projects, ProjectsCreateRequest request,long eta,Employee employee)
    {
        projects.setProjectName(request.getProjectName());
        projects.setEmployee(employee);
        projects.setEtaDate(eta);
        projects.setLanguage(request.getLanguage());
        return projects;
    }
    public static String getLongConverToDate(Long value)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(value));
    }

    public static List<ProjectsVO> createProjectsVOList(List<Projects> projectsList){
        return projectsList.stream().map(ProjectMapper::createProjectsVO).collect(Collectors.toList());
    }

}
