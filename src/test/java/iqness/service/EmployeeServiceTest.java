package iqness.service;

import iqness.request.EmployeeCreateRequest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    /*@Mock
    EmployeeDao employeeDao;
    private static final UUID ID = UUID.randomUUID();
    @InjectMocks
    EmployeeService employeeService;

    @Test
    void createEmployeeTest() throws EmployeeExistsException, ParseException, IOException, ImageExistsException {
        MultipartFile image;
        image = null;
        Mockito.when(
                        employeeDao.getEmployeeByName(Mockito.anyString()))
                .thenReturn(null);
        Mockito.when(employeeDao.saveEmployee(Mockito.any())).thenReturn(getEmployee());
        var request = EmployeeCreateRequest.builder().employeeName("name").qualification("MCA").email("email").employeeName("name").build();
        var response = employeeService.createEmployee(request,image);
        assertEquals("email", response.getEmail());
    }
    private Employee getEmployee()
    {
        return Employee.builder().id(ID).qualification("MCA").district(District.ERNAKULAM).email("email").dateOfBirth(11111l).name("name").build();
    }
    @Test
    void createEmployeeExistsTest()  {
        MultipartFile image;
        image = null;
        Mockito.when(
                        employeeDao.getEmployeeByName(Mockito.anyString()))
                .thenReturn(getEmployee());
        var request = EmployeeCreateRequest.builder().employeeName("name").qualification("MCA")
                .email("email").employeeName("name").build();
        assertThrows(
                EmployeeExistsException.class, () -> employeeService.createEmployee(request,image));
    }*/
}
