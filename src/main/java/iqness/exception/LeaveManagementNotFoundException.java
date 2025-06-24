package iqness.exception;

import iqness.utils.Constants;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class LeaveManagementNotFoundException extends IqnessException {
    private static final long serialVersionUID = 30763009752460581L;

    public LeaveManagementNotFoundException(UUID id) {

        super(
                "Leave  is not found with  id : " + id,
                HttpStatus.CONFLICT,
                Constants.LEAVEMANAGEMENT_NOT_FOUND_ERROR_CODE
        );
    }
}
