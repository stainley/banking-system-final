package edu.lambton.services;

import edu.lambton.Main;
import edu.lambton.exception.BankException;
import edu.lambton.exception.types.*;
import edu.lambton.file.reader.account.ReadAccountInformation;
import edu.lambton.file.reader.account.ReadAccountInformationImpl;
import edu.lambton.file.reader.account.ReadClientInformation;
import edu.lambton.file.reader.account.ReadClientInformationImpl;
import edu.lambton.file.reader.client.ReadClientDetail;
import edu.lambton.file.reader.client.ReadClientDetailImpl;
import edu.lambton.file.reader.credential.CredentialReadFile;
import edu.lambton.file.reader.credential.CredentialReadFileImpl;
import edu.lambton.file.reader.transaction.ReadTransaction;
import edu.lambton.file.reader.transaction.ReadTransactionImpl;
import edu.lambton.file.writer.account.WriteAccountInformation;
import edu.lambton.file.writer.account.WriteAccountInformationImpl;
import edu.lambton.file.writer.client.WriteClientDetail;
import edu.lambton.file.writer.client.WriteClientDetailImpl;
import edu.lambton.file.writer.credential.WriteCredentialFile;
import edu.lambton.file.writer.credential.WriteCredentialFileImpl;
import edu.lambton.file.writer.transaction.WriteTransaction;
import edu.lambton.file.writer.transaction.WriteTransactionImpl;
import edu.lambton.model.AccountAbstract;
import edu.lambton.model.Client;
import edu.lambton.model.PersonalData;
import edu.lambton.model.transaction.Transaction;
import edu.lambton.model.transaction.TransactionType;
import edu.lambton.model.type.AccountType;
import edu.lambton.model.type.ChequingAccount;
import edu.lambton.model.type.SavingAccount;
import edu.lambton.util.AccountNumberGenerator;
import edu.lambton.util.DBFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class AccountService {

    private Client createUser() {
        List<AccountAbstract> accounts = new ArrayList<>();

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
            System.out.print("How many account would you like to add? [MAXIMUM 2]");
            int numAccounts = input.nextInt();

            for (int i = 0; i < numAccounts; i++) {
                accounts.add(createAccount());
            }
            return new Client(username, accounts);
        }
        return null;
    }

    private boolean createPassword(String username, String password) {
        WriteCredentialFile writeCredentialFile = new WriteCredentialFileImpl();
        StringBuilder sb = new StringBuilder();
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

        sb.append(username).append(",").append(encodedPassword);

        try {
            return writeCredentialFile.writePasswordFile(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validatePassword(String username, String password) {
        CredentialReadFile credentialReadFile = new CredentialReadFileImpl();
        String passwordFromFile = credentialReadFile.readPasswordInformation(username);

        byte[] decodedPassword = Base64.getDecoder().decode(passwordFromFile);
        return password.equals(new String(decodedPassword));
    }

    private AccountAbstract createAccount() {
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
        //long accountNumber = 0;
        long accountNumber = 0;
        while (true) {
            try {
                accountNumber = new AccountNumberGenerator().generatorAccountNumber();
                System.out.println("Account number: " + accountNumber);
                //accountNumber = input.nextLong();

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
        AccountType typeAccount = typeAccount(typeAccountSelected);

        if (typeAccount == AccountType.SAVING_ACCOUNT) {
            return new SavingAccount(accountNumber, initialBalance);
        } else {
            return new ChequingAccount(accountNumber, initialBalance);
        }
    }

    private boolean usernameIsAvailable(String username) {
        ReadClientInformation readFileUserInformation = new ReadClientInformationImpl();
        for (String usernameFound : readFileUserInformation.getAllUsernameInformation()) {
            if (Objects.equals(usernameFound, username)) {
                return false;
            }
        }
        return true;
    }

    private boolean accountNumberIsAvailable(long accountNumber) {
        ReadAccountInformation readAccountInformation = new ReadAccountInformationImpl();
        for (long accNumberFound : readAccountInformation.getAllAccountInformation()) {
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
        WriteClientDetail writeClientDetail = new WriteClientDetailImpl();
        writeClientDetail.writeClientDetail(createUser());
    }

    public void register(String username) {
        WriteClientDetail writeClientDetail = new WriteClientDetailImpl();

        try {
            writeClientDetail.writeClientDetail(username, registerInfoPersonaData());
            System.out.println("User registered successfully.\n\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Client getUserByAccountNumber(long accountNumber) {
        ReadClientInformation readUserInformation = new ReadClientInformationImpl();

        if (readUserInformation.validateIfFileExists(DBFile.DB_FILE_NAME)) {

            Client accountsInfFile = readUserInformation.readClientInformation(accountNumber);

            if (accountsInfFile.getAccounts() == null) {
                throw new AccountNotFoundException("Account doesn't exit");
            } else {
                return accountsInfFile;
            }

        } else {
            System.out.println("File doesn't exist. Go back to login menu");
            throw new AccountNotFoundException("Account not found");
        }
    }

    public Client login(String accountName, String password) {
        ReadClientInformation readClientInformation = new ReadClientInformationImpl();

        if (validatePassword(accountName, password)) {
            if (readClientInformation.validateIfFileExists(DBFile.DB_FILE_NAME)) {
                Client accountsInFile = readClientInformation.readClientInformation(accountName);

                if (accountsInFile.getAccounts() == null) {
                    throw new AccountNotFoundException("Account doesn't exit");
                } else {
                    return accountsInFile;
                }
            } else {
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
    public void depositMoney(String accountName, AccountAbstract account, double money) {
        double newBalance = account.getBalance() + money;
        account.setBalance(newBalance);

        WriteAccountInformation writeAccountInformation = new WriteAccountInformationImpl();
        writeAccountInformation.writeAccountBalance(accountName, account);

        // Log the transaction into a file
        Transaction transaction = new Transaction();
        Main.transactionId = this.createTransactionSequence();
        transaction.setTransactionId(Main.transactionId);
        transaction.setUsername(accountName);
        transaction.setAccount(account);
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.DEPOSIT);

        WriteTransaction<Transaction> writeTransaction = new WriteTransactionImpl();
        writeTransaction.writeTransactionReport(transaction, money, Main.transactionId);

        System.out.println("Money " + money + " deposited in you account. New balance is: $" + newBalance);
    }

    /**
     * Obtain the last sequence a return a new sequence incremented
     *
     * @return long new sequence
     */
    public long createTransactionSequence() {
        long newSequence;
        // find the last sequence in the file
        ReadTransaction<Transaction> readTransaction = new ReadTransactionImpl();
        try {
            List<Transaction> transactions = readTransaction.readAllTransaction();
            newSequence = transactions.stream()
                    .max(Comparator.comparing(Transaction::getTransactionId))
                    .orElse(null).getTransactionId();

            // generate a new transaction
            newSequence += 1;
            return newSequence;
        } catch (FileNotFoundException e) {
            System.out.println("Using the existence sequence");
            System.out.println(e.getMessage());
        }
        return Main.transactionId;
    }

    /***
     * Used to withdraw money from account
     * @param accountName Username of the account
     * @param account That store the account data
     * @param money Amount to be deducted from account
     */
    public void withdrawMoney(String accountName, AccountAbstract account, double money) {
        double balance = account.getBalance();

        if (money <= 0) {
            throw new BankException("Please deposit a positive number greater than 1.");
        }

        if (balance < money) {
            throw new NotEnoughBalanceException("Transaction could not be  processed. Not enough balance");
        } else {
            double newBalance = balance - money;
            account.setBalance(newBalance);

            WriteAccountInformation writeAccountInformation = new WriteAccountInformationImpl();
            writeAccountInformation.writeAccountBalance(accountName, account);
        }
    }

    /***
     * Transfer money from one account to another account
     * @param fromAccount From which account money will be deducted
     * @param fromUserAccount From which account number money will be withdrawn
     * @param toAccountNumber To which account number money will be sent
     * @param amount  Money to be deposited
     */
    public void transferMoney(AccountAbstract fromAccount, Client fromUserAccount, long toAccountNumber, double amount) {
        if (fromAccount.getBalance() < amount) {
            throw new NotEnoughBalanceException("Don't enough balance. You balance is: " + fromAccount.getBalance());
        }

        // First we need to withdraw money, and then send to another account
        withdrawMoney(fromUserAccount.getUsername(), fromAccount, amount);

        // Search user account to be sent money
        Client toUser = getUserByAccountNumber(toAccountNumber);
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
        ReadClientDetail clientDetailReadFile = new ReadClientDetailImpl();
        PersonalData personalData = clientDetailReadFile.getClientInformationByUsername(username);
        if (personalData == null) {
            throw new AccountNotFoundException("Cannot find detail");
        }
        return personalData;
    }

    public void updatePersonaData(String username, PersonalData personalData) {
        WriteClientDetail writeClientDetail = new WriteClientDetailImpl();
        try {
            writeClientDetail.writeClientDetail(username, personalData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
