package iqness.exception;

import iqness.utils.Constants;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class HolidayManagementNotFountException extends IqnessException {
    private static final long serialVersionUID = 30763009752460581L;

    public HolidayManagementNotFountException(UUID id) {

        super(
                "holiday  is not found with  id : " + id,
                HttpStatus.CONFLICT,
                Constants.HOLIDAYMANAGEMENT_NOT_FOUND_ERROR_CODE
        );
    }
}
