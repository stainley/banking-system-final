package edu.lambton.exception.types;

import edu.lambton.exception.BankException;

public class InvalidOptionException extends BankException {
    public InvalidOptionException(String message) {
        super(message);
    }
}
