package edu.lambton.model.type;

public enum AccountType {
    SAVING_ACCOUNT("Saving Account"), CHEQUING_ACCOUNT("Chequing Account");
    AccountType(String stringName) {
        this.typeString = stringName;
    }
    public String getString() {
        return this.typeString;
    }

    private final String typeString;
}
