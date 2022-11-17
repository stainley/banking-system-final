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
        AccountService accountService = null;
        MainMenu mainMenu = new MainMenu();
        long accNo = 0L;
        while (keepRunning) {
            mainMenu.createMainScreen();

            try {
                Scanner selectOption = new Scanner(System.in);
                System.out.print("Select an option: ");
                int option = selectOption.nextInt();
                switch (option) {
                    case 1:
                        accountService = new AccountService();
                        accountService.registerAccount();
                        break;
                    case 2:
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

                        break;
                    case 3:
                        System.out.println("Exiting....");
                        keepRunning = false;
                        System.exit(9);
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            } catch (AccountNotFoundException cnfe) {
                System.out.println("Register accound");
            }

        }
    }


    private static void showMyAccounts(User userAccounts) {
        System.out.println("Show account information");
        System.out.println("-----------------------------------------------------------------------------");
        userAccounts.getAccounts().forEach(account -> {
            System.out.println("\tAccount Number: " + account.getAccountNumber() + " - Balance: " + account.getBalance());
        });
        System.out.println("-----------------------------------------------------------------------------");
    }
}