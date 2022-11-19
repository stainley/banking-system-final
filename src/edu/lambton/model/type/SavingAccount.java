package edu.lambton.model.type;

import edu.lambton.model.AccountAbstract;
import edu.lambton.model.AccountType;

public class SavingAccount extends AccountAbstract {

    private final AccountType accountType = AccountType.SAVING_ACCOUNT;

     public SavingAccount(){}

    protected SavingAccount(Long accountNumber) {
        super(accountNumber);
    }

    public SavingAccount(Long accountNumber, double balance) {
        super(accountNumber, balance);
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    @Override
    public String getAccountInformation() {
        return super.getAccountInformation() + "," + this.getAccountType();
    }
}
