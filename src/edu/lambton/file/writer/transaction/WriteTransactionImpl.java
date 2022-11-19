package edu.lambton.file.writer.transaction;

import edu.lambton.model.transaction.Transaction;
import edu.lambton.util.DBFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteTransactionImpl implements WriteTransaction<Transaction> {


    @Override
    public void writeTransactionReport(Transaction transaction, double amount, long transactionId) {
        try (BufferedWriter writeFile = new BufferedWriter(new FileWriter(DBFile.DB_TRANSACTION_REPORT, true))) {

            writeFile.write(transaction.getTransactionInfo() + "," + transaction.getTransactionType() + "," + String.format("%,3.2f", amount) + "\n");
            writeFile.flush();
            this.closeFile(writeFile);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    @Override
    public void closeFile(BufferedWriter file) throws IOException {
        try {
            file.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
