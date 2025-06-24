package iqness.exception;

import iqness.utils.Constants;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class AssetsAllocationsNotFoundException extends IqnessException {
    private static final long serialVersionUID = 30763009752460581L;

    public AssetsAllocationsNotFoundException(UUID id) {

        super(
                "AssetsAllocations  is not found with  id : " + id,
                HttpStatus.CONFLICT,
                Constants.ASSETSALLOCATIONS_NOT_FOUND_ERROR_CODE
             );
    }
}
