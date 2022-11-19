package edu.lambton.file.reader.client;

import edu.lambton.model.PersonalData;
import edu.lambton.util.DBFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadClientDetailImpl implements ReadClientDetail {

    @Override
    public PersonalData getClientInformationByUsername(String username) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(DBFile.DB_PERSONAL_INFORMATION));
            while (br.ready()) {
                String row = br.readLine();
                String[] column = row.split(",");
                if (column[0].equals(username)) {
                    return new PersonalData(column[1], Integer.parseInt(column[3]), column[2], column[5], column[4]);
                }
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } finally {
            this.closeFile(br);
        }
        return null;
    }

    @Override
    public void closeFile(BufferedReader file) {
        try {
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
