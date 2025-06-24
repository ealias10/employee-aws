package iqness.mapper;

import iqness.model.Employee;
import iqness.model.Modules;
import iqness.model.Projects;
import iqness.request.ModulesAdminUpdateRequest;
import iqness.request.ModulesCreateRequest;
import iqness.request.ModulesUserUpdateRequest;
import iqness.vo.ModulesVO;
import iqness.vo.ProjectsVO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ModulesMapper {

   public static Modules createModules(ModulesCreateRequest request, long eta, Employee existingEmployee, Projects projects)
   {
       return Modules.builder()
               .employee(existingEmployee)
               .etaDate(eta)
               .note(request.getNote())
               .roles(request.getRoles())
               .active(true)
               .modulesName(request.getModulesName())
               .status("create")
               .projects(projects)
               .build();
   }

   public static ModulesVO createModulesVO(Modules modules)
   {
       return ModulesVO.builder()
               .moduleId(modules.getId())
               .empname(modules.getEmployee().getName())
               .etaDate(getLongConverToDate(modules.getEtaDate()))
               .modulesName(modules.getModulesName())
               .projectName(modules.getProjects().getProjectName())
               .roles(modules.getRoles())
               .status(modules.getStatus())
               .note(modules.getNote())
               .projId(modules.getProjects().getProjId())
               .empId(modules.getProjects().getProjId())
               .email(modules.getEmployee().getEmail())
               .build();
   }

    public static List<ModulesVO> createListOfModulesVO(List<Modules> modulesList)
    {
        return modulesList.stream()
                .map(
                        modules ->
                                new ModulesVO(
                                        modules.getId(),
                                        modules.getModulesName(),
                                        modules.getStatus(),
                                        modules.getRoles(),
                                        modules.getNote(),
                                        getLongConverToDate(modules.getEtaDate()),
                                        modules.getEmployee().getName(),
                                        modules.getProjects().getProjectName(),
                                        modules.getProjects().getProjId(),
                                        modules.getProjects().getProjId(),
                                        null
                                ))
                .collect(Collectors.toList());

    }

    public static Modules updateByUserModules(ModulesUserUpdateRequest request,long eta,Modules modules)
    {
       modules.setEtaDate(eta);
       modules.setNote(request.getNote());
       modules.setStatus(request.getStatus());
       return modules;
    }

    public static Modules updateByAdminModules(ModulesAdminUpdateRequest request, Modules modules, Employee employee, Projects projects)
    {
        modules.setModulesName(request.getModulesName());
        modules.setEmployee(employee);
        modules.setNote(request.getNote());
        modules.setProjects(projects);
        modules.setStatus(request.getStatus());
        modules.setRoles(request.getRoles());
        return modules;
    }
    public static String getLongConverToDate(Long value)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(value));
    }
}
