package com.example.queuebyv2;

import java.security.cert.Certificate;

public class UserRequest {

    String certificates;
    String date;
    String status;
    String firstname;
    String middleinitial;
    String lastname;
    String purpose;
    private String requestId;
    private String email;

    public String getRequestId() {
        return requestId;
    }

    public String getEmail() {
        return email;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddleinitial() {
        return middleinitial;
    }

    public String getLastname() {
        return lastname;
    }


    public String getCertificates() {
        return certificates;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
