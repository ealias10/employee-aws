package iqness.exception;

import iqness.utils.Constants;
import org.springframework.http.HttpStatus;

public class LeaveManagementExistsException extends IqnessException {

    private static final long serialVersionUID = 30763009752460581L;

    public LeaveManagementExistsException(String fromDate,String toDate) {

        super(
                "fromDate (" + fromDate + ") to toDate ("+toDate+") leave exist",
                HttpStatus.CONFLICT,
                Constants.LEAVE_EXISTS_ERROR_CODE);
    }


}
