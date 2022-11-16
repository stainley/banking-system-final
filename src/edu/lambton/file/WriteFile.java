package edu.lambton.file;

import edu.lambton.model.PersonalData;
import edu.lambton.util.DBFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {


    public void writeAccountInformation(PersonalData data) throws IOException {
        BufferedWriter bufferedWriter = null;
        try  {
            bufferedWriter = new BufferedWriter(new FileWriter(DBFile.DB_FILE_NAME, true));
            bufferedWriter.write(data.toString());

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            bufferedWriter.close();
        }
    }
}
