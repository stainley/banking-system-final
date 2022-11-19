package edu.lambton.file.reader.account;

import edu.lambton.file.ValidateFile;
import edu.lambton.model.Account;
import edu.lambton.model.AccountType;
import edu.lambton.model.Client;
import edu.lambton.util.DBFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReadClientInformationImpl extends ValidateFile implements ReadClientInformation {

    @Override
    public Client readClientInformation(long accountNumber) {

        List<Account> accounts = new ArrayList<>();
        Client localUser = new Client();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DBFile.DB_FILE_NAME))) {

            while (bufferedReader.ready()) {
                String accountName = "";
                String accountInfo = bufferedReader.readLine();
                String[] columns = accountInfo.split(",");
                Account account = new Account();
                for (int index = 0; index < columns.length; index++) {
                    if (columns[1].equals(String.valueOf(accountNumber))) {
                        accountName = columns[0];
                        setAccountNumber(columns, account);
                    }
                }
                if (account.getAccountNumber() != null) {
                    accounts.add(account);
                    localUser.setUsername(accountName);
                    localUser.setAccounts(accounts);
                }
            }
            closeFile(bufferedReader);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return localUser;
    }

    private void setAccountNumber(String[] columns, Account account) {
        account.setAccountNumber(Long.parseLong(columns[1]));
        if (columns[2].equals("Saving Account")) {
            account.setAccountType(AccountType.SAVING_ACCOUNT);
        } else {
            account.setAccountType(AccountType.CHEQUING_ACCOUNT);
        }

        account.setBalance(Double.parseDouble(columns[3]));
        account.setCreationDate(LocalDateTime.parse(columns[4]));
    }

    @Override
    public Set<String> getAllUsernameInformation() {

        Set<String> usernames = new HashSet<>();

        if (validateIfFileExists(DBFile.DB_FILE_NAME)) {
            try (BufferedReader br = new BufferedReader(new FileReader(DBFile.DB_FILE_NAME))) {
                while (br.ready()) {
                    String row = br.readLine();
                    String[] columns = row.split(",");
                    usernames.add(columns[0]);
                }
                closeFile(br);
            } catch (IOException ioe) {
                System.err.println(ioe.getMessage());
            }
        }
        return usernames;
    }

    @Override
    public Client readClientInformation(String accountName) {


        List<Account> accounts = new ArrayList<>();
        Client localUser = new Client();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DBFile.DB_FILE_NAME))) {

            while (bufferedReader.ready()) {
                String accountInfo = bufferedReader.readLine();
                String[] columns = accountInfo.split(",");
                Account account = null;
                for (int index = 0; index < columns.length; index++) {
                    if (columns[0].equals(accountName)) {
                        account = new Account();
                        setAccountNumber(columns, account);
                    }
                }
                if (account != null) {
                    accounts.add(account);
                    localUser.setUsername(accountName);
                    localUser.setAccounts(accounts);
                }
            }
            this.closeFile(bufferedReader);
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        return localUser;
    }

    @Override
    public void closeFile(BufferedReader file) {
        try {
            file.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
