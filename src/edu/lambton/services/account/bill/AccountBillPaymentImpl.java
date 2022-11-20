package edu.lambton.services.account.bill;

import edu.lambton.Main;
import edu.lambton.exception.types.NotEnoughBalanceException;
import edu.lambton.file.reader.company.ReadCompanyAccount;
import edu.lambton.file.reader.company.ReadCompanyAccountImpl;
import edu.lambton.model.AccountAbstract;
import edu.lambton.model.Client;
import edu.lambton.model.CompanyAccount;
import edu.lambton.screen.MainMenu;
import edu.lambton.screen.ReportSuccessTransaction;
import edu.lambton.services.account.deposit.AccountDeposit;
import edu.lambton.services.account.deposit.AccountDepositImpl;
import edu.lambton.services.account.withdraw.AccountWithdraw;
import edu.lambton.services.account.withdraw.AccountWithdrawImpl;
import edu.lambton.services.client.ClientAccount;
import edu.lambton.services.client.ClientAccountImpl;

import java.util.List;
import java.util.Scanner;

public class AccountBillPaymentImpl implements AccountBillPayment {

    private final AccountDeposit accountDeposit = new AccountDepositImpl();
    private final AccountWithdraw accountWithdraw = new AccountWithdrawImpl();
    private final ClientAccount clientAccount = new ClientAccountImpl();

    @Override
    public List<CompanyAccount> getAllCompanyAccount() {
        ReadCompanyAccount<CompanyAccount> companyAccount = new ReadCompanyAccountImpl();

        return companyAccount.readAllCompanyAccount();
    }

    @Override
    public void transferMoneyToAccount(Client fromUserAccount) {
        System.out.println("""                
                ########################################################################################################
                #                                           BILL PAYMENT OPTION                                        #
                ########################################################################################################
                """);
        String[] accountsNumber = new String[2];
        Scanner input = new Scanner(System.in);

        System.out.print("Money: ");
        double amount = input.nextDouble();

        int accNumSelected = Main.getAccountNumberFromAccountType(input, fromUserAccount, accountsNumber);

        if (accNumSelected == 1 || accNumSelected == 2) {
            long finalAccNo1 = Long.parseLong(accountsNumber[accNumSelected - 1]);
            // validate if the account number if > than 0
            transferMoney(fromUserAccount, input, amount, finalAccNo1);
        }
    }

    private void transferMoney(Client fromUserAccount, Scanner input, double amount, long finalAccNo1) {
        if (finalAccNo1 > 0) {
            fromUserAccount.getAccounts().forEach(account -> {

                if (account.getAccountNumber().equals(finalAccNo1)) {

                    MainMenu.getInstance().payBillMenu();
                    System.out.print("Select option [1-5] or B to go back: ");
                    String optionSelected = input.next();
                    int numericOption;
                    if (optionSelected instanceof String) {
                        if (optionSelected.equalsIgnoreCase("B")) {
                            return;
                        }
                    }
                    numericOption = Integer.parseInt(optionSelected);
                    CompanyAccount companyAccount = getAllCompanyAccount().get(numericOption - 1);
                    transferMoney(account, fromUserAccount, companyAccount.getAccountNumber(), companyAccount.getCompanyName(), amount);
                }
            });
        }
    }

    public void transferMoney(AccountAbstract fromAccount, Client fromUserAccount, long toAccountNumber, String companyName, double amount) {
        if (fromAccount.getBalance() < amount) {
            throw new NotEnoughBalanceException("Don't enough balance. You balance is: " + fromAccount.getBalance());
        }
        // First we need to withdraw money, and then send to another account
        accountWithdraw.withdrawMoney(fromUserAccount.getUsername(), fromAccount, amount);

        // Search user account to be sent money
        Client toUser = clientAccount.getUserByAccountNumber(toAccountNumber);
        String toUserName = toUser.getUsername();

        // Then deposit money in the other account
        toUser.getAccounts().forEach(account -> {
            if (account.getAccountNumber() == toAccountNumber) {
                accountDeposit.depositMoney(toUserName, account, amount);
                //System.out.println("Money has been transferred successfully.");
                account.setBalance(amount);
                ReportSuccessTransaction.getInstance().reportSuccessTransferTransaction(fromAccount, account, companyName, Main.transactionId);
            }
        });

    }
}
