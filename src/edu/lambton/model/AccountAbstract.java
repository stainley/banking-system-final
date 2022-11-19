package edu.lambton.model;

import edu.lambton.model.type.AccountType;

import java.time.LocalDateTime;

public abstract class AccountAbstract {
    private Long accountNumber;
    private double balance;
    private LocalDateTime creationDate;

    private AccountType accountType;

    protected AccountAbstract(){}

    protected AccountAbstract(Long accountNumber) {
        this.accountNumber = accountNumber;
        this.creationDate = LocalDateTime.now();
    }

    protected AccountAbstract(Long accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.creationDate = LocalDateTime.now();
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountInformation() {
        return accountNumber + "," + balance + "," + creationDate;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
