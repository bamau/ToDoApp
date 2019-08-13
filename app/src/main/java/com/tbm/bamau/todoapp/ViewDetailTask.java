package com.tbm.bamau.todoapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tbm.bamau.todoapp.Models.Task;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static android.support.constraint.motion.MotionScene.TAG;

public class ViewDetailTask extends AppCompatActivity {

    DbHelper database;
    TextView due, setDate, setTime, setRepeat, reminders, addReminders, note, addNote, image, takePhoto;
    EditText edtName;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
    final String[] Repeat = { "No Repeat", "Every day", "Every week", "Every month", "Every year"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdetailtask);

        getSupportActionBar().setTitle(R.string.detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Initialization();

        Bundle bundle = getIntent().getExtras();
        final int idTask = bundle.getInt("ID",0);

        database = new DbHelper(this);

        Task task = database.getTaskById(idTask);
        getDetailTask(task);

    }

    private void Initialization(){

        due = findViewById(R.id.due);
        setDate = findViewById(R.id.setDate);
        setTime = findViewById(R.id.setTime);
        setRepeat = findViewById(R.id.setRepeat);
        reminders = findViewById(R.id.reminders);
        addReminders = findViewById(R.id.addRemind);
        note = findViewById(R.id.note);
        addNote = findViewById(R.id.addNote);
        image = findViewById(R.id.image);
        takePhoto = findViewById(R.id.takePhoto);
        edtName = findViewById(R.id.editText);

    }

    private void getDetailTask(Task task){

        int i = task.getIdTask();
        edtName.setText(task.getNameTask());
        edtName.setSelection(edtName.getText().length());
        edtName.setEnabled(false); edtName.setInputType(InputType.TYPE_NULL);
        String day, month, year;
        day = task.getDayTask();
        month = task.getMonthTask();
        year = task.getYearTask();
        setDate.setText(day+" "+month+" "+year);
        setTime.setText(task.getTimeTask());
        setRepeat.setText(task.getRepeat());
        addReminders.setText(task.getTimeReminder());
        addNote.setText(task.getNote());
        takePhoto.setText(task.getLinkImage());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.view_setting) {
            Intent intent = new Intent(ViewDetailTask.this, SettingActivity.class);
            startActivity(intent);
        }if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
