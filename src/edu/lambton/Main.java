package edu.lambton;

import edu.lambton.exception.AccountNotFoundException;
import edu.lambton.model.Account;
import edu.lambton.model.User;
import edu.lambton.screen.MainMenu;
import edu.lambton.services.AccountService;

import java.util.Scanner;

public class Main {
    public static Account globalAccount;

    public static void main(String[] args) {
        boolean keepRunning = true;
        AccountService accountService;
        MainMenu mainMenu = new MainMenu();
        long accNo;
        while (keepRunning) {
            mainMenu.createMainScreen();

            try {
                Scanner selectOption = new Scanner(System.in);
                System.out.print("Select an option: ");
                int option = selectOption.nextInt();
                switch (option) {
                    case 1 -> {
                        accountService = new AccountService();
                        // TODO: Validate if account exists in another user before create a new account number.
                        accountService.registerAccount();
                    }
                    case 2 -> {
                        accountService = new AccountService();
                        System.out.print("Enter username: ");
                        try {
                            boolean keep = true;
                            User userFound = accountService.login(selectOption.next().trim());

                            while (keep) {

                                mainMenu.optionsMenu();
                                System.out.print("Please select an option: ");
                                int accOptions = selectOption.nextInt();
                                double money;
                                switch (accOptions) {
                                    case 1:
                                        showMyAccounts(userFound);
                                        break;
                                    case 2:
                                        System.out.println("Deposit Money");
                                        // Invoke deposit money
                                        System.out.print("Please type amount: $");
                                        money = selectOption.nextDouble();
                                        System.out.print("Select account: ");
                                        accNo = selectOption.nextLong();
                                        long finalAccNo1 = accNo;
                                        userFound.getAccounts().forEach(account -> {
                                            if (account.getAccountNumber().equals(finalAccNo1)) {
                                                globalAccount = account;
                                            }
                                        });
                                        accountService.depositMoney(userFound.getUsername(), money);
                                        break;
                                    case 3:
                                        System.out.println("Withdraw Money");
                                        // Invoke deposit money
                                        System.out.print("Please type amount: $");
                                        money = selectOption.nextDouble();
                                        System.out.print("Select account: ");
                                        accNo = selectOption.nextLong();
                                        long finalAccNo = accNo;
                                        userFound.getAccounts().forEach(account -> {
                                            if (account.getAccountNumber().equals(finalAccNo)) {
                                                globalAccount = account;
                                            }
                                        });
                                        accountService.withdrawMoney(userFound.getUsername(), money);
                                        break;
                                    case 8:
                                        keep = false;
                                        break;
                                    default:
                                        System.out.println("Invalid option.  Choose an option");
                                }
                            }


                        } catch (AccountNotFoundException acn) {
                            System.err.println("Account doesn't exists, please select another account");
                        }
                    }
                    case 3 -> {
                        System.out.println("Exiting....");
                        keepRunning = false;
                        System.exit(9);
                    }
                    default -> System.out.println("Invalid option");
                }
            } catch (AccountNotFoundException cnfe) {
                System.out.println("Please register a new account.");
            }

        }
    }

    private static void showMyAccounts(User userAccounts) {
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
                balance[0] =    String.format("$%,3.2f", account.getBalance());
            System.out.print("""
                #       Account Number: %s                                                  
                #           - Account Type: %s                                              
                #           - Balance: %s                                                   
                #---------------------------------------------------------------------------
                """.formatted(accountNumber[0], accountType[0], balance[0]));
        });
        System.out.println("#############################################################################");

    }
}