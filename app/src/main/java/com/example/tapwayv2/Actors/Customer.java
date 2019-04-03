package com.example.tapwayv2.Actors;

public class Customer {

    //Personal Information
    private String phoneNumber; //Unique ID
    private String fullName;
    private String destinationType;
    private String destinationNumber; // For customers living in flats, concatenation wing and flat number delimited by '#'
    private String destinationName;
    private String area;
    private String city;
    private String state;
    private String pinCode;
    private String landmark;

    //Parameterised constructor
    public Customer(String phoneNumber, String fullName, String destinationType, String destinationNumber, String destinationName, String area, String city, String state, String pinCode, String landmark) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.destinationType = destinationType;
        this.destinationNumber = destinationNumber;
        this.destinationName = destinationName;
        this.area = area;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.landmark = landmark;
    }

    //Default constructor
    public Customer(){

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

    public String getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }

    public String getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(String destinationNumber) {
        this.destinationNumber = destinationNumber;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
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
