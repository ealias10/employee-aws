package iqness.controller;
import io.swagger.v3.oas.annotations.Parameter;
import iqness.exception.EmployeeNotFoundException;
import iqness.exception.LeaveManagementExistsException;
import iqness.exception.LeaveManagementNotFoundException;
import iqness.exception.UsersExistsException;
import iqness.request.LeaveManagementRequest;
import iqness.request.LeaveManagementStatusRequest;
import iqness.request.UserCreateRequest;
import iqness.service.EmployeeService;
import iqness.service.LeaveManagementService;
import iqness.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@Validated
@RequestMapping("/leavemanagement")
public class LeaveManagementController {

    @Autowired
    private LeaveManagementService leaveManagementService;

    @PostMapping("/create")
    public ResponseEntity<ResponseVO<Object>> applyLeave(
            @RequestBody(required = true) LeaveManagementRequest request) throws LeaveManagementExistsException, ParseException, EmployeeNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        LeaveManagementVO leaveManagementVO = leaveManagementService.applyLeave(request);
        response.addData(leaveManagementVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/listByAdmin")
    public ResponseEntity<ResponseVO<LeaveManagementVO>> listLeaveByAdmin(@Parameter(description = "offset, start from 1")
                                                               @RequestParam(value = "offset", required = false, defaultValue = "1") @Min(1) Integer offset,
                                                               @Parameter(description = "limit") @RequestParam(value = "limit", required = false, defaultValue = "10") @Min(1) Integer limit,
                                                               @RequestParam(value = "search", required = false) String search) {
        ResponseVO<LeaveManagementVO> response = new ResponseVO<>();
        PaginatedResponseVOAndCount<LeaveManagementVO> paginatedResponseVOAndCount = leaveManagementService.listLeaveByAdmin(offset, limit, search);
        response.addPaginatedDataList(paginatedResponseVOAndCount.getData(), paginatedResponseVOAndCount.getTotalCount());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/listByUser")
    public ResponseEntity<ResponseVO<LeaveManagementVO>> listLeaveByUser(@Parameter(description = "offset, start from 1")
                                                                          @RequestParam(value = "offset", required = false, defaultValue = "1") @Min(1) Integer offset,
                                                                          @Parameter(description = "limit") @RequestParam(value = "limit", required = false, defaultValue = "10") @Min(1) Integer limit,
                                                                          @RequestParam(value = "search", required = false) String search) throws EmployeeNotFoundException {
        ResponseVO<LeaveManagementVO> response = new ResponseVO<>();
        PaginatedResponseVOAndCount<LeaveManagementVO> paginatedResponseVOAndCount = leaveManagementService.listLeaveByUser(offset, limit, search);
        response.addPaginatedDataList(paginatedResponseVOAndCount.getData(), paginatedResponseVOAndCount.getTotalCount());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{leaveId}/details")
    public ResponseEntity<ResponseVO<Object>> getLeaveById(@PathVariable("leaveId") UUID leaveId) throws EmployeeNotFoundException, LeaveManagementNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        LeaveManagementVO leaveManagementVO = leaveManagementService.getLeaveById(leaveId);
        response.addData(leaveManagementVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{leaveId}/delete")
    public ResponseEntity<ResponseVO<Object>> deleteLeave(@PathVariable("leaveId") UUID leaveId) throws LeaveManagementNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        leaveManagementService.deleteLeave(leaveId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{leaveId}/approveleave")
    public ResponseEntity<ResponseVO<Object>> getLeaveApprovedById(@PathVariable("leaveId") UUID leaveId, @RequestBody(required = true) LeaveManagementStatusRequest request) throws EmployeeNotFoundException, LeaveManagementNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        LeaveManagementVO leaveManagementVO = leaveManagementService.getLeaveApprovedById(leaveId,request);
        response.addData(leaveManagementVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{leaveId}/update")
    public ResponseEntity<ResponseVO<Object>> updateLeaveById(@PathVariable("leaveId") UUID leaveId, @RequestBody(required = true) LeaveManagementRequest request) throws LeaveManagementExistsException, ParseException, EmployeeNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        LeaveManagementVO leaveManagementVO = leaveManagementService.updateLeaveById(leaveId,request);
        response.addData(leaveManagementVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
