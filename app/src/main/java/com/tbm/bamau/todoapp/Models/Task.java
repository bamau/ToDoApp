package com.tbm.bamau.todoapp.Models;

public class Task {
    private int IdTask;
    private int StatusTask;
    private String NameTask;
    private String TimeTask;
    private String DayTask;
    private String MonthTask;
    private String YearTask;
    private String Repeat;
    private String TimeReminder;
    private String Note;
    private String LinkImage;

    public Task() {
    }

    public Task(int statusTask, String nameTask, String timeTask, String dayTask, String monthTask, String yearTask, String repeat, String timeReminder, String note, String linkImage) {
        StatusTask = statusTask;
        NameTask = nameTask;
        TimeTask = timeTask;
        DayTask = dayTask;
        MonthTask = monthTask;
        YearTask = yearTask;
        Repeat = repeat;
        TimeReminder = timeReminder;
        Note = note;
        LinkImage = linkImage;
    }

    public Task(int idTask, int statusTask, String nameTask, String timeTask, String dayTask, String monthTask, String yearTask, String repeat, String timeReminder, String note, String linkImage) {
        IdTask = idTask;
        StatusTask = statusTask;
        NameTask = nameTask;
        TimeTask = timeTask;
        DayTask = dayTask;
        MonthTask = monthTask;
        YearTask = yearTask;
        Repeat = repeat;
        TimeReminder = timeReminder;
        Note = note;
        LinkImage = linkImage;
    }

    public int getIdTask() {
        return IdTask;
    }

    public void setIdTask(int idTask) {
        IdTask = idTask;
    }

    public int getStatusTask() {
        return StatusTask;
    }

    public void setStatusTask(int statusTask) {
        StatusTask = statusTask;
    }

    public String getNameTask() {
        return NameTask;
    }

    public void setNameTask(String nameTask) {
        NameTask = nameTask;
    }

    public String getTimeTask() {
        return TimeTask;
    }

    public void setTimeTask(String timeTask) {
        TimeTask = timeTask;
    }

    public String getDayTask() {
        return DayTask;
    }

    public void setDayTask(String dayTask) {
        DayTask = dayTask;
    }

    public String getMonthTask() {
        return MonthTask;
    }

    public void setMonthTask(String monthTask) {
        MonthTask = monthTask;
    }

    public String getYearTask() {
        return YearTask;
    }

    public void setYearTask(String yearTask) {
        YearTask = yearTask;
    }

    public String getRepeat() {
        return Repeat;
    }

    public void setRepeat(String repeat) {
        Repeat = repeat;
    }

    public String getTimeReminder() {
        return TimeReminder;
    }

    public void setTimeReminder(String timeReminder) {
        TimeReminder = timeReminder;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getLinkImage() {
        return LinkImage;
    }

    public void setLinkImage(String linkImage) {
        LinkImage = linkImage;
    }
}
