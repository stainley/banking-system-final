package edu.lambton.services.account.bill;

import edu.lambton.Main;
import edu.lambton.exception.types.NotEnoughBalanceException;
import edu.lambton.file.reader.company.ReadCompanyAccount;
import edu.lambton.file.reader.company.ReadCompanyAccountImpl;
import edu.lambton.model.AccountAbstract;
import edu.lambton.model.Client;
import edu.lambton.model.CompanyAccount;
import edu.lambton.model.type.AccountType;
import edu.lambton.screen.MainMenu;
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
        List<CompanyAccount> companyAccounts = companyAccount.readAllCompanyAccount();

        return companyAccounts;
    }

    @Override
    public void transferMoneyToAccount(Client fromUserAccount) {
        String[] accountsNumber = new String[2];
        Scanner input = new Scanner(System.in);

        System.out.print("Money: ");
        double amount = input.nextDouble();

        System.out.print("From account number: ");

        fromUserAccount.getAccounts().forEach(account -> {
            if (account.getAccountType().equals(AccountType.CHEQUING_ACCOUNT)) {
                accountsNumber[0] = String.valueOf(account.getAccountNumber() > 0 ? account.getAccountNumber() : 0);
            } else {
                accountsNumber[1] = String.valueOf(account.getAccountNumber() > 0 ? account.getAccountNumber() : 0);
            }
        });

        if (accountsNumber[0] == null) {
            accountsNumber[0] = "0";
        } else if (accountsNumber[1] == null) {
            accountsNumber[1] = "0";
        }
        new MainMenu().chooseAccountMenu(accountsNumber);
        System.out.print("Select account: [1][2]: ");
        int accNumSelected = input.nextInt();

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

                    System.out.println("Pay the bill");
                    new MainMenu().payBillMenu();
                    System.out.print("Select option [1-5] or B to go back: ");
                    String optionSelected = input.next();
                    int numericOption;

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
                System.out.println("Money has been transferred successfully.");
                account.setBalance(amount);
                new MainMenu().reportSuccessTransferTransaction(fromAccount, account, companyName, Main.transactionId);
            }
        });

    }
}
