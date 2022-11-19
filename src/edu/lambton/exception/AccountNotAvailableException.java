package edu.lambton.exception;

public class AccountNotAvailableException extends BankException {

    public AccountNotAvailableException(String message) {
        super(message);
    }
}
