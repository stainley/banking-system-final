package edu.lambton.file.reader.account;

import edu.lambton.file.ValidateFile;
import edu.lambton.util.DBFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadAccountInformationImpl extends ValidateFile implements ReadAccountInformation {

    @Override
    public List<Long> getAllAccountInformation() {

        List<Long> accountsNumber = new ArrayList<>();
        if (validateIfFileExists(DBFile.DB_FILE_NAME)) {
            try (BufferedReader br = new BufferedReader(new FileReader(DBFile.DB_FILE_NAME))) {

                while (br.ready()) {
                    String line = br.readLine();
                    String[] columns = line.split(",");
                    long accountNumber = Long.parseLong(columns[1]);
                    accountsNumber.add(accountNumber);
                }
                this.closeFile(br);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return accountsNumber;
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
