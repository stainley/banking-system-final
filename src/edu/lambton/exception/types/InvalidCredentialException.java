package edu.lambton.exception.types;

import edu.lambton.exception.BankException;

public class InvalidCredentialException extends BankException {
    public InvalidCredentialException(String message) {
        super(message);
    }
}
