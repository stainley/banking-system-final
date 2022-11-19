package edu.lambton.services.account.transfer;

import edu.lambton.model.AccountAbstract;
import edu.lambton.model.Client;

public interface AccountTransfer {

    /***
     * Transfer money from one account to another account
     * @param fromAccount From which account money will be deducted
     * @param fromUserAccount From which account number money will be withdrawn
     * @param toAccountNumber To which account number money will be sent
     * @param amount  Money to be deposited
     */
    void transferMoney(AccountAbstract fromAccount, Client fromUserAccount, long toAccountNumber, double amount);


    /***
     * Transfer money to account
     * @param fromUserAccount User account
     */
    void transferMoneyToAccount(Client fromUserAccount);
}
