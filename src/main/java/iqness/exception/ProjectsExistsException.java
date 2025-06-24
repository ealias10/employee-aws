package iqness.exception;

import iqness.utils.Constants;
import org.springframework.http.HttpStatus;

public class ProjectsExistsException extends IqnessException {
    private static final long serialVersionUID = 30763009752460581L;

    public ProjectsExistsException(String name) {

        super(
                "Project  (" + name + ") already exists",
                HttpStatus.CONFLICT,
                Constants.PROJECT_EXISTS_ERROR_CODE);
    }
}
