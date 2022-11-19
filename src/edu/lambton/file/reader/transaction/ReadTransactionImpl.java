package edu.lambton.file.reader.transaction;

import edu.lambton.file.ValidateFile;
import edu.lambton.model.transaction.Transaction;
import edu.lambton.util.DBFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadTransactionImpl extends ValidateFile implements ReadTransaction<Transaction> {


    @Override
    public List<Transaction> readAllTransaction() throws FileNotFoundException {
        List<Transaction> transactions = new ArrayList<>();
        if (validateIfFileExists(DBFile.DB_TRANSACTION_REPORT)) {
            try (BufferedReader readerFile = new BufferedReader(new FileReader(DBFile.DB_TRANSACTION_REPORT))) {

                while (readerFile.ready()) {
                    String line = readerFile.readLine();
                    String[] columns = line.split(",");
                    System.out.println(columns[0]);
                    System.out.println(columns[1]);
                    System.out.println(columns[2]);

                    transactions.add(new Transaction(Long.parseLong(columns[0]), columns[1], null, null, null));
                }
                this.closeFile(readerFile);
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } else {
            throw new FileNotFoundException("File doesn't exist: " + DBFile.DB_TRANSACTION_REPORT);
        }
        return transactions;
    }

    @Override
    public void closeFile(BufferedReader file) {
        try {
            file.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
