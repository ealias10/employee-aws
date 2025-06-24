package iqness.exception;

import iqness.utils.Constants;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ModulesNotFoundException  extends IqnessException {
    private static final long serialVersionUID = 30763009752460581L;

    public ModulesNotFoundException(UUID id) {

        super(
                "Modules  is not found with  id : " + id,
                HttpStatus.CONFLICT,
                Constants.MODULES_NOT_FOUND_ERROR_CODE
        );
    }
}
