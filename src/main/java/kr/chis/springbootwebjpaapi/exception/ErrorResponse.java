package kr.chis.springbootwebjpaapi.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Executable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author InSeok
 * Date : 2020/12/02
 * Remark :
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String errorCode;
    private String errorMessage;

    private String path;

    /*
    {
        "timestamp": "2020-12-02T21:07:09.789+00:00",
        "status": 403,
        "error": "Forbidden",
        "message": "",
        "path": "/api/v1/users/22"
    }
     */
    private ErrorResponse(HttpStatus status, Exception e, ErrorCode errorCode) {

        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = e.getMessage();
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorCode.getMessage();
        this.path = "";//todo


    }

    public static ErrorResponse of(HttpStatus status, Exception e, ErrorCode errorCode) {
        return new ErrorResponse(status, e, errorCode);

    }
}

