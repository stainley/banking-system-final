package edu.lambton.file.writer.transaction;

import edu.lambton.file.writer.IWriteFile;
import edu.lambton.model.transaction.Bank;

import java.io.BufferedWriter;

public interface WriteTransaction<T extends Bank> extends IWriteFile<BufferedWriter> {
    void writeTransactionReport(T transaction, double amount, long transactionId);
}
