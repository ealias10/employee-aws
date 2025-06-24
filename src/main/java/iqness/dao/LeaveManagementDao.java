package iqness.dao;
import iqness.model.LeaveManagement;
import iqness.repository.LeaveManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

@Repository
public class LeaveManagementDao {

    @Autowired
    LeaveManagementRepository leaveManagementRepository;

    public  LeaveManagement getLeaveByDate(long fromDate, long toDate, UUID employeeId)
    {
        return leaveManagementRepository.getLeaveByDate(fromDate,toDate,employeeId);
    }

    public  LeaveManagement getLeaveByDateAndLeaveId(long fromDate, long toDate, UUID employeeId,UUID leaveId)
    {
        return leaveManagementRepository.getLeaveByDateAndLeaveId(fromDate,toDate,employeeId,leaveId);
    }
    public  LeaveManagement save(LeaveManagement leaveManagement)
    {
        return leaveManagementRepository.save(leaveManagement);
    }

    public Page<LeaveManagement> listLeaveAll(Pageable pageable)
    {
        return leaveManagementRepository.listLeaveByAdmin(pageable);
    }

    public Page<LeaveManagement> getlistLeaveByEmployeeName(String name, Pageable pageable)
    {
        return leaveManagementRepository.getlistLeaveByEmployeeName(name,pageable);
    }

    public Page<LeaveManagement>  listLeaveByUser(UUID employeeId, Pageable pageable)
    {
        return leaveManagementRepository.listLeaveByUser(employeeId,pageable);
    }
    public Page<LeaveManagement>  listLeaveByUserReason(String search,UUID employeeId, Pageable pageable)
    {
        return leaveManagementRepository.listLeaveByUserReason(search,employeeId,pageable);
    }

    public LeaveManagement getLeaveById(UUID leaveId)
    {
        return leaveManagementRepository.getById(leaveId);
    }

}
