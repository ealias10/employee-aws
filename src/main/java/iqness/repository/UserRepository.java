package iqness.repository;

import iqness.model.Employee;
import iqness.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<Users, UUID> {

    @Query("select u from Users u where u.username=:name and u.active='true'")
    Users getUserByName(@Param("name") String name);



    @Query("select u from Users u where u.username=:name or u.employee.empId=:empId and u.active='true'")
    Users getUserByNameOrEmpId(@Param("name") String name,@Param("empId") String empId);

    Optional<Users> findByIdAndActiveTrue(UUID id);

    Page<Users> findByActiveTrue(Pageable pageable);
    @Query("select (count(u) > 0) from Users u where  (upper(u.username) = upper(:name) or u.employee.empId=:empId) and u.active='true' and u.id!=:id ")
    boolean doesUserWithNameExistForUpdate(@Param("name")String name,@Param("empId")String empId,@Param("id")UUID id);


    @Query("select u from Users u where (upper(u.username) like %:name%) and u.active='true'")
    Page<Users> getUserListByName(@Param("name") String name, Pageable pageable);

    @Query("select u from Users u where (upper(u.employee.empId) like %:name%) and u.active='true'")
    Page<Users> getUserListByEmployeeId(@Param("name") String name, Pageable pageable);
    @Query("select u from Users u where (upper(u.employee.empId) like %:name%) or (upper(u.username) like %:name%) and u.active='true'")
    Page<Users> getUserListByNameOrEmployeeId(@Param("name") String name, Pageable pageable);
    @Modifying
    @Query("update Users u  set u.active='false' where u.id=:id ")
    void deleteUserById(@Param("id")UUID id);

}
