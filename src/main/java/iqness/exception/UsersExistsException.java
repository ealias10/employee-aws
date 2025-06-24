package iqness.exception;

import iqness.exception.IqnessException;
import iqness.utils.Constants;
import org.springframework.http.HttpStatus;

public class UsersExistsException extends IqnessException {
    private static final long serialVersionUID = 30763009752460581L;

    public UsersExistsException(String name) {

        super(
                "Username  (" + name + ") already exists",
                HttpStatus.CONFLICT,
                Constants.USER_EXISTS_ERROR_CODE,
                "Username  not available",
                "Username  already being used! Please try another");
    }
    public UsersExistsException(String empId,String name) {

        super(
                "Username  (" + name + ") or Empid ("+empId+") already exists",
                HttpStatus.CONFLICT,
                Constants.USER_EXISTS_ERROR_CODE,
                "Username  not available",
                "Username  already being used! Please try another");
    }
}
