package com.example.queuebyv2;

public class CurrentUser {
    private String age;
    private String barangay;
    private String city;
    private String CN;
    private String email;
    private String FN;
    private String LN;
    private String MN;
    private String street;
    private String suffix;
    private String userID;
    private String birthdate;
    private String birthplace;

    private String gender;
    public static CurrentUser instance;

    public static CurrentUser getInstance(){
        if(instance == null){
            instance = new CurrentUser();
        }
        return instance;
    }

    private CurrentUser(){}

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate){this.birthdate = birthdate;}



    public void setBirthplace(String birthplace){this.birthplace = birthplace;}


    public void setAge(String age){this.age = age;}


    public void setBarangay(String barangay){this.barangay = barangay;}


    public void setCity(String city){this.city = city;}


    public void setCN(String CN) {this.CN = CN;}


    public void setEmail(String email) {
        this.email = email;
    }


    public void setFN(String FN) {
        this.FN = FN;
    }


    public void setLN(String LN) {
        this.LN = LN;
    }


    public void setMN(String MN) {
        this.MN = MN;
    }


    public void setStreet(String street) {
        this.street = street;
    }


    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAge() {
        return age;
    }

    public String getBarangay() {
        return barangay;
    }

    public String getCity() {
        return city;
    }

    public String getCN() {
        return CN;
    }

    public String getEmail() {
        return email;
    }

    public String getFN() {
        return FN;
    }

    public String getLN() {
        return LN;
    }

    public String getMN() {
        return MN;
    }

    public String getStreet() {
        return street;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
