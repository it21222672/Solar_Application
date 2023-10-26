package com.example.solarapplication;

import java.io.Serializable;

public class Inquiries implements Serializable {
    private int Id;
    private String Username;
    private String Email;
    private String Inquiry;

    public Inquiries(int id, String username, String email, String inquiry) {
        Id = id;
        Username = username;
        Email = email;
        Inquiry = inquiry;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getInquiry() {
        return Inquiry;
    }

    public void setInquiry(String inquiry) {
        Inquiry = inquiry;
    }
}
