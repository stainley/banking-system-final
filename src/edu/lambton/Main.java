package edu.lambton;

import edu.lambton.exception.types.AccountNotFoundException;
import edu.lambton.exception.types.InvalidCredentialException;
import edu.lambton.exception.types.NotEnoughBalanceException;
import edu.lambton.model.AccountAbstract;
import edu.lambton.model.AccountType;
import edu.lambton.model.Client;
import edu.lambton.model.PersonalData;
import edu.lambton.screen.MainMenu;
import edu.lambton.services.AccountService;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static AccountAbstract globalAccount;
    static final String[] validNumbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

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

                int option = 0;
                String stringNumber = selectOption.next();
                for (String number : validNumbers) {
                    if (Objects.equals(number, stringNumber)) {
                        option = Integer.parseInt(stringNumber);
                    }
                }
                switch (option) {
                    case 1 -> {
                        accountService = new AccountService();
                        accountService.registerAccount();
                    }
                    case 2 -> {
                        accountService = new AccountService();
                        System.out.print("Enter username: ");
                        String userName = selectOption.next().trim();

                        System.out.print("Enter password: ");
                        String password = selectOption.next().trim();

                        try {
                            boolean keep = true;
                            Client userFound = accountService.login(userName, password);

                            while (keep) {

                                mainMenu.optionsMenu(userName);
                                System.out.print("Please select an option: ");
                                int accOptions = selectOption.nextInt();
                                double money;
                                switch (accOptions) {
                                    case 1:

                                        while (true) {
                                            if (!mainMenu.showMyAccounts(userFound)) {
                                                break;
                                            }
                                        }
                                        break;
                                    case 2:
                                        System.out.println("Deposit Money");
                                        // Invoke deposit money
                                        System.out.print("Please type amount: $");
                                        money = selectOption.nextDouble();
                                        String[] accountsNumber = new String[2];
                                        userFound.getAccounts().forEach(account -> {
                                            if (account.getAccountType().equals(AccountType.CHEQUING_ACCOUNT)) {
                                                accountsNumber[0] = String.valueOf(account.getAccountNumber());
                                            } else {
                                                accountsNumber[1] = String.valueOf(account.getAccountNumber());
                                            }
                                        });
                                        new MainMenu().chooseAccountMenu(accountsNumber);
                                        System.out.print("Select account: [1][2]: ");
                                        int accNumSelected = selectOption.nextInt();
                                        if (accNumSelected == 1 || accNumSelected == 2) {
                                            long finalAccNo1 = Long.parseLong(accountsNumber[accNumSelected - 1]);
                                            AccountService finalAccountService = accountService;
                                            double finalMoney1 = money;
                                            userFound.getAccounts().forEach(account -> {
                                                if (account.getAccountNumber().equals(finalAccNo1)) {
                                                    globalAccount = account;
                                                    finalAccountService.depositMoney(userFound.getUsername(), account, finalMoney1);
                                                }
                                            });
                                        }
                                        break;
                                    case 3:
                                        while (true) {
                                            try {

                                                System.out.println("Withdraw Money");
                                                System.out.print("Please type amount: $");
                                                money = selectOption.nextDouble();
                                                System.out.print("Select account: ");
                                                accNo = selectOption.nextLong();
                                                long finalAccNo = accNo;
                                                AccountService finalAccountService1 = accountService;
                                                double finalMoney = money;
                                                userFound.getAccounts().forEach(account -> {
                                                    if (account.getAccountNumber().equals(finalAccNo)) {
                                                        finalAccountService1.withdrawMoney(userFound.getUsername(), account, finalMoney);

                                                    }
                                                });
                                                break;
                                            } catch (NotEnoughBalanceException ioe) {
                                                System.out.println(ioe.getMessage());
                                            }
                                        }
                                        break;
                                    case 4:
                                        while (true) {
                                            try {
                                                System.out.println("Transfer money to another account");
                                                transferMoneyToAccount(userFound);
                                                break;
                                            } catch (AccountNotFoundException anf) {
                                                System.err.println(anf.getMessage());
                                            }
                                        }
                                        break;
                                    case 5:
                                        try {
                                            System.out.println("Pay the bill");
                                            transferMoneyToAccount(userFound);
                                            break;
                                        } catch (AccountNotFoundException anf) {
                                            System.err.println(anf.getMessage());
                                        }
                                        break;
                                    case 6:
                                        while (true) {
                                            System.out.println("Client Information");
                                            PersonalData personalData = new AccountService().getPersonalData(userFound.getUsername());
                                            mainMenu.personalInformationMenu(personalData);
                                            System.out.print("Please select option to go back: ");
                                            Scanner input = new Scanner(System.in);
                                            if (input.nextInt() == 6) {
                                                break;
                                            }
                                        }
                                        break;
                                    case 7:
                                        keep = false;
                                        break;
                                    default:
                                        System.out.println("Invalid option.  Choose an option");
                                }
                            }

                        } catch (AccountNotFoundException acn) {
                            System.err.println("Account doesn't exists, please select another account");
                        } catch (InvalidCredentialException ice) {
                            System.err.printf("""
                                                          Error Message: %s
                                    """, ice.getMessage());
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

    /***
     * Transfer money to account
     * @param fromUserAccount User account
     */
    private static void transferMoneyToAccount(Client fromUserAccount) {
        Scanner input = new Scanner(System.in);
        System.out.print("From account number: ");
        long fromAccountNumber = input.nextLong();

        // Find the account we want to use from one or more accounts
        fromUserAccount.getAccounts().forEach(account -> {
            if (account.getAccountNumber().equals(fromAccountNumber)) {

                System.out.print("To account number: ");
                long toAccountNumber = input.nextLong();

                System.out.print("Money: ");
                double amount = input.nextDouble();

                AccountService accountService = new AccountService();
                accountService.transferMoney(account, fromUserAccount, toAccountNumber, amount);
            }
        });
    }


}

