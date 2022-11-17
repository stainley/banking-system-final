package edu.lambton.services;

import edu.lambton.Main;
import edu.lambton.exception.AccountNotFoundException;
import edu.lambton.exception.InvalidOptionException;
import edu.lambton.file.ReadFile;
import edu.lambton.file.WriteFile;
import edu.lambton.model.Account;
import edu.lambton.model.AccountType;
import edu.lambton.model.PersonalData;
import edu.lambton.model.User;
import edu.lambton.util.DBFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountService {

    private User createUser() {
        List<Account> accounts = new ArrayList<>();

        Scanner input = new Scanner(System.in);
        System.out.print("Username: ");
        String username = input.next().trim();

        // how many account you want to add
        System.out.print("How many account would you like to add? ");
        int numAccounts = input.nextInt();

        for (int i = 0; i < numAccounts; i++) {
            accounts.add(createAccount());
        }

        return new User(username, accounts);
    }

    private Account createAccount() {
        Scanner input = new Scanner(System.in);

        System.out.print("Account number: ");
        long accoundNum = input.nextLong();
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
        System.out.print("Choose balance: ");
        double initialBalance = input.nextDouble();

        return new Account(accoundNum, typeAccount(typeAccountSelected), initialBalance);
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
        String address = inputData.next();
        System.out.print("Birth of Year: ");
        int birthOfYear = inputData.nextInt();
        System.out.print("Email: ");
        String email = inputData.next();
        System.out.print("Phone Number: ");
        String phone = inputData.next();

        return new PersonalData(name, birthOfYear, address, email, phone);
    }

    public void registerAccount() {
        WriteFile writeFile = new WriteFile();

        try {
            writeFile.writeAccountInformation(createUser());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void register() {
        WriteFile writeFile = new WriteFile();

        try {
            writeFile.writeAccountInformation(registerInfoPersonaData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User login(String accountName) {
        ReadFile readFile = new ReadFile();
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
    }

    public void depositMoney(String accoutName, double money) {
        double newBalance = Main.globalAccount.getBalance() + money;
        Main.globalAccount.setBalance(newBalance);
        System.out.println("Money " + money + " deposited in you account. New balance is: $" + newBalance);

        WriteFile writeFile = new WriteFile();
        writeFile.updateBalanceInFile(accoutName, Main.globalAccount);
    }

    public void withdrawMoney(String accountName, double money) {
        double balance = Main.globalAccount.getBalance();
        if (balance < money) {
            System.out.println("Transaction could not be  processed");
        } else {
            double newBalance = balance - money;
            Main.globalAccount.setBalance(newBalance);

            WriteFile writeFile = new WriteFile();
            writeFile.updateBalanceInFile(accountName, Main.globalAccount);
        }

    }
}
