package kr.chis.springbootwebjpaapi.exception;

/**
 * @author InSeok
 * Date : 2020/12/02
 * Remark :
 */
public class UserException extends RuntimeException{
    public UserException(String message) {
        super(message);
    }
}
