package com.tbm.bamau.todoapp.Models;

public class Settings {

        Integer idSetting;
        String name;
        int img;


    public Settings(Integer idSetting, String name, int img) {
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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
