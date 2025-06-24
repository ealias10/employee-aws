package iqness.exception;

import iqness.utils.Constants;
import org.springframework.http.HttpStatus;

public class AssetsAllocationsExistsException extends IqnessException {
    private static final long serialVersionUID = 30763009752460581L;

    public AssetsAllocationsExistsException(String empId,String serialNo) {

        super(
                "AssetsAllocations already Allocations with EmployeeId (" + empId + ") and SerialNo :("+serialNo+") ",
                HttpStatus.CONFLICT,
                Constants.ASSETSALLOCATIONS_EXISTS_ERROR_CODE,
                "AssetsAllocations  not available",
                "AssetsAllocations  already being used! Please try another");
    }
    public AssetsAllocationsExistsException() {

        super(
                "AssetsAllocations already Allocations  ",
                HttpStatus.CONFLICT,
                Constants.ASSETSALLOCATIONS_EXISTS_ERROR_CODE,
                "AssetsAllocations  not available",
                "AssetsAllocations  already being used! Please try another");
    }
}
