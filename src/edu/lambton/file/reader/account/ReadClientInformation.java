package edu.lambton.file.reader.account;

import edu.lambton.file.reader.IReadFile;
import edu.lambton.model.Client;

import java.io.BufferedReader;
import java.util.Set;

public interface ReadClientInformation extends IReadFile<BufferedReader> {
    boolean validateIfFileExists(String fileName);
    Client readClientInformation(long accountNumber);
    Set<String> getAllUsernameInformation();
    Client readClientInformation(String accountName);
}
