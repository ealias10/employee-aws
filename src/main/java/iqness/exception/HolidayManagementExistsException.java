package iqness.exception;

import iqness.utils.Constants;
import org.springframework.http.HttpStatus;

public class HolidayManagementExistsException extends IqnessException {
    private static final long serialVersionUID = 30763009752460581L;

    public HolidayManagementExistsException(String name) {

        super(
                "Holiday  (" + name + ") already exists",
                HttpStatus.CONFLICT,
                Constants.HOLIDAY_EXISTS_ERROR_CODE);
    }
}
