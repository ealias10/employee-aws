package iqness.exception;

import iqness.utils.Constants;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ProjectsNotFoundException  extends IqnessException {
    private static final long serialVersionUID = 30763009752460581L;

    public ProjectsNotFoundException(UUID id) {

        super(
                "Projects  is not found with  id : " + id,
                HttpStatus.CONFLICT,
                Constants.PROJECT_NOT_FOUND_ERROR_CODE
        );
    }
    public ProjectsNotFoundException(String id) {

        super(
                "Projects  is not found with  project id : " + id,
                HttpStatus.CONFLICT,
                Constants.PROJECT_NOT_FOUND_ERROR_CODE
        );
    }
}
