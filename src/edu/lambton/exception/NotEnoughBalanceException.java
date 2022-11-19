package edu.lambton.exception;

public class NotEnoughBalanceException extends BankException {

    public NotEnoughBalanceException(String message) {
        super(message);
    }
}
