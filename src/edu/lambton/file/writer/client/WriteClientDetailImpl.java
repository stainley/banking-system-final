package edu.lambton.file.writer.client;

import edu.lambton.model.AccountAbstract;
import edu.lambton.model.Client;
import edu.lambton.model.PersonalData;
import edu.lambton.util.DBFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.System.err;

public class WriteClientDetailImpl implements WriteClientDetail {

    @Override
    public void writeClientDetail(String username, PersonalData data) {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(DBFile.DB_PERSONAL_INFORMATION, true))) {
            bufferedWriter.newLine();
            bufferedWriter.write(username + "," + data.toString());
            bufferedWriter.flush();

            this.closeFile(bufferedWriter);
        } catch (IOException ioe) {
            err.println(ioe.getMessage());
        }
    }

    @Override
    public void writeClientDetail(Client userAccount) {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(DBFile.DB_FILE_NAME, true))) {

            for (AccountAbstract account : userAccount.getAccounts()) {
                bufferedWriter.write(userAccount.getUsername() + "," + account.getAccountInformation() + "\n");
            }
            bufferedWriter.flush();
            this.closeFile(bufferedWriter);
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    @Override
    public void closeFile(BufferedWriter file) {
        try {
            file.close();
        } catch (IOException e) {
            err.println(e.getMessage());
        }
    }
}
