package com.titos.info.exception;

/**
 * @author Titos
 */
public class JwtVerifyException extends ParameterException{
    public JwtVerifyException() {
    }

    public JwtVerifyException(String message) {
        super(message);
    }

    public JwtVerifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtVerifyException(Throwable cause) {
        super(cause);
    }

    public JwtVerifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
