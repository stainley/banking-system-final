package edu.lambton.file.reader.company;

import edu.lambton.model.CompanyAccount;
import edu.lambton.util.DBFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadCompanyAccountImpl implements ReadCompanyAccount<CompanyAccount> {
    @Override
    public List<CompanyAccount> readAllCompanyAccount() {
        int header = 0;
        List<CompanyAccount> companyAccounts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(DBFile.DB_COMPANY_ACCOUNT))) {
            while (br.ready()) {
                String row = br.readLine();
                if (header < 1) {
                    header++;
                    continue;
                }
                String[] column = row.split(",");
                companyAccounts.add(new CompanyAccount(column[0], Long.parseLong(column[1])));
            }
            closeFile(br);
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        return companyAccounts;
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
