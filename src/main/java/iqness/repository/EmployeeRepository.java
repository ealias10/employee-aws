package iqness.repository;

import iqness.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query("select e from Employee e where e.name=:name and e.active='true'")
    Employee getEmployeeByName(@Param("name") String name);

    Page<Employee> findByActiveTrue(Pageable pageable);

    @Query("select e from Employee e where (upper(e.name) like %:name%) and e.active='true'")
    Page<Employee> getEmployeeListByName(@Param("name") String name, Pageable pageable);


    @Query("select e from Employee e where (upper(e.name) like %:name%)  or (upper(e.email) like %:name%) and e.active='true'")
    Page<Employee> getEmployeeListByNameAndEmail(@Param("name") String name, Pageable pageable);


    @Query("select e from Employee e where (upper(e.email) like %:email%) and e.active='true'")
    Page<Employee> getEmployeeListByEmail(@Param("email") String name, Pageable pageable);

    Optional<Employee> findByIdAndActiveTrue(UUID id);

    @Modifying
    @Query("update Employee e  set e.active='false' where e.id=:id")
    void deleteEmployeeById(@Param("id") UUID id);

    @Query("select count(e) from Employee e")
    Integer getEmployeeCount();
    @Query("select e from Employee e where e.photoPath=:imageUrl")
    Employee getEmployeeByFile(@Param("imageUrl")String imageUrl);

    @Query("select (count(e) > 0) from Employee e where  upper(e.name) = upper(:name) and e.id!=:id and e.active='true' ")
    boolean doesEmployeeWithNameExistForUpdate(@Param("id") UUID id, @Param("name") String name);


    @Query("select e from Employee e where e.empId=:empId and e.active='true'")
    Employee getEmployeeByEmpId(@Param("empId")String empId);

    @Query("select e from Employee e where e.active='true'")
    List<Employee> getEmployeeList();

    @Query("select e from Users u join u.employee e where u.id=:userId")
    Employee getEmployeeByUserId(@Param("userId")UUID userId);
}
