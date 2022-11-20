package edu.lambton.model.transaction;

public enum TransactionType {
    WITHDRAW("Withdraw"), DEPOSIT("Deposit"), TRANSFER("Transfer");
    TransactionType(String description) {
        this.description = description;
    }
    private final String description;
    public String getDescription() {
        return this.description;
    }
}
