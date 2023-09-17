package com.example.licenta.classes;

public class ProfileModel {
    String lessons;
    int image;

    public ProfileModel(String lessons, int image) {
        this.lessons = lessons;
        this.image = image;
    }

    public String getLessons() {
        return lessons;
    }

    public int getImage() {
        return image;
    }
}
