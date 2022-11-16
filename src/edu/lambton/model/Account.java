package edu.lambton.model;

public class Account {

    private final Long accountNumber;
    private final AccountType accountType;

    public Account(Long accountNumber,  AccountType accountType) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
