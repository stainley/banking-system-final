package edu.lambton.util;

public class DBFile {
    private static final String DIRECTORY = "data/";
    public static final String DB_FILE_NAME = DIRECTORY + "information.csv";
    public static final String DB_FILE_CREDENTIAL = DIRECTORY + "credential.txt";
    public static final String DB_PERSONAL_INFORMATION = DIRECTORY + "personal_data.csv";
    public static final String DB_TRANSACTION_REPORT = DIRECTORY + "transaction.csv";

    public static final String DB_COMPANY_ACCOUNT = DIRECTORY + "companies_account.csv";

    private DBFile() {
    }
}
