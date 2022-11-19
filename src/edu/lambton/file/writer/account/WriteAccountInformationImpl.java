package edu.lambton.file.writer.account;

import edu.lambton.model.Account;
import edu.lambton.util.DBFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;

public class WriteAccountInformationImpl implements WriteAccountInformation {


    @Override
    public void writeAccountBalance(String owner, Account account) {
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

    @Override
    public void writeClientDetail(Account accountData) throws IOException {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(DBFile.DB_FILE_NAME, true));
            bufferedWriter.write(accountData.getAccountInformation() + "\n");

        } catch (IOException ioe) {
            err.println(ioe.getMessage());
            throw new IOException("Error processing file");
        } finally {
            assert bufferedWriter != null;
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
