package com.example.loginpage;

public class RegestrationRequest {
    private String email;
    private String name;
    private String address;
    private String password ;
    private String contactNo;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contactNo;
    }

    public void setContact(String contact) {
        this.contactNo = contact;
    }
}
