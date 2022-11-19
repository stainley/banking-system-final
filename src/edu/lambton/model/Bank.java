package edu.lambton.model;

public class Bank extends ContactInformation {
    private final String name;

    public Bank(String phoneNumber, String email, String name) {
        super(phoneNumber, email);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
