package com.rit.basa_bari.models;

public class SubletUpload {
    private String simage;
    private String smonth;
    private String sgender;
    private int subletRent;
    private String sphone;
    private String slocation;
    private String sdescription;
    private String subrentString;

    public SubletUpload() {
    }

    public SubletUpload(String simage, String smonth, String sgender, int subletRent, String sphone, String slocation, String sdescription) {
        this.simage = simage;
        this.smonth = smonth;
        this.sgender = sgender;
        this.subletRent = subletRent;
        this.sphone = sphone;
        this.slocation = slocation;
        this.sdescription = sdescription;
    }

    public String getSubrentString() {
        return Integer.toString(subletRent);
    }

    public void setSubrentString(String subrentString) {
        this.subletRent = Integer.parseInt(subrentString);
    }

    public String getSimage() {
        return simage;
    }

    public String getSmonth() {
        return smonth;
    }

    public String getSgender() {
        return sgender;
    }

    public int getSubletRent() {
        return subletRent;
    }

    public String getSphone() {
        return sphone;
    }

    public String getSlocation() {
        return slocation;
    }

    public String getSdescription() {
        return sdescription;
    }
}
