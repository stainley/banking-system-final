package edu.lambton.screen;

import edu.lambton.model.AccountAbstract;
import edu.lambton.model.type.ChequingAccount;
import edu.lambton.model.type.SavingAccount;
import edu.lambton.util.MenuUtil;

import java.util.Scanner;

public class ReportSuccessTransaction {

    private static ReportSuccessTransaction instance;

    private final static String MONEY_FORMAT = "$%,3.2f";

    private final MenuUtil menuUtil = MenuUtil.getInstance();

    private ReportSuccessTransaction() {
    }

    private static final String PRESS_Y_TO_RETURN = "Press Y to return.";

    public static ReportSuccessTransaction getInstance() {
        if (instance == null) {
            instance = new ReportSuccessTransaction();
        }
        return instance;
    }

    public boolean reportSuccessTransaction(AccountAbstract account, double amount, long transactionId) {
        clearHelper(account);
        StringBuilder typeAccount = new StringBuilder();
        typeAccount.append(account.getAccountType().getString());

        System.out.printf("""
                #############################################################################
                #                            ACCOUNT INFORMATION
                #############################################################################
                #       Transaction ID: %s
                #       Account Number: %s
                #           - Account Type: %s
                #           - New Balance: %s
                #           - Amount: %s
                #############################################################################
                %n""", transactionId, account.getAccountNumber(), typeAccount, String.format(MONEY_FORMAT, account.getBalance()), String.format(MONEY_FORMAT, amount));
        System.out.print(PRESS_Y_TO_RETURN);
        Scanner pressEnter = new Scanner(System.in);
        String keyPressed = pressEnter.next();
        return !keyPressed.equalsIgnoreCase("Y");
    }

    private void clearHelper(AccountAbstract account) {
        menuUtil.clearScreen();
        StringBuilder typeAccount = new StringBuilder();
        if (account instanceof SavingAccount) {
            typeAccount.append(account.getAccountType().getString());
        } else if (account instanceof ChequingAccount) {
            typeAccount.append(account.getAccountType().getString());
        } else {
            System.err.println("Invalid type account");
        }
    }

    public void reportSuccessTransferTransaction(AccountAbstract fromAccount, AccountAbstract toAccount, String companyName, long transactionId) {
        clearHelper(fromAccount);
        StringBuilder typeAccount = new StringBuilder();
        typeAccount.append(fromAccount.getAccountType().getString());
        System.out.printf("""
                        #############################################################################
                        #                            ACCOUNT INFORMATION
                        #############################################################################
                        #       Transaction ID: %s
                        #       From: Account Number: %s
                        #           - Account Type: %s
                        #           - Balance: %s
                        #       To: Account Name: %s
                        #           - Amount: %s
                        #############################################################################
                        %n""",
                transactionId,
                fromAccount.getAccountNumber(),
                typeAccount, String.format(MONEY_FORMAT, fromAccount.getBalance()),
                companyName,
                String.format(MONEY_FORMAT, toAccount.getBalance()));

        System.out.print(PRESS_Y_TO_RETURN);
        Scanner pressEnter = new Scanner(System.in);
        String keyPressed = pressEnter.next();
        //return !keyPressed.equalsIgnoreCase("Y");
    }

    public boolean reportSuccessTransferTransaction(AccountAbstract fromAccount, AccountAbstract toAccount, long transactionId) {
        clearHelper(fromAccount);
        StringBuilder typeAccount = new StringBuilder();
        if (fromAccount instanceof SavingAccount) {
            typeAccount.append(fromAccount.getAccountType().getString());
        } else if (fromAccount instanceof ChequingAccount) {
            typeAccount.append(fromAccount.getAccountType().getString());
        } else {
            System.err.println("Invalid type account");
        }

        System.out.printf("""
                        #############################################################################
                        #                            ACCOUNT INFORMATION
                        #############################################################################
                        #       Transaction ID: %s
                        #       From: Account Number: %s
                        #           - Account Type: %s
                        #           - Balance: %s
                        #       To: Account Number: %s
                        #           - Amount: %s
                        #############################################################################
                        %n""",
                transactionId,
                fromAccount.getAccountNumber(),
                typeAccount, String.format(MONEY_FORMAT, fromAccount.getBalance()),
                toAccount.getAccountNumber(),
                String.format(MONEY_FORMAT, toAccount.getBalance()));

        System.out.print(PRESS_Y_TO_RETURN);
        Scanner pressEnter = new Scanner(System.in);
        String keyPressed = pressEnter.next();
        return !keyPressed.equalsIgnoreCase("Y");
    }
}
