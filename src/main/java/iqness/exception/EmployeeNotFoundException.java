package iqness.exception;

import iqness.utils.Constants;
import org.springframework.http.HttpStatus;

import java.util.UUID;
public class EmployeeNotFoundException extends IqnessException {
    private static final long serialVersionUID = 30763009752460581L;

    public EmployeeNotFoundException(String name) {
        super(
                "Employee is not found with Employeename: " + name,
                HttpStatus.NOT_FOUND,
                Constants.EMPLOYEE_NOT_FOUND_ERROR_CODE);
    }

    public EmployeeNotFoundException(UUID id) {
        super(
                "Employee is not found with  id : " + id,
                HttpStatus.NOT_FOUND,
                Constants.EMPLOYEE_NOT_FOUND_ERROR_CODE);
    }

    public EmployeeNotFoundException() {
        super(
                "Employee is not found : " ,
                HttpStatus.NOT_FOUND,
                Constants.EMPLOYEE_NOT_FOUND_ERROR_CODE);
    }
}
