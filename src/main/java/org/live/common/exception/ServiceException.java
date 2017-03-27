package org.live.common.exception;

/**
 *  service层异常
 * Created by Mr.wang on 2016/11/24.
 */
public class ServiceException extends CustomException {

    public ServiceException() {
    }

    public ServiceException(int errorCode) {
        super(errorCode);
    }

    public ServiceException(int errorCode, String message) {
        super(errorCode, message);
    }

    public ServiceException(int errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(int errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
