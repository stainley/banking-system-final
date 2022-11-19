package edu.lambton.file.writer.credential;

import edu.lambton.util.DBFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteCredentialFileImpl implements WriteCredentialFile{

    @Override
    public boolean writePasswordFile(String passwordInfo) throws IOException {
        BufferedWriter passBufferedWriter = null;
        try {
            passBufferedWriter = new BufferedWriter(new FileWriter(DBFile.DB_FILE_CREDENTIAL, true));

            passBufferedWriter.write(passwordInfo + "\n");
            passBufferedWriter.flush();
            return true;
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            assert passBufferedWriter != null;
            closeFile(passBufferedWriter);
        }
        return false;
    }

    @Override
    public void closeFile(BufferedWriter file) throws IOException {
        file.close();
    }
}
