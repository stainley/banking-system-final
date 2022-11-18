package edu.lambton.file;

import edu.lambton.model.Account;
import edu.lambton.model.PersonalData;
import edu.lambton.model.User;
import edu.lambton.util.DBFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class WriteFile {


    public void writeAccountInformation(String username, PersonalData data) throws IOException {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(DBFile.DB_PERSONAL_INFORMATION, true));
            bufferedWriter.write(username + "," + data.toString() + "\n");
            bufferedWriter.flush();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            bufferedWriter.close();
        }
    }

    public void writeAccountInformation(Account accountData) throws IOException {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(DBFile.DB_FILE_NAME, true));
            bufferedWriter.write(accountData.getAccountInformation() + "\n");

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            bufferedWriter.close();
        }
    }

    public void writeAccountInformation(User userAccount) throws IOException {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(DBFile.DB_FILE_NAME, true));

            for (Account account : userAccount.getAccounts()) {
                bufferedWriter.write(userAccount.getUsername() + "," + account.getAccountInformation() + "\n");
            }
            bufferedWriter.flush();

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            bufferedWriter.close();
        }
    }

    public boolean writePasswordFile(String passwordInfo) throws IOException {
        BufferedWriter passBufferedWriter = null;
        try {
            passBufferedWriter = new BufferedWriter(new FileWriter(DBFile.DB_FILE_CREDENTIAL, true));

            passBufferedWriter.write(passwordInfo + "\n");
            passBufferedWriter.flush();
            return true;
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            passBufferedWriter.close();
        }
        return false;
    }

    public void updateBalanceInFile(String owner, Account account) {

        try {
            List<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of(DBFile.DB_FILE_NAME), StandardCharsets.UTF_8));
            for (int counter = 0; counter < fileContent.size(); counter++) {

                String[] order = fileContent.get(counter).split(",");
                if (Long.parseLong(order[1]) == account.getAccountNumber()) {
                    fileContent.set(counter, owner + "," + account.getAccountInformation());
                    break;
                }
            }

            Files.write(Path.of(DBFile.DB_FILE_NAME), fileContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
