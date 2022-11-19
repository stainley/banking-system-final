package edu.lambton.model.type;

import edu.lambton.model.AccountAbstract;

public class ChequingAccount extends AccountAbstract {

    private final AccountType accountType = AccountType.CHEQUING_ACCOUNT;

    public ChequingAccount() {
    }

    public ChequingAccount(Long accountNumber) {
        super(accountNumber);
    }

    public ChequingAccount(Long accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public AccountType getAccountType() {
        return this.accountType;
    }

    @Override
    public String getAccountInformation() {
        return super.getAccountInformation() + "," + getAccountType();
    }
}
