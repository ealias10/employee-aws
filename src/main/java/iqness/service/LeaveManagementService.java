package iqness.service;

import iqness.dao.EmployeeDao;
import iqness.dao.LeaveManagementDao;
import iqness.exception.*;
import iqness.mapper.EmployeeMapper;
import iqness.mapper.LeaveManagementMapper;
import iqness.mapper.UserMapper;
import iqness.model.*;
import iqness.request.LeaveManagementRequest;
import iqness.request.LeaveManagementStatusRequest;
import iqness.request.UserCreateRequest;
import iqness.utils.Utility;
import iqness.vo.EmployeeVO;
import iqness.vo.LeaveManagementVO;
import iqness.vo.PaginatedResponseVOAndCount;
import iqness.vo.UsersVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.management.relation.RoleNotFoundException;
import javax.transaction.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.fromString;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class LeaveManagementService {

    @Autowired
    private LeaveManagementDao leavemanagementDao;

    @Autowired
    private EmployeeDao employeeDao;

    public LeaveManagementVO applyLeave(LeaveManagementRequest request) throws LeaveManagementExistsException, ParseException, EmployeeNotFoundException {
        String userIdFromToken = null;
        try {
            userIdFromToken = Utility.getUserId();
            Employee existingEmployee=employeeDao.getEmployeeByUserId(fromString(userIdFromToken));
            if (existingEmployee == null) {
                throw new EmployeeNotFoundException();
            }
            LeaveManagement existingLeaveManagement = leavemanagementDao.getLeaveByDate(getDate(request.getFromDate()),getDate(request.getToDate()),existingEmployee.getId());
            if (existingLeaveManagement != null) {
                throw new LeaveManagementExistsException(request.getFromDate(),request.getToDate());
            }
            LeaveManagement applyLeaveManagement= LeaveManagementMapper.applyLeave(request,getDate(request.getFromDate()),getDate(request.getToDate()),existingEmployee);
            LeaveManagement leaveManagementSave=leavemanagementDao.save(applyLeaveManagement);
            return LeaveManagementMapper.applyLeaveVO(leaveManagementSave);
        } catch (Exception e) {
            log.error("Error while creating leave,  Request: {}", request);
            throw e;
        }
    }

    public LeaveManagementVO updateLeaveById(UUID leaveId,LeaveManagementRequest request) throws LeaveManagementExistsException, ParseException, EmployeeNotFoundException {
        String userIdFromToken = null;
        try {
            userIdFromToken = Utility.getUserId();
            Employee existingEmployee=employeeDao.getEmployeeByUserId(fromString(userIdFromToken));
            if (existingEmployee == null) {
                throw new EmployeeNotFoundException();
            }
            LeaveManagement existingLeaveManagement = leavemanagementDao.getLeaveByDateAndLeaveId(getDate(request.getFromDate()),getDate(request.getToDate()),existingEmployee.getId(),leaveId);
            if (existingLeaveManagement != null) {
                throw new LeaveManagementExistsException(request.getFromDate(),request.getToDate());
            }
            LeaveManagement updateLeaveManagement=leavemanagementDao.getLeaveById(leaveId);
            LeaveManagement applyLeaveManagement= LeaveManagementMapper.applyLeaveUpdate(updateLeaveManagement,getDate(request.getFromDate()),getDate(request.getToDate()),request.getReason());
            LeaveManagement leaveManagementSave=leavemanagementDao.save(applyLeaveManagement);
            return LeaveManagementMapper.applyLeaveVO(leaveManagementSave);
        } catch (Exception e) {
            log.error("Error while creating leave,  Request: {}", request);
            throw e;
        }
    }
    public PaginatedResponseVOAndCount<LeaveManagementVO> listLeaveByAdmin(Integer offset, Integer limit, String search) {
        try {
            Pageable pageable = PageRequest.of(offset - 1, limit);
            Page<LeaveManagement> leaveList;
            if (search == null || search.trim() == "") {
                leaveList = leavemanagementDao.listLeaveAll(pageable);
            } else {
                leaveList = leavemanagementDao.getlistLeaveByEmployeeName(search.toUpperCase(), pageable);
            }
            log.info("Retrieved leave list successfully");
            var leaveManagementVOList = LeaveManagementMapper.createListOfLeaveManagementVO(leaveList.toList());
            return new PaginatedResponseVOAndCount<>(
                    leaveList.getTotalElements(), leaveManagementVOList);
        } catch (Exception e) {
            log.error("Error while Retrieved leave list");
            throw e;
        }
    }

    public PaginatedResponseVOAndCount<LeaveManagementVO> listLeaveByUser(Integer offset, Integer limit, String search) throws EmployeeNotFoundException {
        String userIdFromToken = null;
        try {
            userIdFromToken = Utility.getUserId();
            Employee existingEmployee=employeeDao.getEmployeeByUserId(fromString(userIdFromToken));
            if (existingEmployee == null) {
                throw new EmployeeNotFoundException();
            }
            Pageable pageable = PageRequest.of(offset - 1, limit);
            Page<LeaveManagement> leaveList;
            if (search == null || search.trim() == "") {
                leaveList = leavemanagementDao.listLeaveByUser(existingEmployee.getId(),pageable);
            } else {
                leaveList = leavemanagementDao.listLeaveByUserReason(search.toUpperCase(),existingEmployee.getId(),pageable);
            }
            log.info("Retrieved leave list successfully");
            var leaveManagementVOList = LeaveManagementMapper.createListOfLeaveManagementVO(leaveList.toList());
            return new PaginatedResponseVOAndCount<>(
                    leaveList.getTotalElements(), leaveManagementVOList);
        } catch (Exception e) {
            log.error("Error while Retrieved leave list");
            throw e;
        }
    }

    public LeaveManagementVO getLeaveById(UUID leaveId) throws EmployeeNotFoundException, LeaveManagementNotFoundException {
        try {
            LeaveManagement existingLeaveManagement =leavemanagementDao.getLeaveById(leaveId);
            if (existingLeaveManagement == null) {
                throw new LeaveManagementNotFoundException(leaveId);
            }
            log.info("Retrieved leave details successfully, leaveId: {}", leaveId);
           return LeaveManagementMapper.applyLeaveVO(existingLeaveManagement);
        } catch (Exception e) {
            log.error("Error while fetching leave details, leaveId: {}",leaveId);
            throw e;
        }
    }

    public LeaveManagementVO  getLeaveApprovedById(UUID leaveId, LeaveManagementStatusRequest request) throws EmployeeNotFoundException, LeaveManagementNotFoundException {
        try {
            LeaveManagement existingLeaveManagement =leavemanagementDao.getLeaveById(leaveId);
            if (existingLeaveManagement == null) {
                throw new LeaveManagementNotFoundException(leaveId);
            }
            existingLeaveManagement.setStatus(request.isStatus());
            LeaveManagement leaveManagementSave=leavemanagementDao.save(existingLeaveManagement);
            log.info("Retrieved leave details successfully, leaveId: {}", leaveId);
            return LeaveManagementMapper.applyLeaveVO(leaveManagementSave);

        } catch (Exception e) {
            log.error("Error while fetching leave details, leaveId: {}",leaveId);
            throw e;
        }
    }

    public void deleteLeave(UUID leaveId) throws LeaveManagementNotFoundException {
        try {

            LeaveManagement existingLeaveManagement =leavemanagementDao.getLeaveById(leaveId);
            if (existingLeaveManagement == null) {
                throw new LeaveManagementNotFoundException(leaveId);
            }
            existingLeaveManagement.setActive(false);
            LeaveManagement saveLeaveManagement=leavemanagementDao.save(existingLeaveManagement);
            log.info(
                    "Deleted leave successfully, leaveId: {}", leaveId);
        } catch (Exception e) {
            log.error(
                    "Error while deleting employee,employeeId: {} ", leaveId);
            throw e;
        }
    }
    private Long getDate(String fromandtodate) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fromandtodate);
        return  date.getTime();
    }

}
