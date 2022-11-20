package edu.lambton;

import edu.lambton.exception.BankException;
import edu.lambton.exception.types.*;
import edu.lambton.model.AccountAbstract;
import edu.lambton.model.Client;
import edu.lambton.model.PersonalData;
import edu.lambton.model.transaction.Transaction;
import edu.lambton.model.type.AccountType;
import edu.lambton.screen.MainMenu;
import edu.lambton.screen.ReportSuccessTransaction;
import edu.lambton.screen.ReportTransaction;
import edu.lambton.services.AccountServiceImpl;
import edu.lambton.services.account.bill.AccountBillPayment;
import edu.lambton.services.account.bill.AccountBillPaymentImpl;
import edu.lambton.services.account.transfer.AccountTransferImpl;
import edu.lambton.services.transaction.TransactionService;
import edu.lambton.services.transaction.TransactionServiceImpl;
import edu.lambton.util.MenuUtil;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static long transactionId = 1;
    public static AccountAbstract globalAccount;
    static final String[] validOptionNumbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public static void main(String[] args) {
        boolean keepRunning = true;
        AccountServiceImpl accountService;
        MainMenu mainMenu = MainMenu.getInstance();

        while (keepRunning) {
            MenuUtil.getInstance().clearScreen();
            mainMenu.createMainScreen();

            try {
                Scanner selectOption = new Scanner(System.in);
                System.out.print("Select an option: ");

                int option = 0;
                String stringNumber = selectOption.next();
                for (String number : validOptionNumbers) {
                    if (Objects.equals(number, stringNumber)) {
                        option = Integer.parseInt(stringNumber);
                    }
                }
                switch (option) {
                    // REGISTER OPTION
                    case 1 -> {
                        MenuUtil.getInstance().clearScreen();
                        accountService = new AccountServiceImpl();
                        accountService.registerAccount();
                    }
                    // LOGIN
                    case 2 -> {
                        accountService = new AccountServiceImpl();
                        System.out.print("Enter username: ");
                        String userName = selectOption.next().trim();

                        System.out.print("Enter password: ");
                        String password = selectOption.next().trim();
                        mainOptionMenu(accountService, mainMenu, selectOption, userName, password);
                    }
                    // EXIT
                    case 3 -> {
                        System.out.println("Exiting....");
                        keepRunning = false;
                        System.exit(9);
                    }
                    default -> System.out.println("Invalid option");
                }
            } catch (AccountNotFoundException cnfe) {
                System.out.println("Please register a new account." + cnfe.getMessage());
            }
        }
    }

    private static void mainOptionMenu(AccountServiceImpl accountService, MainMenu mainMenu, Scanner selectOption, String userName, String password) {
        try {
            boolean isRunning = true;
            Client userFound = accountService.login(userName, password);

            while (isRunning) {
                MenuUtil.getInstance().clearScreen();
                mainMenu.optionsMenu(userName);
                System.out.print("Please select an option: ");
                int accOptions = selectOption.nextInt();
                double money;
                String[] accountsNumber = new String[2];
                switch (accOptions) {
                    case 1:
                        // SHOW MY ACCOUNT INFO
                        while (true) {
                            MenuUtil.getInstance().clearScreen();
                            if (!mainMenu.showMyAccounts(userFound)) {
                                break;
                            }
                        }
                        break;
                    case 2:
                        // DEPOSIT MONEY
                        while (true) {

                            MenuUtil.getInstance().clearScreen();
                            System.out.println("""
                                    ################################################################################
                                    #                           DEPOSIT OPTION                                     #
                                    ################################################################################
                                    """);
                            System.out.print("Please type amount: $");
                            money = selectOption.nextDouble();
                            try {
                                if (money <= 0) {
                                    throw new NegativeBalanceException("Please deposit more or equal than $1");
                                }
                                while (true) {
                                    try {
                                        if (selectDepositAccount(accountService, selectOption, userFound, money, accountsNumber))
                                            break;
                                    } catch (AccountNotAvailableException aee) {
                                        System.err.println(aee.getMessage());
                                    }
                                }
                            } catch (NegativeBalanceException nb) {
                                System.err.println(nb.getMessage());
                            }
                            break;
                        }
                        break;
                    case 3:
                        // WITHDRAW OPERATION
                        MenuUtil.getInstance().clearScreen();
                        while (true) {
                            try {

                                System.out.println("""
                                        ########################################################################################################
                                        #                                           WITHDRAW OPTION                                            #
                                        ########################################################################################################
                                        """);
                                System.out.print("Please type amount: $");
                                money = selectOption.nextDouble();

                                if (money <= 0) {
                                    throw new NegativeBalanceException("Please withdraw positive greater than 1");
                                }

                                while (true) {
                                    try {
                                        int withdrawAccNumSelected = getAccountNumberFromAccountType(selectOption, userFound, accountsNumber);
                                        if (withdrawAccNumSelected == 1 || withdrawAccNumSelected == 2) {
                                            long finalAccNo1 = Long.parseLong(accountsNumber[withdrawAccNumSelected - 1]);
                                            if (finalAccNo1 > 0) {
                                                double finalMoney = money;
                                                userFound.getAccounts().forEach(account -> {
                                                    if (account.getAccountNumber().equals(finalAccNo1)) {
                                                        accountService.withdrawMoney(userFound.getUsername(), account, finalMoney);
                                                        ReportSuccessTransaction.getInstance().reportSuccessTransaction(account, finalMoney, Main.transactionId);
                                                    }
                                                });
                                                break;
                                            }
                                        }
                                    } catch (InvalidOptionException ioe) {
                                        System.err.println(ioe.getMessage());
                                    }
                                }
                                break;
                            } catch (NotEnoughBalanceException ioe) {
                                System.out.println(ioe.getMessage());
                            } catch (NegativeBalanceException nbe) {
                                System.err.println(nbe.getMessage());
                            }
                        }
                        break;
                    case 4:
                        // TRANSFER MONEY DIFFERENT ACCOUNT
                        MenuUtil.getInstance().clearScreen();
                        while (true) {
                            try {
                                System.out.println("""
                                        ########################################################################################################
                                        #                                       TRANSFER MONEY OPTION                                          #
                                        ########################################################################################################
                                        """);
                                AccountTransferImpl accountTransference = AccountTransferImpl.getInstance();
                                accountTransference.transferMoneyToAccount(userFound);
                                break;
                            } catch (AccountNotFoundException anf) {
                                System.err.println(anf.getMessage());
                            }
                        }
                        break;
                    case 5:
                        // PAYMENT BILLS
                        while (true) {
                            MenuUtil.getInstance().clearScreen();
                            try {

                                AccountBillPayment accountBillPayment = new AccountBillPaymentImpl();
                                accountBillPayment.transferMoneyToAccount(userFound);
                                break;
                            } catch (NotEnoughBalanceException anf) {
                                System.out.println(anf.getMessage());
                            }
                        }
                        break;
                    case 6:
                        // PERSONAL DATA
                        while (true) {
                            MenuUtil.getInstance().clearScreen();
                            PersonalData personalData = new AccountServiceImpl().getPersonalData(userFound.getUsername());
                            mainMenu.personalInformationMenu(personalData);
                            System.out.print("Please press Y go back: ");
                            Scanner input = new Scanner(System.in);
                            if (input.next().equalsIgnoreCase("Y")) {
                                break;
                            }
                        }
                        break;
                    case 7:
                        // SHOW MY TRANSACTIONS
                        MenuUtil.getInstance().clearScreen();
                        while (true) {
                            TransactionService<Transaction> transactionService = new TransactionServiceImpl();
                            List<Transaction> transactions = transactionService.getAllTransactionByUsername(userFound.getUsername());
                            ReportTransaction.getInstance().reportAllTransactionMenuByUsername(transactions);

                            System.out.print("Press Y to go back: ");
                            Scanner inputGoBack = new Scanner(System.in);
                            if (inputGoBack.next().equalsIgnoreCase("Y")) {
                                break;
                            }
                        }
                        break;
                    case 8:
                        // LOGOUT
                        isRunning = false;
                        try {
                            System.out.println("Thanks for using Bank System X");
                            Thread.sleep(3_000);
                        } catch (InterruptedException e) {
                            System.err.println(e.getMessage());
                        }
                        break;

                    default:
                        System.out.println("Invalid option.  Choose an option");
                }
            }

        } catch (AccountNotFoundException acn) {
            System.err.println("Account doesn't exists, please select another account");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (InvalidCredentialException ice) {
            System.err.printf("""
                                          Error Message: %s
                    """, ice.getMessage());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static boolean selectDepositAccount(AccountServiceImpl accountService, Scanner selectOption, Client userFound, double money, String[] accountsNumber) throws BankException {
        int accNumSelected = getAccountNumberFromAccountType(selectOption, userFound, accountsNumber);

        if (accNumSelected == 1 || accNumSelected == 2) {
            long finalAccNo1 = Long.parseLong(accountsNumber[accNumSelected - 1]);
            // validate if the account number if > than 0
            if (finalAccNo1 > 0) {
                userFound.getAccounts().forEach(account -> {
                    if (account.getAccountNumber().equals(finalAccNo1)) {
                        globalAccount = account;
                        accountService.depositMoney(userFound.getUsername(), account, money);
                        ReportSuccessTransaction.getInstance().reportSuccessTransaction(account, money, Main.transactionId);
                    }
                });
                return true;
            } else {
                throw new AccountNotAvailableException("Select a valid account number.");
            }
        }
        return false;
    }

    public static int getAccountNumberFromAccountType(Scanner selectOption, Client userFound, String[] accountsNumber) {
        userFound.getAccounts().forEach(account -> {
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
        MainMenu.getInstance().chooseAccountMenu(accountsNumber);
        System.out.print("Select account: [1][2]: ");
        return selectOption.nextInt();
    }
}

