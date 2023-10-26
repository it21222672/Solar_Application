package com.example.solarapplication;

import java.io.Serializable;

public class Journalist implements Serializable {
    private int Id;
    private String Username;
    private String Email;
    private String TpNumber;
    private String Password;
    private  String NicNumber;

    public Journalist() {}

    public Journalist(int id, String username, String email, String tpNumber, String password, String nicNumber) {
        Id = id;
        Username = username;
        Email = email;
        TpNumber = tpNumber;
        Password = password;
        NicNumber = nicNumber;
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

    public String getTpNumber() {
        return TpNumber;
    }

    public void setTpNumber(String tpNumber) {
        TpNumber = tpNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNicNumber() {
        return NicNumber;
    }

    public void setNicNumber(String nicNumber) {
        NicNumber = nicNumber;
    }
}
