package edu.lambton.services.account.bill;

import edu.lambton.model.Client;
import edu.lambton.model.CompanyAccount;

import java.util.List;

public interface AccountBillPayment {
    List<CompanyAccount> getAllCompanyAccount();
    void transferMoneyToAccount(Client fromUserAccount);
}
