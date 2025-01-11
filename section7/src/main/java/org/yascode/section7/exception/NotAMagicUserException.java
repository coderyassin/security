package org.yascode.section7.exception;

import org.springframework.security.core.AuthenticationException;

public class NotAMagicUserException extends AuthenticationException {

    public NotAMagicUserException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public NotAMagicUserException(String msg) {
        super(msg);
    }
}
