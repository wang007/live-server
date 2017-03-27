package org.live.common.exception;

/**
 *  自定义异常
 *
 * Created by Mr.wang on 2016/11/24.
 */
public class CustomException extends RuntimeException {

    /**
     * 错误码
     */
    private int errorCode ;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 构造方法
     */
    public CustomException(){}

    public CustomException(int errorCode) {
        super();
        this.errorCode = errorCode ;
    }

    public CustomException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }


    public CustomException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public CustomException(String message, Throwable cause) {
        super(message,cause) ;
    }

    public CustomException(int errorCode, String message, Throwable cause) {
        super(message,cause) ;
        this.errorCode = errorCode ;
    }

    public CustomException(String message) {
        super(message) ;
    }

    public CustomException(Throwable cause) {
        super(cause) ;
    }


}
