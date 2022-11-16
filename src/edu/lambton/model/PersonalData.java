package edu.lambton.model;

public class PersonalData {
    private String name;
    private String address;
    private int birthOfYear;

    public PersonalData(String name, String address, int birthOfYear) {
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
}
