package iqness.dao;

import iqness.model.Employee;
import iqness.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class EmployeeDao {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee getEmployeeByName(String name) {
        return employeeRepository.getEmployeeByName(name);
    }

    public Employee saveEmployee(Employee employee)
    {
        return employeeRepository.save(employee);
    }


    public Page<Employee> listEmployee(Pageable pageable) {
        return employeeRepository.findByActiveTrue(pageable);
    }

    public Page<Employee> getEmployeeListByName(String name, Pageable pageable) {
        return employeeRepository.getEmployeeListByName(name, pageable);
    }
    public Page<Employee> getEmployeeListByEmail(String email, Pageable pageable) {
        return employeeRepository.getEmployeeListByEmail(email, pageable);
    }
    public Page<Employee> getEmployeeListByNameAndEmail(String name, Pageable pageable) {
        return employeeRepository.getEmployeeListByNameAndEmail(name, pageable);
    }

    public Employee getEmployeeByUserId(UUID userId)
    {
        return employeeRepository.getEmployeeByUserId(userId);
    }
    public Employee getEmployeeById(UUID id) {
        return employeeRepository.findByIdAndActiveTrue(id).orElse(null);
    }
    public void deleteEmployeeById(UUID id) {
        employeeRepository.deleteEmployeeById(id);
    }

    public boolean doesEmployeeWithNameExistForUpdate(UUID id,String name)
    {
        return employeeRepository.doesEmployeeWithNameExistForUpdate(id,name);
    }
    public Integer getEmployeeCount()
    {
        return employeeRepository.getEmployeeCount();
    }
    public Employee getEmployeeByFile(String image)
    {
        return employeeRepository.getEmployeeByFile(image);
    }

    public List<Employee> getEmployeeList()
    {
        return employeeRepository.getEmployeeList();
    }

    public Employee getEmployeeByEmpId(String empId)
    {
        return employeeRepository.getEmployeeByEmpId(empId);
    }
}
