package edu.lambton.services.client;

import edu.lambton.exception.BankException;
import edu.lambton.exception.types.AccountNotAvailableException;
import edu.lambton.exception.types.InvalidFormatException;
import edu.lambton.exception.types.InvalidOptionException;
import edu.lambton.file.reader.account.ReadAccountInformation;
import edu.lambton.file.reader.account.ReadAccountInformationImpl;
import edu.lambton.file.reader.account.ReadClientInformation;
import edu.lambton.file.reader.account.ReadClientInformationImpl;
import edu.lambton.file.writer.client.WriteClientDetail;
import edu.lambton.file.writer.client.WriteClientDetailImpl;
import edu.lambton.file.writer.credential.WriteCredentialFile;
import edu.lambton.file.writer.credential.WriteCredentialFileImpl;
import edu.lambton.model.AccountAbstract;
import edu.lambton.model.Client;
import edu.lambton.model.PersonalData;
import edu.lambton.model.type.AccountType;
import edu.lambton.model.type.ChequingAccount;
import edu.lambton.model.type.SavingAccount;
import edu.lambton.services.account.TypeAccountService;
import edu.lambton.util.AccountNumberGenerator;
import edu.lambton.util.MenuUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CredentialServiceImpl implements CredentialService {

    private final TypeAccountService typeAccountService = TypeAccountService.getInstance();

    public void register(String username) {
        WriteClientDetail writeClientDetail = new WriteClientDetailImpl();

        try {
            writeClientDetail.writeClientDetail(username, registerInfoPersonaData());
            System.out.println("User registered successfully.\n\n");
        } catch (IOException e) {
            throw new BankException("An error had occurred: " + e.getMessage());
        } catch (InvalidFormatException ife) {
            throw new InvalidFormatException(ife.getMessage());
        }
    }

    @Override
    public Client createUser() {
        List<AccountAbstract> accounts = new ArrayList<>();

        Scanner input = new Scanner(System.in);
        String username;

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
            while (true) {
                try {
                    register(username);
                    break;
                } catch (BankException bankException) {
                    System.err.println(bankException.getMessage());
                    System.out.println("Try again.");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                    MenuUtil.getInstance().clearScreen();
                }
            }

            // how many account you want to add
            System.out.print("How many account would you like to add? [MAXIMUM 2]");
            int numAccounts = input.nextInt();
            if (numAccounts == 0 || numAccounts > 2) {
                throw new InvalidOptionException("You only can create 1 or 2 accounts.");
            }
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
            throw new BankException("An error has occurred:" + e.getMessage());
        }
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
        long accountNumber;
        while (true) {
            try {
                accountNumber = new AccountNumberGenerator().generatorAccountNumber();
                System.out.println("Account number: " + accountNumber);

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
        AccountType typeAccount = typeAccountService.typeAccount(typeAccountSelected);

        if (typeAccount == AccountType.SAVING_ACCOUNT) {
            return new SavingAccount(accountNumber, initialBalance);
        } else {
            return new ChequingAccount(accountNumber, initialBalance);
        }
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


    private boolean usernameIsAvailable(String username) {
        ReadClientInformation readFileUserInformation = new ReadClientInformationImpl();
        for (String usernameFound : readFileUserInformation.getAllUsernameInformation()) {
            if (Objects.equals(usernameFound, username)) {
                return false;
            }
        }
        return true;
    }

    private PersonalData registerInfoPersonaData() {
        Scanner inputData = new Scanner(System.in);
        System.out.print("Name: ");
        String name = inputData.useDelimiter("\n").next();
        System.out.print("Address: ");
        StringBuilder address = new StringBuilder();
        String addressClean = inputData.useDelimiter("\n").next();
        if (addressClean.contains(",")) {
            String collect = Arrays.stream(addressClean.split(","))
                    .map(value -> value + " ")
                    .collect(Collectors.joining());
            address.append(collect);
        } else {
            address.append(addressClean);
        }


        System.out.print("Birth of Year: ");
        int birthOfYear = inputData.nextInt();
        if (birthOfYear < 1 || birthOfYear > LocalDate.now().getYear() || (LocalDate.now().getYear() - birthOfYear >= 150)) {
            throw new InvalidFormatException("Bad year format");
        }
        System.out.print("Email: ");
        String email = inputData.next();
        String emailRegex = "^(.+)@(\\\\S+)$";
        if (email.isEmpty() || patternMatches(email, emailRegex)) {
            throw new InvalidFormatException("Bad email format");
        }

        System.out.print("Phone Number: ");
        String phone = inputData.next();

        return new PersonalData(name, birthOfYear, address.toString(), email, phone);
    }

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
