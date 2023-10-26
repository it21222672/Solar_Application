package com.example.solarapplication;

import java.io.Serializable;

public class Article implements Serializable {

    private int Id;
    byte[] Image;
    private  String Title;
    private String Description;
    private String Status;
    int JournalistId;

    public Article(int id, byte[] image, String title, String description, String status, int journalistId) {
        Id = id;
        Image = image;
        Title = title;
        Description = description;
        Status = status;
        JournalistId = journalistId;
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

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
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

    public int getJournalistId() {
        return JournalistId;
    }

    public void setJournalistId(int journalistId) {
        JournalistId = journalistId;
    }
}
