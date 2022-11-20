package edu.lambton.file.writer.account;

import edu.lambton.file.writer.IWriteFile;
import edu.lambton.model.Account;
import edu.lambton.model.AccountAbstract;

import java.io.BufferedWriter;
import java.io.IOException;

public interface WriteAccountInformation extends IWriteFile<BufferedWriter> {

    void writeAccountBalance(String owner, AccountAbstract account);

    /**
     * @deprecated writeClientDetail
     * This method has been deprecated
     * @param accountData Account type
     * @throws IOException error to be managed
     */
    @Deprecated(since = "Nov 18,2022")
    void writeClientDetail(Account accountData) throws IOException;
}
