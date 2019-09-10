package com.rit.basa_bari.models;

public class RenterProfile {
    private String name;
    private String contact;
    private String address;
    private String password;
    private String occupation;
    private String email;

    public RenterProfile() {

    }

    public RenterProfile(String name, String contact, String address, String password, String occupation, String email) {
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.password = password;
        this.occupation = occupation;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getEmail() {
        return email;
    }
}
