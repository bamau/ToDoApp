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
    private static final String NAME_TASK = "NameTask";
    private static final String DATE_TIME_TASK = "DateTime";

    private Context context;

    public DbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TASKTABLE = "CREATE TABLE " + TASKTABLE + "("
                + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME_TASK + " TEXT,"
                + DATE_TIME_TASK + " TEXT" + ")";

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
        values.put(NAME_TASK, task.getNameTask());
        values.put(DATE_TIME_TASK, task.getDatetimeTask());

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
        Cursor cursor = db.query(TASKTABLE, new String[] { TASK_ID,
                        NAME_TASK, DATE_TIME_TASK}, TASK_ID + "=?",
                new String[] { String.valueOf(id) },null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
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

        values.put(NAME_TASK,task.getNameTask());

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
                task.setNameTask(cursor.getString(1));
                task.setDatetimeTask(cursor.getString(2));
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

