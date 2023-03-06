package com.titos.tool.exception;

/**
 * @author Titos
 */
public class JwtNotExistException extends ParameterException{
    public JwtNotExistException() {

    }
    public JwtNotExistException(String message) {
        super(message);
    }

    public JwtNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtNotExistException(Throwable cause) {
        super(cause);
    }

    public JwtNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
