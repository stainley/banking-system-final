package edu.lambton.model;

public class ContactInformation extends PersonalData {
    private String phoneNumber;
    private String email;

    public ContactInformation(String name, String address, int birthOfYear) {
        super(name, address, birthOfYear);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
