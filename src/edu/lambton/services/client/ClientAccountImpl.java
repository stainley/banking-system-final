package edu.lambton.services.client;

import edu.lambton.exception.types.AccountNotFoundException;
import edu.lambton.file.reader.account.ReadClientInformation;
import edu.lambton.file.reader.account.ReadClientInformationImpl;
import edu.lambton.model.Client;
import edu.lambton.util.DBFile;

public class ClientAccountImpl implements ClientAccount {

    @Override
    public Client getUserByAccountNumber(long accountNumber) {
        ReadClientInformation readUserInformation = new ReadClientInformationImpl();

        if (readUserInformation.validateIfFileExists(DBFile.DB_FILE_NAME)) {

            Client accountsInfFile = readUserInformation.readClientInformation(accountNumber);

            if (accountsInfFile.getAccounts() == null) {
                throw new AccountNotFoundException("Account doesn't exit");
            } else {
                return accountsInfFile;
            }

        } else {
            System.out.println("File doesn't exist. Go back to login menu");
            throw new AccountNotFoundException("Account not found");
        }
    }
}
