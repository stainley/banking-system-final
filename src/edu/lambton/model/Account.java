package edu.lambton.model;

import java.time.LocalDateTime;

public class Account {

    private Long accountNumber;
    private AccountType accountType;
    private double balance;
    private LocalDateTime creationDate;

    public Account() {
    }

    public Account(Long accountNumber, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.creationDate = LocalDateTime.now();
    }

    public Account(Long accountNumber, AccountType accountType, double balance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.creationDate = LocalDateTime.now();
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
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

    public AccountType getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountInformation() {
        return accountNumber + "," + accountType.getString() + "," + balance + "," + creationDate;
    }
}
