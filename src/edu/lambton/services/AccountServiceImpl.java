package edu.lambton.services;

import edu.lambton.exception.types.AccountNotFoundException;
import edu.lambton.exception.types.InvalidCredentialException;
import edu.lambton.exception.types.InvalidOptionException;
import edu.lambton.file.reader.account.ReadClientInformation;
import edu.lambton.file.reader.account.ReadClientInformationImpl;
import edu.lambton.file.reader.client.ReadClientDetail;
import edu.lambton.file.reader.client.ReadClientDetailImpl;
import edu.lambton.file.writer.client.WriteClientDetail;
import edu.lambton.file.writer.client.WriteClientDetailImpl;
import edu.lambton.model.AccountAbstract;
import edu.lambton.model.Client;
import edu.lambton.model.PersonalData;
import edu.lambton.services.account.deposit.AccountDeposit;
import edu.lambton.services.account.deposit.AccountDepositImpl;
import edu.lambton.services.account.withdraw.AccountWithdraw;
import edu.lambton.services.account.withdraw.AccountWithdrawImpl;
import edu.lambton.services.client.CredentialService;
import edu.lambton.services.client.CredentialServiceImpl;
import edu.lambton.services.client.credential.ValidateCredential;
import edu.lambton.util.DBFile;

public class AccountServiceImpl extends AccountService {

    private final AccountDeposit accountDeposit = new AccountDepositImpl();
    private final AccountWithdraw accountWithdraw = new AccountWithdrawImpl();

    public void registerAccount() {
        CredentialService credentialService = new CredentialServiceImpl();
        WriteClientDetail writeClientDetail = new WriteClientDetailImpl();
        try {
            writeClientDetail.writeClientDetail(credentialService.createUser());
        } catch (InvalidOptionException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public Client login(String accountName, String password) {
        ReadClientInformation readClientInformation = new ReadClientInformationImpl();

        if (ValidateCredential.getInstance().validatePassword(accountName, password)) {
            if (readClientInformation.validateIfFileExists(DBFile.DB_FILE_NAME)) {
                Client accountsInFile = readClientInformation.readClientInformation(accountName);

                if (accountsInFile.getAccounts() == null) {
                    throw new AccountNotFoundException("Account doesn't exit");
                } else {
                    return accountsInFile;
                }
            } else {
                throw new AccountNotFoundException("Account not found");
            }
        } else {
            throw new InvalidCredentialException("Username/Password not matched");
        }
    }

    public PersonalData getPersonalData(String username) {
        ReadClientDetail clientDetailReadFile = new ReadClientDetailImpl();
        PersonalData personalData = clientDetailReadFile.getClientInformationByUsername(username);
        if (personalData == null) {
            throw new AccountNotFoundException("Cannot find detail");
        }
        return personalData;
    }

    @Override
    public void depositMoney(String accountName, AccountAbstract account, double money) {
        this.accountDeposit.depositMoney(accountName, account, money);
    }

    @Override
    public void withdrawMoney(String accountName, AccountAbstract account, double money) {
        this.accountWithdraw.withdrawMoney(accountName, account, money);
    }
}