package edu.lambton.services.account.withdraw;

import edu.lambton.Main;
import edu.lambton.exception.BankException;
import edu.lambton.exception.types.NotEnoughBalanceException;
import edu.lambton.file.writer.account.WriteAccountInformation;
import edu.lambton.file.writer.account.WriteAccountInformationImpl;
import edu.lambton.file.writer.transaction.WriteTransaction;
import edu.lambton.file.writer.transaction.WriteTransactionImpl;
import edu.lambton.model.AccountAbstract;
import edu.lambton.model.transaction.Transaction;
import edu.lambton.model.transaction.TransactionType;
import edu.lambton.services.transaction.TransactionServiceImpl;

import java.time.LocalDateTime;

public class AccountWithdrawImpl implements AccountWithdraw{

    @Override
    public void withdrawMoney(String accountName, AccountAbstract account, double money) {
        double balance = account.getBalance();

        if (money <= 0) {
            throw new BankException("Please deposit a positive number greater than 1.");
        }

        if (balance < money) {
            throw new NotEnoughBalanceException("Transaction could not be  processed. Not enough balance");
        } else {
            double newBalance = balance - money;
            account.setBalance(newBalance);

            WriteAccountInformation writeAccountInformation = new WriteAccountInformationImpl();
            writeAccountInformation.writeAccountBalance(accountName, account);

            // Log the transaction into a file
            Transaction transaction = new Transaction();
            Main.transactionId = new TransactionServiceImpl().createTransactionSequence();
            transaction.setTransactionId(Main.transactionId);
            transaction.setUsername(accountName);
            transaction.setAccount(account);
            transaction.setTransactionTime(LocalDateTime.now());
            transaction.setTransactionType(TransactionType.WITHDRAW);

            WriteTransaction<Transaction> writeTransaction = new WriteTransactionImpl();
            writeTransaction.writeTransactionReport(transaction, money, Main.transactionId);

            System.out.println("Withdraw has been completed. " + String.format("$%,3.2f", money));
        }
    }
}
