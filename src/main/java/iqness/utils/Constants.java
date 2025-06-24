package iqness.utils;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Constants class");
    }

    // custom 401 errors
    public static final String UNAUTHORIZED_ERROR_CODE = "401-100";
    public static final String INVALID_USER_CREDENTIALS = "401-101";
    public static final String INVALID_TOKEN_ERROR_CODE = "401-102";

    //custom 402

    public static final String  ASSETSALLOCATIONS_CONSTRAINT_VIOLATION_ERROR_CODE="402-100";

    //custom 404
    public static final String USER_NOT_FOUND_ERROR_CODE = "404-100";
    public static final String EXPIRED_TOKEN_ERROR_CODE = "404-101";
    public static final String  EMPLOYEE_NOT_FOUND_ERROR_CODE = "404-102";
    public static final String ASSETS_NOT_FOUND_ERROR_CODE="404-103";
    public static final String ASSETSALLOCATIONS_NOT_FOUND_ERROR_CODE="404-104";
    public static final String LEAVEMANAGEMENT_NOT_FOUND_ERROR_CODE="404-105";
    public static final String PROJECT_NOT_FOUND_ERROR_CODE="404-106";

    public static final String MODULES_NOT_FOUND_ERROR_CODE="404-107";



    public static final String HOLIDAYMANAGEMENT_NOT_FOUND_ERROR_CODE="404-106";

    //custom 409
    public static final String USER_EXISTS_ERROR_CODE = "409-100";
    public static final String EMPLOYEE_EXISTS_ERROR_CODE= "409-101";
    public static final String IMAGE_EXISTS_ERROR_CODE= "409-102";
    public static final String ASSETSALLOCATIONS_EXISTS_ERROR_CODE="409-103";
    public static final String ASSETS_EXISTS_ERROR_CODE= "409-104";
    public static final String LEAVE_EXISTS_ERROR_CODE= "409-105";
    public static final String HOLIDAY_EXISTS_ERROR_CODE="409-106";

    public static final String MODULES_EXISTS_ERROR_CODE="409-107";

    public static final String PROJECT_EXISTS_ERROR_CODE="409-107";

}
