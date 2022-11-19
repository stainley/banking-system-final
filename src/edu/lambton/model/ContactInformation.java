package edu.lambton.model;

public class ContactInformation {
    private String phoneNumber;
    private String email;

    /**
     * Create an object of Contact Information using 2 params
     * @param phoneNumber String phone number
     * @param email Sting email
     */
    public ContactInformation(String phoneNumber, String email) {
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Reteurn the email
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "ContactInformation{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
