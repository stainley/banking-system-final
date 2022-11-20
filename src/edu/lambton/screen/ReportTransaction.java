package edu.lambton.screen;

import edu.lambton.model.transaction.Transaction;

import java.util.List;

public class ReportTransaction {
    private static ReportTransaction instance;
    private ReportTransaction() {
    }

    public static ReportTransaction getInstance() {
        if (instance == null) {
            instance = new ReportTransaction();
        }
        return instance;
    }

    public void reportAllTransactionMenuByUsername(List<Transaction> transactions) {
        System.out.print("""
                ********************************************************************************************************
                *                                      REPORT TRANSACTIONS
                ********************************************************************************************************
                """);

        transactions.forEach(transaction -> {
            System.out.println(transaction.getTransactionId() + " | " +
                    transaction.getAccount().getAccountInformation() + " | " +
                    transaction.getTransactionType());
            System.out.println("-----------------------------------------------------------------------------------------");
        });
    }
}
