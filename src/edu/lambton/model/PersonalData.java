package edu.lambton.model;

public class PersonalData extends ContactInformation {
    private final String name;
    private final String address;
    private final int birthOfYear;

    public PersonalData(String name, int birthOfYear, String address, String email, String phoneNumber) {
        super(phoneNumber, email);
        this.name = name;
        this.address = address;
        this.birthOfYear = birthOfYear;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getBirthOfYear() {
        return birthOfYear;
    }

    public String getPersonaData() {
        return "Name" + getName() + "," +
                "Address" + getAddress() + "," +
                "Birth of Year" + getBirthOfYear() + ",";
    }

    @Override
    public String toString() {
        return name + "," + address + "," + birthOfYear + "," + getPhoneNumber() + "," + getEmail();
    }
}
