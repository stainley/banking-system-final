package edu.lambton.file.reader.client;

import edu.lambton.model.PersonalData;
import edu.lambton.util.DBFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class ReadClientDetailImpl implements ReadClientDetail {

    @Override
    public PersonalData getClientInformationByUsername(String username) {

        try (BufferedReader br = new BufferedReader(new FileReader(DBFile.DB_PERSONAL_INFORMATION))) {
            while (br.ready()) {
                String row = br.readLine();
                String[] column = row.split(",");
                if (column[0].equals(username)) {
                    return new PersonalData(column[1], LocalDate.now().getYear() - Integer.parseInt(column[3]), column[2], column[5], column[4]);
                }
            }
            this.closeFile(br);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return null;
    }

    @Override
    public void closeFile(BufferedReader file) {
        try {
            file.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
