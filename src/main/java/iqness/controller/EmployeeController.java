package iqness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import iqness.exception.EmployeeExistsException;
import iqness.exception.EmployeeNotFoundException;
import iqness.exception.ImageExistsException;
import iqness.request.EmployeeCreateRequest;
import iqness.service.EmployeeService;
import iqness.vo.AssetsVO;
import iqness.vo.EmployeeVO;
import iqness.vo.PaginatedResponseVOAndCount;
import iqness.vo.ResponseVO;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@Validated
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/create")
    public ResponseEntity<ResponseVO<Object>> createEmployee(
            @RequestPart("body") String request,@RequestPart("image")MultipartFile image) throws EmployeeExistsException, ParseException, IOException, ImageExistsException {
        ResponseVO<Object> response = new ResponseVO<>();
        var objectMapper = new ObjectMapper();
        EmployeeVO employeeVO = employeeService.createEmployee(objectMapper.readValue(request, EmployeeCreateRequest.class),image);
        response.addData(employeeVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseVO<EmployeeVO>> listEmployee(@Parameter(description = "offset, start from 1")
                                                                @RequestParam(value = "offset", required = false, defaultValue = "1") @Min(1) Integer offset,
                                                                @Parameter(description = "limit") @RequestParam(value = "limit", required = false, defaultValue = "10") @Min(1) Integer limit,
                                                                @RequestParam(value = "search", required = false) String search,
                                                                @RequestParam(value = "filter", required = false) String filter) {
        ResponseVO<EmployeeVO> response = new ResponseVO<>();
        PaginatedResponseVOAndCount<EmployeeVO> paginatedResponseVOAndCount = employeeService.listEmployee(offset, limit, search,filter);
        response.addPaginatedDataList(paginatedResponseVOAndCount.getData(), paginatedResponseVOAndCount.getTotalCount());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}/delete")
    public ResponseEntity<ResponseVO<Object>> deleteEmployee(@PathVariable("employeeId") UUID employeeId) throws EmployeeNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{employeeId}/details")
    public ResponseEntity<ResponseVO<Object>> getEmployeeById(@PathVariable("employeeId") UUID employeeId) throws EmployeeNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        EmployeeVO employeeVO = employeeService.getEmployeeById(employeeId);
        response.addData(employeeVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{employeeId}/update")
    public ResponseEntity<ResponseVO<Object>> updateEmployee(@PathVariable("employeeId") UUID employeeId, @RequestBody(required = true) @Valid EmployeeCreateRequest request) throws EmployeeExistsException, EmployeeNotFoundException, ParseException {
        ResponseVO<Object> response = new ResponseVO<>();
        EmployeeVO employeeVO = employeeService.updateEmployee(request, employeeId);
        response.addData(employeeVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<ResponseVO<Object>> getAllEmployee()  {
        ResponseVO<Object> response = new ResponseVO<>();
        List<EmployeeVO> employeeVO = employeeService.getEmployeeList();
        response.addData(employeeVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
