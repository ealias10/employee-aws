package iqness.exception;

import iqness.utils.Constants;
import org.springframework.http.HttpStatus;

public class EmployeeExistsException extends IqnessException {
    private static final long serialVersionUID = 30763009752460581L;

    public EmployeeExistsException(String name) {

        super(
                "Employee  (" + name + ") already exists",
                HttpStatus.CONFLICT,
                Constants.EMPLOYEE_EXISTS_ERROR_CODE,
                "Employee  not available",
                "Employee  already being used! Please try another");
    }
}
