package com.tbm.bamau.todoapp.Models;

import android.widget.ImageView;

public class Settings {

        Integer idSetting;
        String name;
        ImageView img;


    public Settings(Integer idSetting, String name, ImageView img) {
        this.idSetting = idSetting;
        this.name = name;
        this.img = img;
    }

    public Settings() {
    }

    public Integer getIdSetting() {
        return idSetting;
    }

    public void setIdSetting(Integer idSetting) {
        this.idSetting = idSetting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }
}
