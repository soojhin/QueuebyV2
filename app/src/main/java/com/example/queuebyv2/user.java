package com.example.queuebyv2;

import android.view.View;

public class user {

    String userID;

    String email;
     String firstname;
     String middlename;
     String lastname;
     String suffix;
     String age;
     String birthdate;
     String birthplace;
     String contactnumber;
     String street;
     String barangay;
     String city;
     String gender;

    public user(String userID,String email, String firstname, String middlename, String lastname, String suffix, String age, String birthdate, String birthplace, String contactnumber, String street, String barangay, String city, String gender) {
        this.userID = userID;
        this.email = email;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.suffix = suffix;
        this.age = age;
        this.birthdate = birthdate;
        this.birthplace = birthplace;
        this.contactnumber = contactnumber;
        this.street = street;
        this.barangay = barangay;
        this.city = city;
        this.gender = gender;
    }
    public String getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getAge() {
        return age;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public String getStreet() {
        return street;
    }

    public String getBarangay() {
        return barangay;
    }

    public String getCity() {
        return city;
    }

    public String getGender() {
        return gender;
    }
}
