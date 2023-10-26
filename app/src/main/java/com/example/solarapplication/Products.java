package com.example.solarapplication;

import java.io.Serializable;

public class Products  implements Serializable {
    private int Id;
    private String ProductName;
    private String BrandName;
    private String Rating;
    private String Price;
    byte[] Image;
    private String Description;
    private String Category;
    private String WarrantyPeriod;
    private String Request;
     int SupplierId;




    public Products(int id, byte[] image, String productName, String brandName, String price, String description, String category, String warrantyPeriod, String request,int supplierId) {
        Id = id;
        Image = image;
        ProductName = productName;
        BrandName = brandName;
        Price = price;
        Description = description;
        Category = category;
        WarrantyPeriod = warrantyPeriod;
        Request = request;
        SupplierId = supplierId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getWarrantyPeriod() {
        return WarrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        WarrantyPeriod = warrantyPeriod;
    }

    public String getRequest() {
        return Request;
    }

    public void setRequest(String request) {
        Request = request;
    }

    public int getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(int supplierId) {
        SupplierId = supplierId;
    }
}
