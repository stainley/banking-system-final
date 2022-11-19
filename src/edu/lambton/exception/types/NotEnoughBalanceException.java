package edu.lambton.exception.types;

import edu.lambton.exception.BankException;

public class NotEnoughBalanceException extends BankException {
    public NotEnoughBalanceException(String message) {
        super(message);
    }
}
