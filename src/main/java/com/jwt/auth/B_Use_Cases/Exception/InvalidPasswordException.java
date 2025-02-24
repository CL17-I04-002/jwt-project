package com.jwt.auth.B_Use_Cases.Exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
    }
    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
