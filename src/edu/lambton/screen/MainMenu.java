package edu.lambton.screen;

import edu.lambton.model.AccountAbstract;
import edu.lambton.model.Client;
import edu.lambton.model.PersonalData;
import edu.lambton.model.type.ChequingAccount;
import edu.lambton.model.type.SavingAccount;


import java.util.Scanner;

public class MainMenu {
// This is the main menu where the user can get registered and  if is already registered then can sign in or can exit//

    public void createMainScreen() {

        System.out.println("""
                ********************************************************************************************************
                *                                                                                                      *
                *                                       MAIN MENU                                                      *
                *                           1. Register                                                                *
                *                           2. Sign In                                                                 *
                *                           3. Exit                                                                    *
                ********************************************************************************************************
                """);

    }
// THis is the options menu where user can have multiple options to do the transactions//

    public void optionsMenu(String message) {
        System.out.printf("""

                *********************************************************************************************************
                                                Welcome %s
                ---------------------------------------------------------------------------------------------------------
                *                                                                                                       *
                *                               1. My Accounts                                                          *
                *                               2. Deposit Money                                                        *
                *                               3. Withdraw Money                                                       *
                *                               4. Transfer Money                                                       *
                *                               5. Bill Payments                                                        *
                *                               6. Client Information                                                   *        
                *                               7. Logout                                                               *
                ********************************************************************************************************
                """, message);
    }


    public boolean showMyAccounts(Client userAccounts) {

        final String[] accountNumber = new String[1];
        final String[] accountType = new String[1];
        final String[] balance = new String[1];

        System.out.print("""
                #############################################################################
                #                            ACCOUNT INFORMATION
                #############################################################################
                """);

        userAccounts.getAccounts().forEach(account -> {
            accountNumber[0] = String.valueOf(account.getAccountNumber());
            accountType[0] = account.getAccountType().getString();
            balance[0] = String.format("$%,3.2f", account.getBalance());
            System.out.printf("""
                    #       Account Number: %s                                                  
                    #           - Account Type: %s                                              
                    #           - Balance: %s                                                   
                    #---------------------------------------------------------------------------
                    """, accountNumber[0], accountType[0], balance[0]);
        });
        System.out.print("###########################################################################\n");
        System.out.print("Press Y to return. ");
        Scanner pressEnter = new Scanner(System.in);
        String keyPressed = pressEnter.next();
        return !keyPressed.equalsIgnoreCase("Y");
    }

// The user gets Registered here by filling the form with the options below//

    public void personalInformationMenu(PersonalData personalData) {
        System.out.printf("""
                ****************************************************************************************************************
                *                                 1. Name:          %s
                *                                 2. Address:       %s
                *                                 3. Phone:         %s
                *                                 4. Email:         %s
                *                                 5. Date Of Birth: %s
                *                                 6. Go Back
                ****************************************************************************************************************
                """, personalData.getName(), personalData.getAddress(), personalData.getPhoneNumber(), personalData.getEmail(), personalData.getBirthOfYear());
    }

    // here by using this option the user can send the money or can request the money //

    public void Interacetransfer() {
        System.out.println("""
                ********************************************************************************************************
                *                                         1.Send Money
                *                                         2.Request Money
                *                                         3.Stop Transaction
                *                                         4.Review History
                *                                         5.Manage Transaction
                *                                         6.Go Back
                ********************************************************************************************************
                """);
    }

    public void payBillMenu() {
        System.out.println("""
                ********************************************************************************************************
                *                                         BILL PAYMENT OPTION
                *-------------------------------------------------------------------------------------------------------
                *                                   1. Telecommunication
                *                                   2. Hydro
                *                                   3. Water
                *                                   4. College
                *                                   5. Housing
                ********************************************************************************************************
                """);
    }

    public void chooseAccountMenu(String[] accountNumber) {
        System.out.printf("""
                ********************************************************************************************************
                *                                         CHOOSE ACCOUNT OPTION
                *-------------------------------------------------------------------------------------------------------
                *                                   1. Chequing Account: %s
                *                                   2. Saving Account: %s
                ********************************************************************************************************
                %n""", accountNumber[0], accountNumber[1]);
    }

    public boolean reportSuccessTransaction(AccountAbstract account, long transactionId) {
        StringBuilder typeAccount = new StringBuilder();
        if (account instanceof SavingAccount) {
            typeAccount.append(account.getAccountType().getString());
        } else if (account instanceof ChequingAccount) {
            typeAccount.append(account.getAccountType().getString());
        } else {
            System.err.println("Invalid type account");
        }

        System.out.printf("""
                #############################################################################
                #                            ACCOUNT INFORMATION
                #############################################################################
                #       Transaction ID: %s
                #       Account Number: %s
                #           - Account Type: %s
                #           - Balance: %s
                #############################################################################
                %n""", transactionId, account.getAccountNumber(), typeAccount, String.format("$%,3.2f", account.getBalance()));
        System.out.print("Press Y to return. ");
        Scanner pressEnter = new Scanner(System.in);
        String keyPressed = pressEnter.next();
        return !keyPressed.equalsIgnoreCase("Y");
    }

    public boolean reportSuccessTransferTransaction(AccountAbstract fromAccount, AccountAbstract toAccount, long transactionId) {
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
                typeAccount, String.format("$%,3.2f", fromAccount.getBalance()),
                toAccount.getAccountNumber(),
                String.format("$%,3.2f", toAccount.getBalance()));
        
        System.out.print("Press Y to return. ");
        Scanner pressEnter = new Scanner(System.in);
        String keyPressed = pressEnter.next();
        return !keyPressed.equalsIgnoreCase("Y");

    }
}