package com.example.tapwayv2.Actors;

public class Vendor {

    //Personal Information
    private String phoneNumber; //Unique ID
    private String fullName;
    private String plotNumber;
    private String area;
    private String city;
    private String state;
    private String pinCode;
    private String landmark;

    //Parameterised constructor
    public Vendor(String phoneNumber, String fullName, String plotNumber, String area, String city, String state, String pinCode, String landmark) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.plotNumber = plotNumber;
        this.area = area;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.landmark = landmark;
    }

    //Default constructor
    public Vendor(){

    }

    // Getter and Setter methods
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPlotNumber() {
        return plotNumber;
    }

    public void setPlotNumber(String plotNumber) {
        this.plotNumber = plotNumber;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }
}
