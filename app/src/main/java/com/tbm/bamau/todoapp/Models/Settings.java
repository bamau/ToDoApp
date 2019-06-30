package com.tbm.bamau.todoapp.Models;

public class Settings {
        String name;
        int imageView;

    public Settings(String name, int imageView) {
        this.name = name;
        this.imageView = imageView;
    }

    public Settings() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }
}
