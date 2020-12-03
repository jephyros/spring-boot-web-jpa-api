package kr.chis.springbootwebjpaapi.exception;

import lombok.Getter;

/**
 * @author InSeok
 * Date : 2020/12/02
 * Remark :
 */
@Getter
public enum ErrorCode {
    //Unknown Error
    UNKNOWN_ERROR(500,"U999","Unknown Error"),

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
    // User& Login
    USER_DATA_NOT_FOUND(404, "U001", "User data is not found."),
    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),

            ;
    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

}
