package edu.lambton.services.account.deposit;

import edu.lambton.model.AccountAbstract;

public interface AccountDeposit {

    /**
     * Deposit money in account and associated by
     *
     * @param accountName Username for the account
     * @param account     Receive money
     * @param money       amount to be deposited
     */
    void depositMoney(String accountName, AccountAbstract account, double money);
}
