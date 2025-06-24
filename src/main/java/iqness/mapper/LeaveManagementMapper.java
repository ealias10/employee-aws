package iqness.mapper;

import iqness.model.Employee;
import iqness.model.LeaveManagement;
import iqness.request.LeaveManagementRequest;
import iqness.vo.LeaveManagementVO;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.currentTimeMillis;

public class LeaveManagementMapper {

    public static LeaveManagement applyLeave(LeaveManagementRequest request, long fromDate, long toDate, Employee employee)
    {
        return
        LeaveManagement.builder()
                .fromDate(fromDate)
                .toDate(toDate)
                .employee(employee)
                .active(true)
                .leaveApplyDate(currentTimeMillis())
                .reason(request.getReason())
                .build();
    }
    public static LeaveManagement applyLeaveUpdate(LeaveManagement request, long fromDate, long toDate,String reason)
    {
        request.setFromDate(fromDate);
        request.setToDate(toDate);
        request.setReason(reason);
        return request;
    }

    public static LeaveManagementVO applyLeaveVO(LeaveManagement request)
    {
        return
                LeaveManagementVO.builder()
                        .leaveManagementId(request.getId())
                         .fromDate(getFromToDateDate(request.getFromDate()))
                        .toDate(getFromToDateDate(request.getToDate()))
                        .employeeName(request.getEmployee().getName())
                        .reason(request.getReason())
                        .status(getLeaveStatus(request.getStatus(),request.getActive()))
                        .empId(request.getEmployee().getEmpId())
                        .createdAt(getFromToDateDate(request.getCreatedAt()))
                        .leaveApproved(getLeaveApproved(request.getStatus(),request.getActive()))
                        .build();
    }

    public static String getLeaveStatus(Boolean status,Boolean active)
    {

        if(active==true) {
            if (status == null)
                return "Pending";
            else if (status == true)
                return "Approved";
            else
                return "Rejected";
        }
        else
        {
            return "Leave Cancel";
        }
    }


    public static List<LeaveManagementVO> createListOfLeaveManagementVO(List<LeaveManagement> leaveList) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        return leaveList.stream()
                .map(
                        leave ->
                                new LeaveManagementVO(
                                 leave.getId(),
                                 getFromToDateDate(leave.getFromDate()),
                                 getFromToDateDate(leave.getToDate()),
                                 leave.getReason(),
                                 getLeaveStatus(leave.getStatus(),leave.getActive()),
                                 leave.getEmployee().getName(),
                                 leave.getEmployee().getEmpId(),
                                 getFromToDateDate(leave.getCreatedAt()),
                                 getLeaveApproved(leave.getStatus(),leave.getActive()),
                                 getButtonColor(leave.getStatus()),
                                 getTextColor(leave.getStatus(),leave.getActive())
                                ))
                .collect(Collectors.toList());
    }
    public static String getTextColor(Boolean status,Boolean active)
    {
        if(active==false)
           return "delete-text";
        if(status == null)
            return "pending-text";
        else if (status==true)
            return "change-text";
        else
            return "cancel-text";

    }
    public static Boolean getLeaveApproved(Boolean value,Boolean active)
    {
        if(active==true) {
            if (value != null) {
                return true;
            } else {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    public static String getButtonColor(Boolean status)
    {

        if(status == null)
            return "pending-button";
        else if (status==true)
            return "change-button";
        else
            return "cancel-button";
    }
    public static String getFromToDateDate(Long value)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(value==0)
            return "";
        else
            return sdf.format(new Date(value));
    }

}
