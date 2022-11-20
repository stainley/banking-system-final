package edu.lambton.screen;

import edu.lambton.model.Client;
import edu.lambton.model.PersonalData;

import java.util.Scanner;

public class MainMenu {

    private static MainMenu instance;

    private MainMenu() {
    }

    public static MainMenu getInstance() {
        if (instance == null) {
            instance = new MainMenu();
        }
        return instance;
    }


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

    public void optionsMenu(String message) {
        // MenuUtil.getInstance().clearScreen();
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
                *                               7. Transactions                                                         *
                *                               8. Logout                                                               *
                ********************************************************************************************************
                """, message);
    }


    public boolean showMyAccounts(Client userAccounts) {
        //MenuUtil.getInstance().clearScreen();
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
                ********************************************************************************************************
                                                  CLIENT INFORMATION OPTION
                ********************************************************************************************************
                *                                 1. Name:          %s
                *                                 2. Address:       %s
                *                                 3. Phone:         %s
                *                                 4. Email:         %s
                *                                 5. Age:           %s
                *
                ********************************************************************************************************
                """, personalData.getName(), personalData.getAddress(), personalData.getPhoneNumber(), personalData.getEmail(), personalData.getBirthOfYear());
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
                %n""",
                accountNumber[0].equals("0") ? "" : accountNumber[0],
                accountNumber[1].equals("0") ? "" : accountNumber[1]);
    }
}