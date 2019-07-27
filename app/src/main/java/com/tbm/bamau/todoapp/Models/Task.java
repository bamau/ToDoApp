package com.tbm.bamau.todoapp.Models;

public class Task {
    private int IdTask;
    private String NameTask;
    private String DatetimeTask;

    public Task() {
    }

    public Task(String nameTask, String datetimeTask) {
        NameTask = nameTask;
        DatetimeTask = datetimeTask;
    }

    public Task(Integer idTask, String nameTask, String datetimeTask) {
        IdTask = idTask;
        NameTask = nameTask;
        DatetimeTask = datetimeTask;
    }

    public int getIdTask() {
        return IdTask;
    }

    public void setIdTask(int idTask) {
        IdTask = idTask;
    }

    public String getNameTask() {
        return NameTask;
    }

    public void setNameTask(String nameTask) {
        NameTask = nameTask;
    }

    public String getDatetimeTask() {
        return DatetimeTask;
    }

    public void setDatetimeTask(String datetimeTask) {
        DatetimeTask = datetimeTask;
    }
}
