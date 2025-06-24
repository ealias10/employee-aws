package iqness.repository;


import iqness.model.LeaveManagement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface LeaveManagementRepository extends JpaRepository<LeaveManagement, UUID> {

    @Query("select l from LeaveManagement l inner join Employee e on(e.id=l.employee.id) where e.id=:id and l.fromDate between :from_date and :to_date or  l.toDate BETWEEN  :from_date and :to_date")
    LeaveManagement getLeaveByDate(@Param("from_date")long fromDate,@Param("to_date")long toDate,@Param("id")UUID employeeId);

    @Query("select l from LeaveManagement l inner join Employee e on(e.id=l.employee.id) where e.id=:id  and (l.fromDate between :from_date and :to_date or  l.toDate BETWEEN  :from_date and :to_date) and l.id!=:leaveId")
    public  LeaveManagement getLeaveByDateAndLeaveId(@Param("from_date")long fromDate,@Param("to_date")long toDate,@Param("id")UUID employeeId,@Param("leaveId")UUID leaveId);
    @Query("select l from LeaveManagement l where (upper(l.employee.name) like %:name%) and l.active='true' order by l.leaveApplyDate DESC")
    Page<LeaveManagement> getlistLeaveByEmployeeName(@Param("name")String name, Pageable pageable);

    @Query("select l from LeaveManagement l inner join Employee e on(e.id=l.employee.id) where e.id=:id order by l.leaveApplyDate DESC")
    Page<LeaveManagement> listLeaveByUser(@Param("id")UUID employeeId, Pageable pageable);


    @Query("select l from LeaveManagement l inner join Employee e on(e.id=l.employee.id) where (upper(l.reason) like %:search%) and e.id=:id order by l.leaveApplyDate DESC")
    Page<LeaveManagement> listLeaveByUserReason(@Param("search")String search,@Param("id")UUID employeeId, Pageable pageable);


    @Query("select l from LeaveManagement l where l.active='true' order by l.leaveApplyDate DESC")
    Page<LeaveManagement> listLeaveByAdmin(Pageable pageable);


}
