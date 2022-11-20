package edu.lambton.file.reader.transaction;

import edu.lambton.file.ValidateFile;
import edu.lambton.model.AccountAbstract;
import edu.lambton.model.transaction.Transaction;
import edu.lambton.model.transaction.TransactionType;
import edu.lambton.model.type.AccountType;
import edu.lambton.model.type.ChequingAccount;
import edu.lambton.model.type.SavingAccount;
import edu.lambton.util.DBFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
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
                    long transactionId = Long.parseLong(columns[0]);
                    String username = columns[1];
                    long accountNumber = Long.parseLong(columns[2]);
                    String accountType = columns[3];
                    double balance = Double.parseDouble(columns[4]);
                    String transactionDate = columns[5];
                    String typeTransaction = columns[6];
                    String amount = columns[7];

                    TransactionType transactionType;
                    if (typeTransaction.equals(TransactionType.WITHDRAW.name())) {
                        transactionType = TransactionType.WITHDRAW;
                    } else {
                        transactionType = TransactionType.DEPOSIT;
                    }

                    AccountAbstract account;
                    if (accountType.equals(AccountType.SAVING_ACCOUNT.name())) {
                        account = new SavingAccount();
                        account.setAccountNumber(accountNumber);
                        account.setAccountType(AccountType.SAVING_ACCOUNT);
                        account.setCreationDate(LocalDateTime.parse(transactionDate));
                        account.setBalance(Double.parseDouble(amount));
                    } else {
                        account = new ChequingAccount();
                        account.setAccountNumber(accountNumber);
                        account.setAccountType(AccountType.CHEQUING_ACCOUNT);
                        account.setBalance(Double.parseDouble(amount));
                        account.setCreationDate(LocalDateTime.parse(transactionDate));
                    }

                    Transaction transaction = new Transaction(transactionId, username, transactionType, account, LocalDateTime.parse(transactionDate));

                    transactions.add(transaction);
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
