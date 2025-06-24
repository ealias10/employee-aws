package iqness.exception;

import iqness.utils.Constants;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class AssetsNotFoundException extends IqnessException {
    private static final long serialVersionUID = 30763009752460581L;

    public AssetsNotFoundException(String name) {
        super(
                "Assets is not found with name: " + name,
                HttpStatus.NOT_FOUND,
                Constants.ASSETS_NOT_FOUND_ERROR_CODE);
    }

    public AssetsNotFoundException(UUID id) {
        super(
                "Assets is not found with  id : " + id,
                HttpStatus.NOT_FOUND,
                Constants.ASSETS_NOT_FOUND_ERROR_CODE);
    }
}
