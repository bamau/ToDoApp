package com.tbm.bamau.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tbm.bamau.todoapp.Models.Task;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.motion.MotionScene.TAG;
import static com.tbm.bamau.todoapp.DBStructure.DAY_TASK;
import static com.tbm.bamau.todoapp.DBStructure.DB_NAME;
import static com.tbm.bamau.todoapp.DBStructure.DB_VERSION;
import static com.tbm.bamau.todoapp.DBStructure.LINK_IMAGE;
import static com.tbm.bamau.todoapp.DBStructure.MONTH_TASK;
import static com.tbm.bamau.todoapp.DBStructure.NAME_TASK;
import static com.tbm.bamau.todoapp.DBStructure.NOTE_TASK;
import static com.tbm.bamau.todoapp.DBStructure.REPEAT_TASK;
import static com.tbm.bamau.todoapp.DBStructure.TASKTABLE;
import static com.tbm.bamau.todoapp.DBStructure.TASK_ID;
import static com.tbm.bamau.todoapp.DBStructure.TASK_STATUS;
import static com.tbm.bamau.todoapp.DBStructure.TIME_REMINDER;
import static com.tbm.bamau.todoapp.DBStructure.TIME_TASK;
import static com.tbm.bamau.todoapp.DBStructure.YEAR_TASK;

public class DbHelper extends SQLiteOpenHelper {

    private Context context;
    public DbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TASKTABLE = "CREATE TABLE " + TASKTABLE + "("
                + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TASK_STATUS + " INTERGER,"
                + NAME_TASK + " TEXT,"
                + TIME_TASK + " TEXT,"
                + DAY_TASK + " TEXT,"
                + MONTH_TASK + " TEXT,"
                + YEAR_TASK + " TEXT,"
                + REPEAT_TASK + " TEXT,"
                + TIME_REMINDER + " TEXT,"
                + NOTE_TASK + " TEXT,"
                + LINK_IMAGE + " TEXT)";

        db.execSQL(CREATE_TASKTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TASKTABLE);
        onCreate(db);
    }

    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }
    //Add new a task
    public void addTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_STATUS, task.getStatusTask());
        values.put(NAME_TASK, task.getNameTask());
        values.put(TIME_TASK, task.getTimeTask());
        values.put(DAY_TASK, task.getDayTask());
        values.put(MONTH_TASK, task.getMonthTask());
        values.put(YEAR_TASK, task.getYearTask());
        values.put(REPEAT_TASK, task.getRepeat());
        values.put(TIME_REMINDER, task.getTimeReminder());
        values.put(NOTE_TASK, task.getNote());
        values.put(LINK_IMAGE, task.getLinkImage());
        //Neu de null thi khi value bang null thi loi
        db.insert(TASKTABLE,null,values);
        db.close();
        Log.d(TAG, "Add Task Successfuly");
    }

    public int addATask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_STATUS, task.getStatusTask());
        values.put(NAME_TASK, task.getNameTask());
        values.put(TIME_TASK, task.getTimeTask());
        values.put(DAY_TASK, task.getDayTask());
        values.put(MONTH_TASK, task.getMonthTask());
        values.put(YEAR_TASK, task.getYearTask());
        values.put(REPEAT_TASK, task.getRepeat());
        values.put(TIME_REMINDER, task.getTimeReminder());
        values.put(NOTE_TASK, task.getNote());
        values.put(LINK_IMAGE, task.getLinkImage());
        //Neu de null thi khi value bang null thi loi
        long ID = db.insert(TASKTABLE,null,values);
        db.close();
        return (int) ID;
    }

    /*
    Select a task by ID
     */

    public Task getTaskById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TASKTABLE, new String[] { TASK_ID, TASK_STATUS,
                        NAME_TASK, TIME_TASK, DAY_TASK, MONTH_TASK, YEAR_TASK, REPEAT_TASK,
                TIME_REMINDER, NOTE_TASK, LINK_IMAGE}, TASK_ID + "=?",
                new String[] { String.valueOf(id) },null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task(cursor.getInt(0),cursor.getInt(1),cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),
                cursor.getString(8), cursor.getString(9), cursor.getString(10));
        cursor.close();
        db.close();
        return task;
    }

    public Task getTaskByNameTask(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TASKTABLE, new String[] { TASK_ID, TASK_STATUS,
                        NAME_TASK, TIME_TASK, DAY_TASK, MONTH_TASK, YEAR_TASK, REPEAT_TASK,
                        TIME_REMINDER, NOTE_TASK, LINK_IMAGE}, NAME_TASK + "=?",
                new String[] { String.valueOf(name) },null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task(cursor.getInt(0),cursor.getInt(1),cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),
                cursor.getString(8), cursor.getString(9), cursor.getString(10));
        cursor.close();
        db.close();
        return task;
    }

    public Task getTaskByDay(String day){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TASKTABLE, new String[] { TASK_ID, TASK_STATUS,
                        NAME_TASK, TIME_TASK, DAY_TASK, MONTH_TASK, YEAR_TASK, REPEAT_TASK,
                        TIME_REMINDER, NOTE_TASK, LINK_IMAGE}, DAY_TASK + "=?",
                new String[] { String.valueOf(day) },null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task(cursor.getInt(0),cursor.getInt(1),cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),
                cursor.getString(8), cursor.getString(9), cursor.getString(10));
        cursor.close();
        db.close();
        return task;
    }

    /*
    Update name of Task
     */

    public void Update(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TASK_STATUS, task.getStatusTask());
        values.put(NAME_TASK, task.getNameTask());
        values.put(TIME_TASK, task.getTimeTask());
        values.put(DAY_TASK, task.getDayTask());
        values.put(MONTH_TASK, task.getMonthTask());
        values.put(YEAR_TASK, task.getYearTask());
        values.put(REPEAT_TASK, task.getRepeat());
        values.put(TIME_REMINDER, task.getTimeReminder());
        values.put(NOTE_TASK, task.getNote());
        values.put(LINK_IMAGE, task.getLinkImage());
        db.update(TASKTABLE,values,TASK_ID +"=?",new String[] { String.valueOf(task.getIdTask())});
        Log.d(TAG, "'"+task.getIdTask()+"'");
        Log.d(TAG, "'"+TASK_ID+"'");

        db.close();
    }

    public int UpdateStatusToLater(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TASK_STATUS, 2);
        values.put(NAME_TASK, task.getNameTask());
        values.put(TIME_TASK, task.getTimeTask());
        values.put(DAY_TASK, task.getDayTask());
        values.put(MONTH_TASK, task.getMonthTask());
        values.put(YEAR_TASK, task.getYearTask());
        values.put(REPEAT_TASK, task.getRepeat());
        values.put(TIME_REMINDER, task.getTimeReminder());
        values.put(NOTE_TASK, task.getNote());
        values.put(LINK_IMAGE, task.getLinkImage());

        return db.update(TASKTABLE,values,TASK_ID +"=?",new String[] { String.valueOf(task.getIdTask())});
    }

    public int UpdateStatus(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TASK_STATUS, 1);
        values.put(NAME_TASK, task.getNameTask());
        values.put(TIME_TASK, task.getTimeTask());
        values.put(DAY_TASK, task.getDayTask());
        values.put(MONTH_TASK, task.getMonthTask());
        values.put(YEAR_TASK, task.getYearTask());
        values.put(REPEAT_TASK, task.getRepeat());
        values.put(TIME_REMINDER, task.getTimeReminder());
        values.put(NOTE_TASK, task.getNote());
        values.put(LINK_IMAGE, task.getLinkImage());
        return db.update(TASKTABLE,values,TASK_ID +"=?",new String[] { String.valueOf(task.getIdTask())});
    }
    /*
     Getting All Task
      */
    public List<Task> getAllTask() {
        List<Task> listTask = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TASKTABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setIdTask(cursor.getInt(0));
                task.setStatusTask(cursor.getInt(1));
                task.setNameTask(cursor.getString(2));
                task.setTimeTask(cursor.getString(3));
                task.setDayTask(cursor.getString(4));
                task.setMonthTask(cursor.getString(5));
                task.setYearTask(cursor.getString(6));
                task.setRepeat(cursor.getString(7));
                task.setTimeReminder(cursor.getString(8));
                task.setNote(cursor.getString(9));
                task.setLinkImage(cursor.getString(10));
                listTask.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listTask;
    }

    public List<Task> getListTaskWithDay(String day, String month, String year, int status) {
        List<Task> listTask = new ArrayList<Task>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ( SELECT * FROM "+TASKTABLE+" ORDER BY " + TIME_TASK + ")  WHERE ("+ DAY_TASK +" LIKE '"+day+"%' AND "+MONTH_TASK+" LIKE'"+month+"%' AND "+YEAR_TASK+" LIKE'"+year+"%' AND "+TASK_STATUS+" LIKE'"+status+"%')",null);
        while (cursor.moveToNext()){
                Task task = new Task();
                task.setIdTask(cursor.getInt(0));
                task.setStatusTask(cursor.getInt(1));
                task.setNameTask(cursor.getString(2));
                task.setTimeTask(cursor.getString(3));
                task.setDayTask(cursor.getString(4));
                task.setMonthTask(cursor.getString(5));
                task.setYearTask(cursor.getString(6));
                task.setRepeat(cursor.getString(7));
                task.setTimeReminder(cursor.getString(8));
                task.setNote(cursor.getString(9));
                task.setLinkImage(cursor.getString(10));
                listTask.add(task);
            }
        cursor.close();
        db.close();
        return listTask;
    }

    public Cursor readTaskPerMonth(String month, String year, int status, SQLiteDatabase database){
        Cursor cursor = database.rawQuery("SELECT * FROM ( SELECT * FROM "+TASKTABLE+") WHERE ("+MONTH_TASK+" LIKE'"+month+"%' AND "+YEAR_TASK+" LIKE'"+year+"%' AND "+TASK_STATUS+" LIKE'"+status+"%')",null);
        return cursor;
    }

    public List<Task> getListTaskWithStatusOrderByYear(int status) {
        List<Task> listTask = new ArrayList<Task>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ( SELECT * FROM "+TASKTABLE+" ORDER BY "+ YEAR_TASK +") WHERE ("+TASK_STATUS+" LIKE'"+status+"%')",null);
        while (cursor.moveToNext()){
            Task task = new Task();
            task.setIdTask(cursor.getInt(0));
            task.setStatusTask(cursor.getInt(1));
            task.setNameTask(cursor.getString(2));
            task.setTimeTask(cursor.getString(3));
            task.setDayTask(cursor.getString(4));
            task.setMonthTask(cursor.getString(5));
            task.setYearTask(cursor.getString(6));
            task.setRepeat(cursor.getString(7));
            task.setTimeReminder(cursor.getString(8));
            task.setNote(cursor.getString(9));
            task.setLinkImage(cursor.getString(10));
            listTask.add(task);
        }
        cursor.close();
        db.close();
        return listTask;
    }

    public List<Task> getListTaskWithStatusOrderByMonth(int status) {
        List<Task> listTask = new ArrayList<Task>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ( SELECT * FROM "+TASKTABLE+" ORDER BY "+ MONTH_TASK +") WHERE ("+TASK_STATUS+" LIKE'"+status+"%')",null);
        while (cursor.moveToNext()){
            Task task = new Task();
            task.setIdTask(cursor.getInt(0));
            task.setStatusTask(cursor.getInt(1));
            task.setNameTask(cursor.getString(2));
            task.setTimeTask(cursor.getString(3));
            task.setDayTask(cursor.getString(4));
            task.setMonthTask(cursor.getString(5));
            task.setYearTask(cursor.getString(6));
            task.setRepeat(cursor.getString(7));
            task.setTimeReminder(cursor.getString(8));
            task.setNote(cursor.getString(9));
            task.setLinkImage(cursor.getString(10));
            listTask.add(task);
        }
        cursor.close();
        db.close();
        return listTask;
    }

    public List<Task> getListTaskWithStatusOrderByDay(int status) {
        List<Task> listTask = new ArrayList<Task>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ( SELECT * FROM "+TASKTABLE+" ORDER BY "+ DAY_TASK +") WHERE ("+TASK_STATUS+" LIKE'"+status+"%')",null);
        while (cursor.moveToNext()){
            Task task = new Task();
            task.setIdTask(cursor.getInt(0));
            task.setStatusTask(cursor.getInt(1));
            task.setNameTask(cursor.getString(2));
            task.setTimeTask(cursor.getString(3));
            task.setDayTask(cursor.getString(4));
            task.setMonthTask(cursor.getString(5));
            task.setYearTask(cursor.getString(6));
            task.setRepeat(cursor.getString(7));
            task.setTimeReminder(cursor.getString(8));
            task.setNote(cursor.getString(9));
            task.setLinkImage(cursor.getString(10));
            listTask.add(task);
        }
        cursor.close();
        db.close();
        return listTask;
    }

    public List<Task> getListTaskWithStatusOrderByTime(int status) {
        List<Task> listTask = new ArrayList<Task>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ( SELECT * FROM "+TASKTABLE+" ORDER BY "+ TIME_TASK +") WHERE ("+TASK_STATUS+" LIKE'"+status+"%')",null);
        while (cursor.moveToNext()){
            Task task = new Task();
            task.setIdTask(cursor.getInt(0));
            task.setStatusTask(cursor.getInt(1));
            task.setNameTask(cursor.getString(2));
            task.setTimeTask(cursor.getString(3));
            task.setDayTask(cursor.getString(4));
            task.setMonthTask(cursor.getString(5));
            task.setYearTask(cursor.getString(6));
            task.setRepeat(cursor.getString(7));
            task.setTimeReminder(cursor.getString(8));
            task.setNote(cursor.getString(9));
            task.setLinkImage(cursor.getString(10));
            listTask.add(task);
        }
        cursor.close();
        db.close();
        return listTask;
    }
    /*
    Delete a Task by ID
     */
    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TASKTABLE, TASK_ID + " = ?", new String[] { String.valueOf(task.getIdTask()) });
        db.close();
    }
    /*
    Get Count Task in Table Task
     */
    public int getTaskCount() {
        String countQuery = "SELECT  * FROM " + TASKTABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}

