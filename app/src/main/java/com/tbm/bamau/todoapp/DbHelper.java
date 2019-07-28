package com.tbm.bamau.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.tbm.bamau.todoapp.Models.Task;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.motion.MotionScene.TAG;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "todoapp";
    private static final String TASKTABLE = "Task";
    private static final String TASK_ID = "Id";
    private static final String TASK_STATUS = "Status";
    private static final String NAME_TASK = "NameTask";
    private static final String TIME_TASK = "TimeTask";
    private static final String DAY_TASK = "DayTask";
    private static final String MONTH_TASK = "MonthTask";
    private static final String YEAR_TASK = "YearTask";
    private static final String REPEAT_TASK = "RepeatTask";
    private static final String TIME_REMINDER = "TimeReminder";
    private static final String NOTE_TASK = "NoteTask";
    private static final String LINK_IMAGE = "LinkImage";
    private static final String LINK_AUDIO = "LinkAudio";

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
                + LINK_IMAGE + " TEXT,"
                + LINK_AUDIO + " TEXT" + ")";

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
        values.put(LINK_AUDIO, task.getLinkAudio());
        //Neu de null thi khi value bang null thi loi
        db.insert(TASKTABLE,null,values);
        db.close();
        Log.d(TAG, "Add Task Successfuly");
    }

    /*
    Select a task by ID
     */

    public Task getTaskById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TASKTABLE, new String[] { TASK_ID, TASK_STATUS,
                        NAME_TASK, TIME_TASK, DAY_TASK, MONTH_TASK, YEAR_TASK, REPEAT_TASK,
                TIME_REMINDER, NOTE_TASK, LINK_IMAGE, LINK_AUDIO}, TASK_ID + "=?",
                new String[] { String.valueOf(id) },null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task(cursor.getInt(0),cursor.getInt(1),cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),
                cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11));
        cursor.close();
        db.close();
        return task;
    }

    /*
    Update name of Task
     */

    public int Update(Task task){
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
        values.put(LINK_AUDIO, task.getLinkAudio());

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
                task.setLinkAudio(cursor.getString(11));
                listTask.add(task);
            } while (cursor.moveToNext());
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
        db.delete(TASKTABLE, TASK_ID + " = ?",
                new String[] { String.valueOf(task.getIdTask()) });
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

