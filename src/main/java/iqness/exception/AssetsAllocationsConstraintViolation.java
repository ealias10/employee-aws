package iqness.exception;

import iqness.utils.Constants;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class AssetsAllocationsConstraintViolation extends IqnessException {
    private static final long serialVersionUID = 30763009752460581L;
    public AssetsAllocationsConstraintViolation(UUID id)
    {
        super(
                "AssetsAllocations  allready use other entry  id : " + id,
                HttpStatus.CONFLICT,
                Constants.ASSETSALLOCATIONS_CONSTRAINT_VIOLATION_ERROR_CODE
        );
    }
}
