package iqness.dao;

import iqness.model.Employee;
import iqness.model.Role;
import iqness.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeDaoTest {
    @InjectMocks
    EmployeeDao employeeDao;
    @Mock
    EmployeeRepository employeeRepository;
    private static final UUID ID = UUID.randomUUID();

    @Test
    void  getEmployeeByNameTest() {
        Mockito.when(
                employeeRepository.getEmployeeByName(
                        Mockito.anyString())).thenReturn(getEmployee());
        var response =employeeDao.getEmployeeByName("ealias");
        assertEquals(ID, response.getId());
    }

    @Test
    void getEmployeeByIdTest() {
        Mockito.when(employeeRepository.findByIdAndActiveTrue(Mockito.any()))
                .thenReturn(Optional.of(getEmployee()));
        var response = employeeDao.getEmployeeById(ID);
        assertEquals(ID, response.getId());
    }

    @Test
    void doesEmployeeWithNameExistForUpdateTest() {
        Mockito.when(
                        employeeRepository.doesEmployeeWithNameExistForUpdate(
                                Mockito.any(), Mockito.anyString()))
                .thenReturn(true);
        var response = employeeDao.doesEmployeeWithNameExistForUpdate(ID, "name");
        assertTrue(response);
    }
  @Test
  void saveEmployeeTest() {
      Mockito.when(employeeRepository.save(Mockito.any())).thenReturn(getEmployee());
      var response = employeeDao.saveEmployee(new Employee());
      assertEquals(ID, response.getId());
  }
    @Test
    void deleteEmployeeTest() {
        Mockito.doNothing()
                .when(employeeRepository)
                .deleteEmployeeById(Mockito.any());
        assertDoesNotThrow(() -> employeeDao.deleteEmployeeById(ID));
        Mockito.verify(employeeRepository, Mockito.times(1))
                .deleteEmployeeById(Mockito.any());
    }
    @Test
    void gerEmployeeByName() {
        Mockito.when(employeeRepository.getEmployeeListByName(Mockito.any(), Mockito.any()))
                .thenReturn(getEmployeePage());
        var response = employeeDao.getEmployeeListByName(ID.toString(), Pageable.ofSize(1));
        assertEquals(ID, response.getContent().get(0).getId());
    }
    @Test
    void gerEmployeeList() {
        Mockito.when(employeeRepository.findByActiveTrue(Mockito.any()))
                .thenReturn(getEmployeePage());
        var response = employeeDao.listEmployee(Pageable.ofSize(1));
        assertEquals(ID, response.getContent().get(0).getId());
    }
    private static PageImpl<Employee> getEmployeePage() {
        return new PageImpl<>(Collections.singletonList(getEmployee()));
    }
    private static Employee getEmployee() {
        return Employee.builder().id(ID).build();
    }

}
