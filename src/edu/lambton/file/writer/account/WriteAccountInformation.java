package edu.lambton.file.writer.account;

import edu.lambton.file.writer.IWriteFile;
import edu.lambton.model.Account;

import java.io.BufferedWriter;
import java.io.IOException;

public interface WriteAccountInformation extends IWriteFile<BufferedWriter> {

    void writeAccountBalance(String owner, Account account);

    void writeClientDetail(Account accountData) throws IOException;

}
