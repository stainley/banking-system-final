package edu.lambton.services.account;

import edu.lambton.exception.types.InvalidOptionException;
import edu.lambton.model.type.AccountType;

public class TypeAccountService {
    private static TypeAccountService instance;

    private TypeAccountService() {
    }

    public static TypeAccountService getInstance() {
        if (instance == null) {
            instance = new TypeAccountService();
        }
        return instance;
    }
    public AccountType typeAccount(int accountNumber) {
        return switch (accountNumber) {
            case 1 -> AccountType.CHEQUING_ACCOUNT;
            case 2 -> AccountType.SAVING_ACCOUNT;
            default -> throw new InvalidOptionException("Invalid option type account");
        };
    }
}
