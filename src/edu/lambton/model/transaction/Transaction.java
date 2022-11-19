package edu.lambton.model.transaction;

import edu.lambton.model.AccountAbstract;

import java.time.LocalDateTime;

public class Transaction extends Bank {
    private long transactionId;
    private TransactionType transactionType;
    private AccountAbstract account;
    private LocalDateTime transactionTime;

    private String username;

    public Transaction() {
    }

    public Transaction(long transactionId, String username, TransactionType transactionType, AccountAbstract account, LocalDateTime transactionTime) {
        this.transactionId = transactionId;
        this.username = username;
        this.transactionType = transactionType;
        this.account = account;
        this.transactionTime = transactionTime;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }


    public AccountAbstract getAccount() {
        return account;
    }

    public void setAccount(AccountAbstract account) {
        this.account = account;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTransactionInfo() {
        return transactionId + "," + username + "," + account.getAccountNumber() + "," + account.getAccountType() + "," + account.getBalance() + "," + transactionTime;
    }

}

