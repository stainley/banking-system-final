package edu.lambton.services.account.withdraw;

import edu.lambton.model.AccountAbstract;

public interface AccountWithdraw {
    
    /***
     * Used to withdraw money from account
     * @param accountName Username of the account
     * @param account That store the account data
     * @param money Amount to be deducted from account
     */
    void withdrawMoney(String accountName, AccountAbstract account, double money);
}
