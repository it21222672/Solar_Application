package com.example.solarapplication;

import java.io.Serializable;

public class Suppliers implements Serializable {

    private int Id;
    byte[] Image;
    private String Company_Name ;
    private String Registration_No ;
    private String Address ;
    private String Name;
    private String Position ;
    private String Email ;
    private String Web ;
    private String Contact_no ;
    private String Description ;
    private String Request;
    private String Password;
    public Suppliers() {}

    public Suppliers(int id, byte[] image, String company_Name, String registration_No, String address, String name, String position, String email, String web, String contact_no, String description, String request, String password) {
        Id = id;
        Image = image;
        Company_Name = company_Name;
        Registration_No = registration_No;
        Address = address;
        Name = name;
        Position = position;
        Email = email;
        Web = web;
        Contact_no = contact_no;
        Description = description;
        Request = request;
        Password = password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public String getCompany_Name() {
        return Company_Name;
    }

    public void setCompany_Name(String company_Name) {
        Company_Name = company_Name;
    }

    public String getRegistration_No() {
        return Registration_No;
    }

    public void setRegistration_No(String registration_No) {
        Registration_No = registration_No;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getWeb() {
        return Web;
    }

    public void setWeb(String web) {
        Web = web;
    }

    public String getContact_no() {
        return Contact_no;
    }

    public void setContact_no(String contact_no) {
        Contact_no = contact_no;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getRequest() {
        return Request;
    }

    public void setRequest(String request) {
        Request = request;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
