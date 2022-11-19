package edu.lambton.file.writer.client;

import edu.lambton.model.Account;
import edu.lambton.model.Client;
import edu.lambton.model.PersonalData;
import edu.lambton.util.DBFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.System.*;

public class WriteClientDetailImpl implements WriteClientDetail {

    @Override
    public void writeClientDetail(String username, PersonalData data) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(DBFile.DB_PERSONAL_INFORMATION, true));
            bufferedWriter.write(username + "," + data.toString() + "\n");
            bufferedWriter.flush();
        } catch (IOException ioe) {
            err.println(ioe.getMessage());
        } finally {
            assert bufferedWriter != null;
            this.closeFile(bufferedWriter);
        }
    }

    @Override
    public void writeClientDetail(Client userAccount) {
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
            this.closeFile(bufferedWriter);
        }
    }

    @Override
    public void closeFile(BufferedWriter file) {
        try {
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
