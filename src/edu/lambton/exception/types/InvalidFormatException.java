package edu.lambton.exception.types;

import edu.lambton.exception.BankException;

public class InvalidFormatException extends BankException {

    public InvalidFormatException(String message) {
        super(message);
    }
}
