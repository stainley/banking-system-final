package edu.lambton.exception.types;

import edu.lambton.exception.BankException;

public class NegativeBalanceException extends BankException {
    public NegativeBalanceException(String message) {
        super(message);
    }
}
