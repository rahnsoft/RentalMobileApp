package com.rit.basa_bari.models;

public class MessUpload {

    private String image;
    private String month;
    private String gender;
    private int messRent;
    private String phone;
    private String location;
    private String description;
    private  String messrentString;

    public MessUpload() {

    }
    public MessUpload(String image, int messRent, String phone, String location, String description) {
        this.image = image;
        this.messRent = messRent;
        this.phone = phone;
        this.location = location;
        this.description = description;

    }

    public MessUpload(String image, String month, String gender, int messRent, String phone, String location, String description) {
        this.image = image;
        this.month = month;
        this.gender = gender;
        this.messRent = messRent;
        this.phone = phone;
        this.location = location;
        this.description = description;
    }

    public String getMessrentString() {
        return Integer.toString(messRent);
    }

    public void setMessrentString(String messrentString) {
        this.messRent = Integer.parseInt(messrentString);
    }

    public String getImage() {
        return image;
    }

    public String getMonth() {
        return month;
    }

    public String getGender() {
        return gender;
    }

    public int getMessRent() {
        return messRent;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }
}
