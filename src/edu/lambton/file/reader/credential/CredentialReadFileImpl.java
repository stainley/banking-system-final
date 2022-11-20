package edu.lambton.file.reader.credential;

import edu.lambton.file.ValidateFile;
import edu.lambton.util.DBFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CredentialReadFileImpl extends ValidateFile implements CredentialReadFile {

    @Override
    public String readPasswordInformation(String username) {


        if (validateIfFileExists(DBFile.DB_FILE_NAME)) {
            try (BufferedReader passBufferedReader = new BufferedReader(new FileReader(DBFile.DB_FILE_CREDENTIAL))) {

                while (passBufferedReader.ready()) {
                    String credentialData = passBufferedReader.readLine();
                    String[] credentialColumn = credentialData.split(",");
                    if (credentialColumn[0].equals(username)) {
                        return credentialColumn[1];
                    }
                }
                this.closeFile(passBufferedReader);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return "";
    }

    @Override
    public void closeFile(BufferedReader connection) {

        try {
            connection.close();
        } catch (IOException ioException) {
            System.out.println("An error has occurred: " + ioException.getMessage());
        }
    }
}
