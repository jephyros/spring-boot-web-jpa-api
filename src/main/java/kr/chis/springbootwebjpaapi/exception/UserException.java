package kr.chis.springbootwebjpaapi.exception;

/**
 * @author InSeok
 * Date : 2020/12/02
 * Remark :
 */
public class UserException extends RuntimeException{
    private ErrorCode errorCode;
    public UserException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
