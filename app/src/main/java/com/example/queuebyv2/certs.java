package com.example.queuebyv2;

public class certs {
    private  String firstname;
    private  String middleinitial;
    private  String lastname;
    private  String houseno;
    private  String barangay;
    private  String city;
    private String purpose;
    private String forms;
    private String Status;
    private String UserID;
    private String Email;
    private String Certificates;

    private String Date;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getCertificates() {
        return Certificates;
    }

    public void setCertificates(String Certificates) {
        this.Certificates = Certificates;
    }
    public certs() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddleinitial() {
        return middleinitial;
    }

    public void setMiddleinitial(String middleinitial) {
        this.middleinitial = middleinitial;
    }

    public String getLastname() {
        return lastname;
    }

    public String getHouseno() {
        return houseno;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setHouseno(String houseno) {
        this.houseno = houseno;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getForms() {
        return forms;
    }

    public void setForms(String forms) {
        this.forms = forms;
    }
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userid) {UserID = userid;}

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {Email = email;}


    //public String getDate() {
    //return Date;
    //}

    //public void setDate(String date) {Date = date;}

    //public String getOR() {
    //return OR;
    //}

    //public void setOR(String or) {OR = or;}
}

