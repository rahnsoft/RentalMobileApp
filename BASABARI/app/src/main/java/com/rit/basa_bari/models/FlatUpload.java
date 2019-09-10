package com.rit.basa_bari.models;

public class FlatUpload {
    private String fimage;
    private String month;
    private String gender;
    private int flatRent;
    private String fphone;
    private String flocation;
    private String fdescription;
    private String frentString;


    public FlatUpload() {
    }

    public FlatUpload(String fimage, int flatRent, String fphone, String flocation, String fdescription) {
        this.fimage = fimage;
        this.flatRent = flatRent;
        this.fphone = fphone;
        this.flocation = flocation;
        this.fdescription = fdescription;
    }

    public FlatUpload(String fimage, String month, String gender, int flatRent, String fphone, String flocation, String fdescription) {
        this.fimage = fimage;
        this.month = month;
        this.gender = gender;
        this.flatRent = flatRent;
        this.fphone = fphone;
        this.flocation = flocation;
        this.fdescription = fdescription;
    }

    public String getFrentString() {
        return Integer.toString(flatRent);
    }

    public void setFrentString(String frentString) throws NumberFormatException {
        this.flatRent = Integer.parseInt(frentString);
    }

    public String getFimage() {
        return fimage;
    }

    public String getMonth() {
        return month;
    }

    public String getGender() {
        return gender;
    }

    public int getFlatRent() {
        return flatRent;
    }

    public String getFphone() {
        return fphone;
    }

    public String getFlocation() {
        return flocation;
    }

    public String getFdescription() {
        return fdescription;
    }
}
