package kr.chis.springbootwebjpaapi.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author InSeok
 * Date : 2020/12/02
 * Remark :
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private String message;
    private int status;
    private List<FieldError> errors;
    private String code;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;
    }

    private ErrorResponse(ErrorCode err){
        this.message = err.getMessage();
        this.status = err.getStatus();
        this.code = err.getCode();

    }

    public static ErrorResponse of(ErrorCode err){
        return new ErrorResponse(err);

    }
}
