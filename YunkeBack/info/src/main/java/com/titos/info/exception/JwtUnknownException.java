package com.titos.info.exception;

/**
 * @author Titos
 */
public class JwtUnknownException extends ParameterException {
    public JwtUnknownException() {
    }

    public JwtUnknownException(String message) {
        super(message);
    }

    public JwtUnknownException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtUnknownException(Throwable cause) {
        super(cause);
    }

    public JwtUnknownException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
