package edu.lambton.services.transaction;

import edu.lambton.file.reader.transaction.ReadTransaction;
import edu.lambton.file.reader.transaction.ReadTransactionImpl;
import edu.lambton.model.transaction.Transaction;
import edu.lambton.services.transaction.TransactionService;

import java.io.FileNotFoundException;
import java.util.List;

public class TransactionServiceImpl implements TransactionService<Transaction> {


    @Override
    public List<Transaction> getAllTransactionByUsername(String username) {
        ReadTransaction<Transaction> readTransaction = new ReadTransactionImpl();
        try {
            return readTransaction.readAllTransaction().stream()
                    .filter(transaction -> transaction.getUsername().equals(username))
                    .toList();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
