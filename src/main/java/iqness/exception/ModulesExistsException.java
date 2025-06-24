package iqness.exception;

import iqness.utils.Constants;
import org.springframework.http.HttpStatus;

public class ModulesExistsException extends IqnessException {
    private static final long serialVersionUID = 30763009752460581L;

    public ModulesExistsException(String name) {

        super(
                "Module  (" + name + ") already exists",
                HttpStatus.CONFLICT,
                Constants.MODULES_EXISTS_ERROR_CODE);
    }
}
