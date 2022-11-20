package edu.lambton.services.transaction;

import edu.lambton.Main;
import edu.lambton.file.reader.transaction.ReadTransaction;
import edu.lambton.file.reader.transaction.ReadTransactionImpl;
import edu.lambton.model.transaction.Transaction;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
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
        return new ArrayList<>();
    }

    public long createTransactionSequence() {
        long newSequence;
        // find the last sequence in the file
        ReadTransaction<Transaction> readTransaction = new ReadTransactionImpl();
        try {
            List<Transaction> transactions = readTransaction.readAllTransaction();
            newSequence = transactions.stream()
                    .max(Comparator.comparing(Transaction::getTransactionId))
                    .orElse(new Transaction()).getTransactionId();

            // generate a new transaction
            newSequence += 1;
            return newSequence;
        } catch (FileNotFoundException e) {
            System.out.println("Using the existence sequence");
            System.out.println(e.getMessage());
        }
        return Main.transactionId;
    }
}
