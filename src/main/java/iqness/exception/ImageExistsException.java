package iqness.exception;

import iqness.utils.Constants;
import org.springframework.http.HttpStatus;

public class ImageExistsException extends IqnessException {
    private static final long serialVersionUID = 30763009752460581L;

    public ImageExistsException(String name) {

        super(
                "Image (" + name + ") already exists",
                HttpStatus.CONFLICT,
                Constants.IMAGE_EXISTS_ERROR_CODE);
    }

    public ImageExistsException() {

        super(
                "Image already exists",
                HttpStatus.CONFLICT,
                Constants.IMAGE_EXISTS_ERROR_CODE);
    }
}
