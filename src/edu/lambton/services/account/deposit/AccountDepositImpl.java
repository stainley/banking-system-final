package edu.lambton.services.account.deposit;

import edu.lambton.file.writer.account.WriteAccountInformation;
import edu.lambton.file.writer.account.WriteAccountInformationImpl;
import edu.lambton.file.writer.transaction.WriteTransaction;
import edu.lambton.file.writer.transaction.WriteTransactionImpl;
import edu.lambton.model.AccountAbstract;
import edu.lambton.model.transaction.Transaction;
import edu.lambton.model.transaction.TransactionType;
import edu.lambton.services.transaction.TransactionServiceImpl;

import java.text.MessageFormat;
import java.time.LocalDateTime;

import static edu.lambton.Main.transactionId;

public class AccountDepositImpl implements AccountDeposit {

    @Override
    public void depositMoney(String accountName, AccountAbstract account, double money) {
        double newBalance = account.getBalance() + money;
        account.setBalance(newBalance);

        WriteAccountInformation writeAccountInformation = new WriteAccountInformationImpl();
        writeAccountInformation.writeAccountBalance(accountName, account);

        // Log the transaction into a file
        Transaction transaction = new Transaction();
        transactionId = new TransactionServiceImpl().createTransactionSequence();
        transaction.setTransactionId(transactionId);
        transaction.setUsername(accountName);
        transaction.setAccount(account);
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.DEPOSIT);

        WriteTransaction<Transaction> writeTransaction = new WriteTransactionImpl();
        writeTransaction.writeTransactionReport(transaction, money, transactionId);

        System.out.println(MessageFormat.format("Deposit of {0} has been completed successfully.  For more detailed information please see transaction form below.", money));
    }
}
