package edu.lambton.exception;

public class InvalidCredentialException extends BankException {

    public InvalidCredentialException(String message) {
        super(message);
    }
}
