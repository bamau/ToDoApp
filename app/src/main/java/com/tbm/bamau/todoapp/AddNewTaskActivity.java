package com.tbm.bamau.todoapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tbm.bamau.todoapp.Fragment.ViewDay_Fragment;
import com.tbm.bamau.todoapp.Models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static android.support.constraint.motion.MotionScene.TAG;

public class AddNewTaskActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    DbHelper database;
    TextView due, setDate, setTime, setRepeat, reminders, addReminders, note, addNote, image, takePhoto, audio, addAudio ;
    EditText edtName, editNote;
    Button btnOk, btnCancel;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH);
    final String[] Repeat = { "No repeat", "Every day", "Every week", "Every month", "Every year"};

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        getSupportActionBar().setTitle("New Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Initialization();

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                    datePickerDialog = new DatePickerDialog(setDate.getContext(), R.style.Theme_AppCompat_DayNight_Dialog
                            , new DatePickerDialog.OnDateSetListener() {
                        String task_date;
                        String[] cutTaskDate;
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Calendar c = Calendar.getInstance();
                            c.set(Calendar.YEAR, year);
                            c.set(Calendar.MONTH, month);
                            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            c.setTimeZone(TimeZone.getDefault());
                            task_date = dateFormat.format(c.getTime());
                            cutTaskDate= task_date.split(" ");
                            setDate.setText(task_date);
                        }
                    },year, month, day);
                    datePickerDialog.show();
            }
        });
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(setTime.getContext(), R.style.Theme_AppCompat_Dialog
                        , new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat hFomart = new SimpleDateFormat("K:mm a", Locale.ENGLISH);
                        String task_time = hFomart.format(c.getTime());
                        setTime.setText(task_time);

                    }
                },hours,minute,false);
                timePickerDialog.show();
            }
        });
        setRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddNewTaskActivity.this);
                builder.setTitle(R.string.pick_a_repeat);
                builder.setItems(Repeat, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:{
                                setRepeat.setText(Repeat[0]);
                                break;
                            }
                            case 1:{
                                setRepeat.setText(Repeat[1]);
                                break;
                            }
                            case 2:{
                                setRepeat.setText(Repeat[2]);
                                break;
                            }
                            case 3:{
                                setRepeat.setText(Repeat[3]);
                                break;
                            }
                            case 4:{
                                setRepeat.setText(Repeat[4]);
                                break;
                            }

                        }
                    }
                });
                builder.create().show();
            }
        });


        addReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddNote();
            }
        });

        database = new DbHelper(this);

    }

    public Date fomartDate (String date) throws ParseException {
        Date fDate = dateFormat.parse(date);
        return fDate;
    }

    public int checkDate(String day1, String month1, String year1, String day2, String month2, String year2) throws ParseException {

        String date1 = day1+" "+month1+" "+year1;
        Date Date1 = fomartDate(date1);
        String date2 = day2+" "+month2+" "+year2;
        Date Date2 = fomartDate(date2);

        int check = Date1.compareTo(Date2);
        return check;
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
        audio = findViewById(R.id.audio);
        addAudio = findViewById(R.id.addAudio);
        edtName = findViewById(R.id.editText);

    }

    private void dialogAddNote() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_note);
        editNote = dialog.findViewById(R.id.editNote);
        btnOk = dialog.findViewById(R.id.btn_ok);
        btnCancel = dialog.findViewById(R.id.btn_cancel);
        if(!addNote.getText().toString().trim().equals("Add a note"))
            editNote.setText(addNote.getText().toString().trim());
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editNote.getText().toString().trim().equals(""))
                    Toast.makeText(AddNewTaskActivity.this, "You can't add empty note", Toast.LENGTH_SHORT).show();
                if(!addNote.getText().toString().trim().equals("Add a note"))
                    addNote.setText(null);
                addNote.setText(editNote.getText().toString().trim());
                dialog.dismiss();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }


    public Task createTask() {

        Integer status = 0;
        String name = edtName.getText().toString().trim();
        String note;
        if (addNote.getText().toString().trim().equals("Add a note"))
            note="";
        else note = addNote.getText().toString().trim();
        String date = setDate.getText().toString().trim();
        String[] cutDate = date.split(" ");
        String day = cutDate[0];
        String month = cutDate[1];
        String year = cutDate[2];
        String time = setTime.getText().toString().trim();
        String repeat = setRepeat.getText().toString().trim();
        String timeReminder = addReminders.getText().toString().trim();
        String linkImage = takePhoto.getText().toString().trim();
        String linkAudio = addAudio.getText().toString().trim();
        Task task = new Task(status,name, time, day, month, year, repeat,timeReminder, note,linkImage,linkAudio);
        return task;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            Task task=createTask();
            if(task.getNameTask().equals("")){
                Toast.makeText(AddNewTaskActivity.this,"Please enter characters!",Toast.LENGTH_SHORT).show();
            }else{
                database.addTask(task);
                Intent intent = new Intent(AddNewTaskActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Add Success!", Toast.LENGTH_SHORT).show();
            }
        }
        if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
