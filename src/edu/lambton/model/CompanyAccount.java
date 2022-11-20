package edu.lambton.model;

public class CompanyAccount {

    private String companyName;
    private long accountNumber;

    public CompanyAccount(String companyName, long accountNumber) {
        this.companyName = companyName;
        this.accountNumber = accountNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }
}
