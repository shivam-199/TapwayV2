package com.example.tapwayv2.Actors;

public class DeliveryBoy {

    //Personal Information : DeliveryBoi
    private String phoneNumber;
    private String fullName;

    //Parameterised constructor
    public DeliveryBoy(String phoneNumber, String fullName) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
    }

    //Default constructor
    public DeliveryBoy (){

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
}
