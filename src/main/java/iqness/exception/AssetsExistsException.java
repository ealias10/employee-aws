package iqness.exception;

import iqness.utils.Constants;
import org.springframework.http.HttpStatus;

public class AssetsExistsException extends IqnessException {
    private static final long serialVersionUID = 30763009752460581L;

    public AssetsExistsException(String name) {

        super(
                "Assets  (" + name + ") already exists",
                HttpStatus.CONFLICT,
                Constants.ASSETS_EXISTS_ERROR_CODE,
                "Assets  not available",
                "Assets  already being used! Please try another");
    }

    public AssetsExistsException() {

        super(
                "Assets  already exists Allocated",
                HttpStatus.CONFLICT,
                Constants.ASSETS_EXISTS_ERROR_CODE,
                "Assets  not available",
                "Assets  already being used! Please try another");
    }
}
