package edu.lambton.file;

import edu.lambton.model.Account;
import edu.lambton.model.AccountType;
import edu.lambton.model.PersonalData;
import edu.lambton.model.User;
import edu.lambton.util.DBFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class ReadFile {

    public boolean validateIfFileExists(String fileName) {

        File file = new File(fileName);
        return file.exists();
    }

    public String readPasswordInformation(String username) {
        BufferedReader passBufferedReader = null;

        if (validateIfFileExists(DBFile.DB_FILE_NAME)) {
            try {
                passBufferedReader = new BufferedReader(new FileReader(DBFile.DB_FILE_CREDENTIAL));
                while (passBufferedReader.ready()) {
                    String credentialData = passBufferedReader.readLine();
                    String[] credentialColumn = credentialData.split(",");
                    if (credentialColumn[0].equals(username)) {
                        return credentialColumn[1];
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    passBufferedReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return "";
    }

    public HashMap<Long, Account> readAccountInformation(String accountName) throws IOException {
        BufferedReader bufferedReader = null;
        HashMap<Long, Account> accounts = new HashMap<>();
        try {

            bufferedReader = new BufferedReader(new FileReader(DBFile.DB_FILE_NAME));
            while (bufferedReader.ready()) {
                String accountInfo = bufferedReader.readLine();
                String[] data = accountInfo.split(",");
                Account account = new Account();
                for (int index = 0; index < data.length; index++) {
                    if (data[0].equals(accountName)) {
                        account.setAccountNumber(Long.parseLong(data[1]));
                        if (data[2].equals("Saving Account")) {
                            account.setAccountType(AccountType.SAVING_ACCOUNT);
                        } else {
                            account.setAccountType(AccountType.CHEQUING_ACCOUNT);
                        }
                        account.setBalance(Double.parseDouble(data[3]));
                        accounts.put(account.getAccountNumber(), account);
                    }
                }
            }

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            bufferedReader.close();
        }
        return accounts;
    }

    public User readUserInformation(long accountNumber) throws IOException {
        BufferedReader bufferedReader = null;

        List<Account> accounts = new ArrayList<>();
        User localUser = new User();
        try {

            bufferedReader = new BufferedReader(new FileReader(DBFile.DB_FILE_NAME));
            while (bufferedReader.ready()) {
                String accountName = "";
                String accountInfo = bufferedReader.readLine();
                String[] columns = accountInfo.split(",");
                Account account = new Account();
                for (int index = 0; index < columns.length; index++) {
                    if (columns[1].equals(String.valueOf(accountNumber))) {
                        accountName = columns[0];

                        account.setAccountNumber(Long.parseLong(columns[1]));
                        if (columns[2].equals("Saving Account")) {
                            account.setAccountType(AccountType.SAVING_ACCOUNT);
                        } else {
                            account.setAccountType(AccountType.CHEQUING_ACCOUNT);
                        }

                        account.setBalance(Double.parseDouble(columns[3]));
                        account.setCreationDate(LocalDateTime.parse(columns[4]));
                    }
                }
                if (account.getAccountNumber() != null) {
                    accounts.add(account);
                    localUser.setUsername(accountName);
                    localUser.setAccounts(accounts);
                }
            }
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            bufferedReader.close();
        }
        return localUser;
    }

    public List<Long> getAllAccountInformation() {
        BufferedReader br = null;
        List<Long> accountsNumber = new ArrayList<>();
        if (validateIfFileExists(DBFile.DB_FILE_NAME)) {
            try {
                br = new BufferedReader(new FileReader(DBFile.DB_FILE_NAME));

                while (br.ready()) {
                    String line = br.readLine();
                    String[] columns = line.split(",");
                    long accountNumber = Long.parseLong(columns[1]);
                    accountsNumber.add(accountNumber);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return accountsNumber;
    }

    public Set<String> getAllUsernameInformation() {
        BufferedReader br = null;
        Set<String> usernames = new HashSet<>();

        if (validateIfFileExists(DBFile.DB_FILE_NAME)) {
            try {
                br = new BufferedReader(new FileReader(DBFile.DB_FILE_NAME));
                while (br.ready()) {
                    String row = br.readLine();
                    String[] columns = row.split(",");
                    usernames.add(columns[0]);
                }
            } catch (IOException ioe) {
                System.err.println(ioe.getMessage());
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return usernames;
    }

    public User readUserInformation(String accountName) throws IOException {
        BufferedReader bufferedReader = null;

        List<Account> accounts = new ArrayList<>();
        User localUser = new User();
        try {

            bufferedReader = new BufferedReader(new FileReader(DBFile.DB_FILE_NAME));
            while (bufferedReader.ready()) {
                String accountInfo = bufferedReader.readLine();
                String[] columns = accountInfo.split(",");
                Account account = null;
                for (int index = 0; index < columns.length; index++) {
                    if (columns[0].equals(accountName)) {
                        account = new Account();
                        account.setAccountNumber(Long.parseLong(columns[1]));
                        if (columns[2].equals("Saving Account")) {
                            account.setAccountType(AccountType.SAVING_ACCOUNT);
                        } else {
                            account.setAccountType(AccountType.CHEQUING_ACCOUNT);
                        }

                        account.setBalance(Double.parseDouble(columns[3]));
                        account.setCreationDate(LocalDateTime.parse(columns[4]));
                    }
                }
                if (account != null) {
                    accounts.add(account);
                    localUser.setUsername(accountName);
                    localUser.setAccounts(accounts);
                }
            }
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            bufferedReader.close();
        }
        return localUser;
    }

    public PersonalData getClientInformationByUsername(String username) {

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(DBFile.DB_PERSONAL_INFORMATION));
            while (br.ready()) {
                String row = br.readLine();
                String[] column = row.split(",");
                if (column[0].equals(username)) {
                    return new PersonalData(column[1], Integer.parseInt(column[3]), column[2], column[5], column[4]);
                }
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
