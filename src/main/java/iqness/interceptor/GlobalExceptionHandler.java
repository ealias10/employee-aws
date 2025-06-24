package iqness.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import iqness.exception.IqnessException;
import iqness.utils.Constants;
import iqness.vo.ErrorVO;
import iqness.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseVO<Object>> handles(
            HttpServletRequest request, MissingServletRequestParameterException exception) {
        StringBuilder validationErrors=new StringBuilder();
        validationErrors.append(exception.getParameterName()).append(exception.getMessage());
        ErrorVO errorVO = new ErrorVO();
        String error=String.valueOf(validationErrors);
        ResponseVO<Object> response = new ResponseVO<>();
        response.setMessage(error);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseVO<Object>> handles(
            HttpServletRequest request, DataIntegrityViolationException exception) {
        StringBuilder validationErrors=new StringBuilder();
        validationErrors.append(exception.getMessage());
        ErrorVO errorVO = new ErrorVO();
        String error=String.valueOf(validationErrors);
        ResponseVO<Object> response = new ResponseVO<>();
        response.setMessage(error);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    public static byte[] getUnauthorizedResponseForAuthFilterErrors(Exception exception) {
        ErrorVO errorVO = new ErrorVO();
        errorVO.getErrors().add(exception.getMessage());
        if (exception instanceof IqnessException) {
            errorVO.setErrorCode(((IqnessException) exception).getErrorCode());
        } else {
            errorVO.setErrorCode(Constants.UNAUTHORIZED_ERROR_CODE);
        }
        ResponseVO<Object> responseVO = new ResponseVO<>();
        responseVO.setStatus(HttpStatus.UNAUTHORIZED.value());
        responseVO.setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        responseVO.setError(errorVO);
        String responseString = convertToJsonString(responseVO);
        return responseString.getBytes();
    }
    private static String convertToJsonString(ResponseVO<Object> response) {
        String responseString = null;
        try {
            responseString = OBJECT_MAPPER.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            responseString = response.toString();
        }
        return responseString;
    }
    @ExceptionHandler(IqnessException.class)
    public ResponseEntity<ResponseVO<Object>> handle(
            HttpServletRequest request, IqnessException exception) {
        logError(request, exception);
        ErrorVO errorVO = new ErrorVO();
        errorVO.getErrors().add(exception.getMessage());
        errorVO.setErrorCode(exception.getErrorCode());
        if (exception.getDisplayTitle() != null) {
            errorVO.setDisplayTitle(exception.getDisplayTitle());
        }
        if (exception.getDisplayMessage() != null) {
            errorVO.setDisplayMessage(exception.getDisplayMessage());
        }
        ResponseVO<Object> responseVO = new ResponseVO<>();
        responseVO.setStatus(exception.getStatus().value());
        responseVO.setMessage(exception.getStatus().getReasonPhrase());
        responseVO.setError(errorVO);

        return new ResponseEntity<>(responseVO, exception.getStatus());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseVO<Object>> handle(
            HttpServletRequest request, MethodArgumentNotValidException exception) {
        StringBuilder validationErrors=new StringBuilder();
        for (FieldError fieldError : exception.getFieldErrors()) {
            if (fieldError.getDefaultMessage() != null) {
                validationErrors.append(fieldError.getField()).append(fieldError.getDefaultMessage());
            }
        }
        ErrorVO errorVO = new ErrorVO();
        String error=String.valueOf(validationErrors);
        ResponseVO<Object> response = new ResponseVO<>();
        response.setMessage(error);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    private void logError(HttpServletRequest request, Exception exception) {

        log.error("Failed {} {} {}", request.getMethod(), request.getRequestURI(), exception);
    }
}
