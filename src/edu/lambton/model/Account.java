package edu.lambton.model;

import java.util.Date;

public class Account {

    private final Long accountNumber;
    private final AccountType accountType;
    private double balance;

    private Date creationDate;

    public Account(Long accountNumber,  AccountType accountType) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
    }

    public Account(Long accountNumber, AccountType accountType, double balance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.creationDate = new Date();
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
