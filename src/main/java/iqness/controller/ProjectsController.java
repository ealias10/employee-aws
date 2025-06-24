package iqness.controller;

import io.swagger.v3.oas.annotations.Parameter;
import iqness.exception.*;
import iqness.model.Modules;
import iqness.request.*;
import iqness.service.ProjectsService;
import iqness.vo.ResponseVO;
import iqness.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@Validated
@RequestMapping("/project")
public class ProjectsController {

    @Autowired
    private ProjectsService projectsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseVO<Object>> createProject(
            @RequestBody(required = true) ProjectsCreateRequest request) throws ProjectsExistsException, ParseException, EmployeeNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        ProjectsVO projectsVO = projectsService.createProject(request);
        response.addData(projectsVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/modules/create")
    public ResponseEntity<ResponseVO<Object>> createModules(
            @RequestBody(required = true) ModulesCreateRequest request) throws ModulesExistsException, ProjectsExistsException, ParseException, EmployeeNotFoundException, ProjectsNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        ModulesVO modulesVO = projectsService.createModules(request);
        response.addData(modulesVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/modules/{moduleId}/details")
    public ResponseEntity<ResponseVO<Object>> getModulesById(@PathVariable("moduleId") UUID moduleId) throws ModulesNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        ModulesVO modulesVO = projectsService.getModulesById(moduleId);
        response.addData(modulesVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/modules/listByAdmin")
    public ResponseEntity<ResponseVO<ModulesVO>> listModulesByAdmin(@Parameter(description = "offset, start from 1")
                                                                     @RequestParam(value = "offset", required = false, defaultValue = "1") @Min(1) Integer offset,
                                                                     @Parameter(description = "limit") @RequestParam(value = "limit", required = false, defaultValue = "10") @Min(1) Integer limit,
                                                                     @RequestParam(value = "search", required = false) String search) {
        ResponseVO<ModulesVO> response = new ResponseVO<>();
        PaginatedResponseVOAndCount<ModulesVO> paginatedResponseVOAndCount = projectsService.listModulesByAdmin(offset, limit, search);
        response.addPaginatedDataList(paginatedResponseVOAndCount.getData(), paginatedResponseVOAndCount.getTotalCount());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/modules/listByUser")
    public ResponseEntity<ResponseVO<ModulesVO>> listModulesByUser(@Parameter(description = "offset, start from 1")
                                                                    @RequestParam(value = "offset", required = false, defaultValue = "1") @Min(1) Integer offset,
                                                                    @Parameter(description = "limit") @RequestParam(value = "limit", required = false, defaultValue = "10") @Min(1) Integer limit,
                                                                    @RequestParam(value = "search", required = false) String search) throws EmployeeNotFoundException {
        ResponseVO<ModulesVO> response = new ResponseVO<>();
        PaginatedResponseVOAndCount<ModulesVO> paginatedResponseVOAndCount = projectsService.listModulesByUser(offset, limit, search);
        response.addPaginatedDataList(paginatedResponseVOAndCount.getData(), paginatedResponseVOAndCount.getTotalCount());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/modules/{moduleId}/delete")
    public ResponseEntity<ResponseVO<Object>> deleteModule(@PathVariable("moduleId") UUID moduleId) throws ModulesNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        projectsService.deleteModule(moduleId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/modules/{moduleId}/updateByUser")
    public ResponseEntity<ResponseVO<Object>> updateModuleByUser(@PathVariable("moduleId") UUID moduleId,
                                                                 @RequestBody(required = true) @Valid ModulesUserUpdateRequest request) throws ModulesNotFoundException, ParseException {
        ResponseVO<Object> response = new ResponseVO<>();
        ModulesVO modulesVO = projectsService.updateModuleByUser(request, moduleId);
        response.addData(modulesVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/modules/{moduleId}/updateByAdmin")
    public ResponseEntity<ResponseVO<Object>> updateModuleByAdmin(@PathVariable("moduleId") UUID moduleId,
                                                                 @RequestBody(required = true) @Valid ModulesAdminUpdateRequest request) throws ModulesExistsException, ModulesNotFoundException, ProjectsNotFoundException, ParseException, EmployeeNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        ModulesVO modulesVO = projectsService.updateModuleByAdmin(request, moduleId);
        response.addData(modulesVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{projectId}/details")
    public ResponseEntity<ResponseVO<Object>> getProjectById(@PathVariable("projectId") UUID projectId) throws ProjectsNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        ProjectsVO projectsVO = projectsService.getProjectById(projectId);
        response.addData(projectsVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/listByAdmin")
    public ResponseEntity<ResponseVO<ProjectsVO>> listProjectByAdmin(@Parameter(description = "offset, start from 1")
                                                                          @RequestParam(value = "offset", required = false, defaultValue = "1") @Min(1) Integer offset,
                                                                          @Parameter(description = "limit") @RequestParam(value = "limit", required = false, defaultValue = "10") @Min(1) Integer limit,
                                                                          @RequestParam(value = "search", required = false) String search) {
        ResponseVO<ProjectsVO> response = new ResponseVO<>();
        PaginatedResponseVOAndCount<ProjectsVO> paginatedResponseVOAndCount = projectsService.listProjectByAdmin(offset, limit, search);
        response.addPaginatedDataList(paginatedResponseVOAndCount.getData(), paginatedResponseVOAndCount.getTotalCount());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}/delete")
    public ResponseEntity<ResponseVO<Object>> deleteProject(@PathVariable("projectId") UUID projectId) throws ProjectsNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        projectsService.deleteProject(projectId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{projectId}/update")
    public ResponseEntity<ResponseVO<Object>> updateProject(@PathVariable("projectId") UUID projectId,
        @RequestBody(required = true) @Valid ProjectsCreateRequest request) throws ProjectsExistsException, ProjectsNotFoundException, ParseException, EmployeeNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        ProjectsVO projectsVO = projectsService.updateProject(request, projectId);
        response.addData(projectsVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<ResponseVO<Object>> getAllProject()  {
        ResponseVO<Object> response = new ResponseVO<>();
        List<ProjectsVO> projectsVO = projectsService.getAllProject();
        response.addData(projectsVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
