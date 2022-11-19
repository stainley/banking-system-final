package edu.lambton.exception.types;

import edu.lambton.exception.BankException;

public class AccountNotAvailableException extends BankException {
    public AccountNotAvailableException(String message) {
        super(message);
    }
}
