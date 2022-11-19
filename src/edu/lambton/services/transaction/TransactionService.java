package edu.lambton.services.transaction;

import edu.lambton.model.transaction.Transaction;

import java.util.List;

public interface TransactionService<T extends Transaction> {

    List<T> getAllTransactionByUsername(String username);
}
