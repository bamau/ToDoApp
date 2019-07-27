package com.tbm.bamau.todoapp.Models;

public class AddTask {
    Integer idAddTask;
    String name1, name2;
    int img;

    public AddTask(Integer idAddTask, String name1, String name2, int img) {
        this.idAddTask = idAddTask;
        this.name1 = name1;
        this.name2 = name2;
        this.img = img;
    }

    public Integer getIdAddTask() {
        return idAddTask;
    }

    public void setIdAddTask(Integer idAddTask) {
        this.idAddTask = idAddTask;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
