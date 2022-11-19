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
        BufferedReader bufferedReader = null;

        List<Account> accounts = new ArrayList<>();
        Client localUser = new Client();
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
            System.out.println(ioe.getMessage());
        } finally {
            closeFile(bufferedReader);
        }
        return localUser;
    }

    @Override
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
                assert br != null;
                closeFile(br);
            }
        }
        return usernames;
    }

    @Override
    public Client readClientInformation(String accountName) {
        BufferedReader bufferedReader = null;

        List<Account> accounts = new ArrayList<>();
        Client localUser = new Client();
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
            this.closeFile(bufferedReader);
        }
        return localUser;
    }

    @Override
    public void closeFile(BufferedReader file) {
        try {
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
