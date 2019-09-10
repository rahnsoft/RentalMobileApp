package com.rit.basa_bari.models;

public class HostelUpload {
    private String himage;
    private String hmonth;
    private String gender;
    private int hostelRent;
    private String hphone;
    private String hlocation;
    private String hdescription;
    private String hostelR;

    public HostelUpload() {
    }

    public HostelUpload(String himage, String hmonth, String gender, int hostelRent, String hphone, String hlocation, String hdescription) {
        this.himage = himage;
        this.hmonth = hmonth;
        this.gender = gender;
        this.hostelRent = hostelRent;
        this.hphone = hphone;
        this.hlocation = hlocation;
        this.hdescription = hdescription;
    }

    public String getHostelR() {
        return Integer.toString(hostelRent);
    }

    public void setHostelR(String hostelR) throws NumberFormatException{
        this.hostelRent = Integer.parseInt(hostelR);
    }

    public String getHimage() {
        return himage;
    }

    public String getHmonth() {
        return hmonth;
    }

    public String getGender() {
        return gender;
    }

    public int getHostelRent() {
        return hostelRent;
    }

    public String getHphone() {
        return hphone;
    }

    public String getHlocation() {
        return hlocation;
    }

    public String getHdescription() {
        return hdescription;
    }
}
