package iqness.controller;

import io.swagger.v3.oas.annotations.Parameter;
import iqness.exception.*;
import iqness.request.HolidayManagementRequest;
import iqness.request.LeaveManagementRequest;
import iqness.service.HolidayManagementService;
import iqness.service.LeaveManagementService;
import iqness.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.text.ParseException;
import java.util.UUID;

@RestController
@Slf4j
@Validated
@RequestMapping("/holiday")
public class HolidayManagementController {

    @Autowired
    private HolidayManagementService holidayManagementService;

    @PostMapping("/create")
    public ResponseEntity<ResponseVO<Object>> createHoliday(
            @RequestBody(required = true) HolidayManagementRequest request) throws HolidayManagementExistsException, ParseException {
        ResponseVO<Object> response = new ResponseVO<>();
        HolidayManagementVO holidayManagementVO = holidayManagementService.createHoliday(request);
        response.addData(holidayManagementVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{holidayId}/update")
    public ResponseEntity<ResponseVO<Object>> updateHoliday(@PathVariable("holidayId") UUID holidayId, @RequestBody(required = true) HolidayManagementRequest request) throws LeaveManagementExistsException, ParseException, EmployeeNotFoundException, HolidayManagementExistsException, HolidayManagementNotFountException {
        ResponseVO<Object> response = new ResponseVO<>();
        HolidayManagementVO holidayManagementVO = holidayManagementService.updateHolidayById(holidayId,request);
        response.addData(holidayManagementVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{holidayId}/details")
    public ResponseEntity<ResponseVO<Object>> getHoliDayById(@PathVariable("holidayId") UUID holidayId) throws HolidayManagementNotFountException {
        ResponseVO<Object> response = new ResponseVO<>();
        HolidayManagementVO holidayManagementVO = holidayManagementService.getHoliDayById(holidayId);
        response.addData(holidayManagementVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/list")
    public ResponseEntity<ResponseVO<HolidayManagementVO>> listHoliday(@Parameter(description = "offset, start from 1")
                                                                         @RequestParam(value = "offset", required = false, defaultValue = "1") @Min(1) Integer offset,
                                                                         @Parameter(description = "limit") @RequestParam(value = "limit", required = false, defaultValue = "10") @Min(1) Integer limit,
                                                                         @RequestParam(value = "search", required = false) String search)  {
        ResponseVO<HolidayManagementVO> response = new ResponseVO<>();
        PaginatedResponseVOAndCount<HolidayManagementVO> paginatedResponseVOAndCount = holidayManagementService.listHoliday(offset, limit, search);
        response.addPaginatedDataList(paginatedResponseVOAndCount.getData(), paginatedResponseVOAndCount.getTotalCount());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{holidayId}/delete")
    public ResponseEntity<ResponseVO<Object>> deleteHoliday(@PathVariable("holidayId") UUID holidayId) throws LeaveManagementNotFoundException, HolidayManagementNotFountException {
        ResponseVO<Object> response = new ResponseVO<>();
        holidayManagementService.deleteHoliday(holidayId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
