package com.example.solarapplication;

import java.io.Serializable;

public class Orders implements Serializable {

    private int Id;
    byte[] Image;
    String ProductName;
    String Quantity;
    int CustomerId;
    String UserName;
    String Email;
    String ContactNumber;
    String Address;
    String PostalCode;
    String Date;

    String Description;
    String Status;
    int SupplierId;

    public Orders(int id, byte[] image, String productName, String quantity, int customerId, String userName, String email, String contactNumber, String address, String postalCode, String date, String description, String status, int supplierId) {
        Id = id;
        Image = image;
        ProductName = productName;
        Quantity = quantity;
        CustomerId = customerId;
        UserName = userName;
        Email = email;
        ContactNumber = contactNumber;
        Address = address;
        PostalCode = postalCode;
        Date = date;
        Description = description;
        Status = status;
        SupplierId = supplierId;
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

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(int supplierId) {
        SupplierId = supplierId;
    }
}
