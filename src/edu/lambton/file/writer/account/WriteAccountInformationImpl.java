package edu.lambton.file.writer.account;

import edu.lambton.model.Account;
import edu.lambton.model.AccountAbstract;
import edu.lambton.util.DBFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.err;

public class WriteAccountInformationImpl implements WriteAccountInformation {
    @Override
    public void writeAccountBalance(String owner, AccountAbstract account) {
        try {
            Path path = Path.of(DBFile.DB_FILE_NAME);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
            for (int counter = 0; counter < fileContent.size(); counter++) {

                String[] order = fileContent.get(counter).split(",");
                if (Long.parseLong(order[1]) == account.getAccountNumber()) {
                    fileContent.set(counter, owner + "," + account.getAccountInformation());
                    break;
                }
            }

            Files.write(path, fileContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            err.println(e.getMessage());
        }
    }

    /**
     * @deprecated writeClientDetail has been deprecated
     * @param accountData Account type
     * @throws IOException error writing in the file
     */
    @Deprecated(since = "Nov 18,2022")
    @Override
    public void writeClientDetail(Account accountData) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(DBFile.DB_FILE_NAME, true))) {
            bufferedWriter.write(accountData.getAccountInformation() + "\n");
            bufferedWriter.flush();
            this.closeFile(bufferedWriter);
        } catch (IOException ioe) {
            throw new IOException("Error processing file");
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
