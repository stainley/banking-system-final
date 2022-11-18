package edu.lambton.services;

import edu.lambton.exception.*;
import edu.lambton.file.ReadFile;
import edu.lambton.file.WriteFile;
import edu.lambton.model.Account;
import edu.lambton.model.AccountType;
import edu.lambton.model.PersonalData;
import edu.lambton.model.User;
import edu.lambton.util.DBFile;

import java.io.IOException;
import java.util.*;

public class AccountService {

    private User createUser() {
        List<Account> accounts = new ArrayList<>();

        Scanner input = new Scanner(System.in);
        String username = "";

        while (true) {
            try {
                System.out.print("Username: ");
                username = input.next().trim();

                if (!usernameIsAvailable(username)) {
                    throw new AccountNotAvailableException("Username is not available");
                }
                break;
            } catch (AccountNotAvailableException noe) {
                System.out.println(noe.getMessage());
            }
        }

        System.out.print("Password: ");
        String password = input.next().trim();

        boolean isPasswordCreated = createPassword(username, password);

        if (isPasswordCreated) {
            System.out.println("PERSONAL INFORMATION");
            register(username);

            // how many account you want to add
            System.out.print("How many account would you like to add? ");
            int numAccounts = input.nextInt();

            for (int i = 0; i < numAccounts; i++) {
                accounts.add(createAccount());
            }
            return new User(username, accounts);
        }
        return null;
    }

    private boolean createPassword(String username, String password) {
        WriteFile writeFile = new WriteFile();
        StringBuilder sb = new StringBuilder();
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

        sb.append(username).append(",").append(encodedPassword);

        try {
            return writeFile.writePasswordFile(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validatePassword(String username, String password) {
        ReadFile readPasswordFile = new ReadFile();
        String passwordFromFile = readPasswordFile.readPasswordInformation(username);

        byte[] decodedPassword = Base64.getDecoder().decode(passwordFromFile);

        return password.equals(new String(decodedPassword));
    }

    private Account createAccount() {
        Scanner input = new Scanner(System.in);


        System.out.println("""
                ----------------------------------------------
                            Account Type
                ----------------------------------------------
                    1. Chequing Account
                    2. Saving Account
                ----------------------------------------------
                """);
        System.out.print("Choose type account: ");
        int typeAccountSelected = input.nextInt();
        long accountNumber = 0;
        while (true) {
            try {
                System.out.print("Account number: ");
                accountNumber = input.nextLong();

                if (!accountNumberIsAvailable(accountNumber)) {
                    throw new AccountNotAvailableException("Account is not available: " + accountNumber);
                }
                break;
            } catch (AccountNotAvailableException anf) {
                System.out.println("Please choose another account number.");
            }
        }

        System.out.print("Choose balance: ");
        double initialBalance = input.nextDouble();

        return new Account(accountNumber, typeAccount(typeAccountSelected), initialBalance);
    }

    private boolean usernameIsAvailable(String username) {
        ReadFile readFile = new ReadFile();
        for (String usernameFound : readFile.getAllUsernameInformation()) {
            if (Objects.equals(usernameFound, username)) {
                return false;
            }
        }
        return true;
    }

    private boolean accountNumberIsAvailable(long accountNumber) {
        ReadFile readFile = new ReadFile();
        for (long accNumberFound : readFile.getAllAccountInformation()) {
            if (accNumberFound == accountNumber) {
                return false;
            }
        }
        return true;
    }

    private AccountType typeAccount(int accountNumber) {
        return switch (accountNumber) {
            case 1 -> AccountType.CHEQUING_ACCOUNT;
            case 2 -> AccountType.SAVING_ACCOUNT;
            default -> throw new InvalidOptionException("Invalid option type account");
        };
    }

    private PersonalData registerInfoPersonaData() {
        Scanner inputData = new Scanner(System.in);
        System.out.print("Name: ");
        String name = inputData.next();
        System.out.print("Address: ");
        StringBuilder address = new StringBuilder();
        address.append(inputData.useDelimiter("\n").next());

        System.out.print("Birth of Year: ");
        int birthOfYear = inputData.nextInt();
        System.out.print("Email: ");
        String email = inputData.next();
        System.out.print("Phone Number: ");
        String phone = inputData.next();

        return new PersonalData(name, birthOfYear, address.toString(), email, phone);
    }

    public void registerAccount() {
        WriteFile writeFile = new WriteFile();

        try {
            writeFile.writeAccountInformation(createUser());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void register(String username) {
        WriteFile writeFile = new WriteFile();

        try {
            writeFile.writeAccountInformation(username, registerInfoPersonaData());
            System.out.println("User registered successfully.\n\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByAccountNumber(long accountNumber) {
        ReadFile readFile = new ReadFile();

        if (readFile.validateIfFileExists(DBFile.DB_FILE_NAME)) {
            try {
                User accountsInfFile = readFile.readUserInformation(accountNumber);

                if (accountsInfFile.getAccounts() == null) {
                    throw new AccountNotFoundException("Account doesn't exit");
                } else {
                    return accountsInfFile;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("File doesn't exist. Go back to login menu");
            throw new AccountNotFoundException("Account not found");
        }
    }

    public User login(String accountName, String password) {
        ReadFile readFile = new ReadFile();

        if (validatePassword(accountName, password)) {
            if (readFile.validateIfFileExists(DBFile.DB_FILE_NAME)) {
                try {
                    User accountstInFile = readFile.readUserInformation(accountName);

                    if (accountstInFile.getAccounts() == null) {
                        throw new AccountNotFoundException("Account doesn't exit");
                    } else {
                        return accountstInFile;
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("File doesn't exist. Go back to login menu");
                throw new AccountNotFoundException("Account not found");
            }
        } else {
            throw new InvalidCredentialException("Username/Password not matched");
        }
    }

    /**
     * Deposit money in account and associated by
     *
     * @param accountName Username for the account
     * @param account     Receive money
     * @param money       amount to be deposited
     */
    public void depositMoney(String accountName, Account account, double money) {
        double newBalance = account.getBalance() + money;
        account.setBalance(newBalance);

        WriteFile writeFile = new WriteFile();
        writeFile.updateBalanceInFile(accountName, account);
        System.out.println("Money " + money + " deposited in you account. New balance is: $" + newBalance);
    }

    /***
     * Used to withdraw money from account
     * @param accountName Username of the account
     * @param account That store the account data
     * @param money Amount to be deducted from account
     */
    public void withdrawMoney(String accountName, Account account, double money) {
        double balance = account.getBalance();
        if (balance < money) {
            throw new NotEnoughBalanceException("Transaction could not be  processed. Not enough balance");
        } else {
            double newBalance = balance - money;
            account.setBalance(newBalance);

            WriteFile writeFile = new WriteFile();
            writeFile.updateBalanceInFile(accountName, account);
        }
    }

    /***
     * Transfer money from one account to another account
     * @param fromAccount From which account money will be deducted
     * @param fromUserAccount From which account number money will be withdrawn
     * @param toAccountNumber To which account number money will be sent
     * @param amount  Money to be deposited
     */
    public void transferMoney(Account fromAccount, User fromUserAccount, long toAccountNumber, double amount) {
        if (fromAccount.getBalance() < amount) {
            throw new NotEnoughBalanceException("Don't enough balance. You balance is: " + fromAccount.getBalance());
        }

        // First we need to withdraw money, and then send to another account
        withdrawMoney(fromUserAccount.getUsername(), fromAccount, amount);

        // Search user account to be sent money
        User toUser = getUserByAccountNumber(toAccountNumber);
        String toUserName = toUser.getUsername();

        // Then deposit money in the other account
        toUser.getAccounts().forEach(account -> {
            if (account.getAccountNumber() == toAccountNumber) {
                depositMoney(toUserName, account, amount);
                System.out.println("Money has been transferred successfully.");
            }
        });
    }

    public PersonalData getPersonalData(String username) {
        ReadFile readFile = new ReadFile();
        PersonalData personalData = readFile.getClientInformationByUsername(username);
        if (personalData == null) {
            throw new AccountNotFoundException("Cannot find detail");
        }
        return personalData;
    }

    public void updatePersonaData(String username, PersonalData personalData) {
        WriteFile writeFile = new WriteFile();
        try {
            writeFile.writeAccountInformation(username, personalData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
