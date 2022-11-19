package edu.lambton.services.client;

import edu.lambton.model.Client;

public interface ClientAccount {
    Client getUserByAccountNumber(long accountNumber);
}
