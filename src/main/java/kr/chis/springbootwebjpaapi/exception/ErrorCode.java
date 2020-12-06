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
    INVALID_INPUT_VALUE(400, "C001","Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", "METHOD_NOT_ALLOWED"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
    // User& Login
    USER_DATA_NOT_FOUND(404, "U001", "User data is not found."),
    EMAIL_DUPLICATION(400, "U002", "이메일이 중복되었습니다."),
    LOGIN_INPUT_INVALID(400, "U003", "Login input is invalid"),
    EMAIL_NAME_PASSWORD_MANDATORY(400,"U004","이메일, 이름, 패스워드는 필수 입니다.")

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
