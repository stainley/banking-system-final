package edu.lambton.services;

import edu.lambton.file.ReadFile;
import edu.lambton.file.WriteFile;
import edu.lambton.model.PersonalData;
import edu.lambton.util.DBFile;

import java.io.IOException;
import java.util.Scanner;

public class AccountService {

    private PersonalData registerInfoPersonaData() {
        Scanner inputData = new Scanner(System.in);
        System.out.print("Name: ");
        String name = inputData.next();
        System.out.print("Address: ");
        String address = inputData.next();
        System.out.print("Birth of Year: ");
        int birthOfYear = inputData.nextInt();
        System.out.print("Email: ");
        String email = inputData.next();
        System.out.print("Phone Number: ");
        String phone = inputData.next();

        return new PersonalData(name, birthOfYear, address, email, phone);
    }


    public void register() {
        WriteFile writeFile = new WriteFile();

        try {
            writeFile.writeAccountInformation(registerInfoPersonaData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void login() {
       ReadFile readFile =  new ReadFile();
       if (readFile.validateIfFileExists(DBFile.DB_FILE_NAME)) {
           try {
               readFile.readAccountInformation();
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       } else {
           System.out.println("File doesn't exist. Go back to login menu");
       }
    }
}
