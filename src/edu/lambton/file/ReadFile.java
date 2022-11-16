package edu.lambton.file;

import edu.lambton.util.DBFile;

import java.io.*;

public class ReadFile {

    public boolean validateIfFileExists(String fileName) {

        File file = new File(fileName);
        return file.exists();
    }

    public void readAccountInformation() throws IOException {
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(DBFile.DB_FILE_NAME));
            while(bufferedReader.ready()) {
                String accountInfo  = bufferedReader.readLine();
                System.out.println(accountInfo);
            }

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            bufferedReader.close();
        }
    }

    public void writeAccountInformation(String data) throws IOException {
        BufferedWriter bufferedWriter = null;
        try  {
            bufferedWriter = new BufferedWriter(new FileWriter(DBFile.DB_FILE_NAME, true));
            bufferedWriter.write(data);

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            bufferedWriter.close();
        }
    }
}
