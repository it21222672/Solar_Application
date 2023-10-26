package com.example.solarapplication;

import java.io.Serializable;

public class Feedback implements Serializable {

    int Id;
    String Feedback;
    int UserId;
    Float Likes;
    String Status;
    private Feedback(){}

    public Feedback(int id, String feedback, int userId, Float likes, String status) {
        Id = id;
        Feedback = feedback;
        UserId = userId;
        Likes = likes;
        Status = status;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public Float getLikes() {
        return Likes;
    }

    public void setLikes(Float likes) {
        Likes = likes;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
