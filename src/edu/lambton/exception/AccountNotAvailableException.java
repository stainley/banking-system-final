package edu.lambton.exception;

public class AccountNotAvailableException extends RuntimeException {

    public AccountNotAvailableException(String message) {
        super(message);
    }
}
