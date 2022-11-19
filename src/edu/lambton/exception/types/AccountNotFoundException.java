package edu.lambton.exception.types;

import edu.lambton.exception.BankException;

public class AccountNotFoundException extends BankException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
