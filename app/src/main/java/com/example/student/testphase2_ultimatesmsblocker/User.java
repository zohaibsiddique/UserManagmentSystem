package com.example.student.testphase2_ultimatesmsblocker;

public class User {
    private String id, title, password, email, contact, type, regDate, modfDate;

    User(String id, String title, String password, String email, String contact, String type, String regDate, String modfDate) {
        this.id = id;
        this.title = title;
        this.password = password;
        this.email = email;
        this.contact = contact;
        this.type = type;
        this.regDate = regDate;
        this.modfDate = modfDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getModfDate() {
        return modfDate;
    }

    public void setModfDate(String modfDate) {
        this.modfDate = modfDate;
    }
}
