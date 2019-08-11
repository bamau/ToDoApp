package com.tbm.bamau.todoapp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.tbm.bamau.todoapp.Notification.AlarmReceiver;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static android.support.constraint.motion.MotionScene.TAG;
import static com.tbm.bamau.todoapp.MainActivity.hideSoftKeyboard;

public class AddNewTaskActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    DbHelper database;
    TextView due, setDate, setTime, setRepeat, reminders, addReminders, note, addNote, image, takePhoto, audio, addAudio ;
    EditText edtName, editNote;
    Button btnOk, btnCancel;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH);
    SimpleDateFormat tFormat = new SimpleDateFormat("KK:mm:a", Locale.ENGLISH);
    final String[] Repeat = { "No Repeat", "Every day", "Every week", "Every month", "Every year"};
    final String[] Reminder = { "Due", "Before 5 minutes", "Before 10 minutes", "Before 15 minutes", "Before 20 minutes"};
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    public static final String EXTRA_TASK_ID = "Task_ID";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        getSupportActionBar().setTitle(R.string.new_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setupUI(findViewById(R.id.viewAdd));
        Initialization();

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                    datePickerDialog = new DatePickerDialog(setDate.getContext(), R.style.Theme_AppCompat_DayNight_Dialog
                            , new DatePickerDialog.OnDateSetListener() {
                        String task_date;
                        String[] cutTaskDate;
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            calendar.setTimeZone(TimeZone.getDefault());
                            task_date = dateFormat.format(calendar.getTime());
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
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(setTime.getContext(), R.style.Theme_AppCompat_DayNight_Dialog
                        , new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.setTimeZone(TimeZone.getDefault());
                        String task_time = tFormat.format(calendar.getTimeInMillis());
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

                AlertDialog.Builder builder = new AlertDialog.Builder(AddNewTaskActivity.this);
                builder.setTitle("Pick a reminder");
                builder.setItems(Reminder, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:{
                                addReminders.setText(Reminder[0]);
                                break;
                            }
                            case 1:{
                                addReminders.setText(Reminder[1]);
                                break;
                            }
                            case 2:{
                                addReminders.setText(Reminder[2]);
                                break;
                            }
                            case 3:{
                                addReminders.setText(Reminder[3]);
                                break;
                            }
                            case 4:{
                                addReminders.setText(Reminder[4]);
                                break;
                            }

                        }
                    }
                });
                builder.create().show();

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

    public Date formatDate (String date) throws ParseException {
        Date fDate = dateFormat.parse(date);
        return fDate;
    }

    public int checkDate(String day1, String month1, String year1, String day2, String month2, String year2) throws ParseException {

        String date1 = day1+" "+month1+" "+year1;
        Date Date1 = formatDate(date1);
        String date2 = day2+" "+month2+" "+year2;
        Date Date2 = formatDate(date2);

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
        edtName = findViewById(R.id.editText);
    }

    private void dialogAddNote() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_note);
        editNote = dialog.findViewById(R.id.editNote);
        btnOk = dialog.findViewById(R.id.btn_ok);
        btnCancel = dialog.findViewById(R.id.btn_cancel);
        if(!addNote.getText().toString().trim().equals(getString(R.string.add_a_note)))
            editNote.setText(addNote.getText().toString().trim());
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editNote.getText().toString().trim().equals("")) {
                    Toast.makeText(AddNewTaskActivity.this, R.string.you_can_not_add_empty_note, Toast.LENGTH_SHORT).show();
                }
                if(!addNote.getText().toString().trim().equals(R.string.add_a_note))
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
        if (addNote.getText().toString().trim().equals(R.string.add_a_note))
            note="";
        else note = addNote.getText().toString().trim();
        String date = setDate.getText().toString().trim();
        String day = null, month = null, year = null;
        String[] cutDate;
        if(!date.equals(getString(R.string.set_date))){
            cutDate = date.split(" ");
            day = cutDate[0];
            month = cutDate[1];
            year = cutDate[2];
        }
        String time = setTime.getText().toString().trim();
//        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//        Intent newIntent = new Intent(this, AlarmReceiver.class);
//        String alertTitle = edtName.getText().toString();
//        newIntent.putExtra(getString(R.string.alert_title), alertTitle);
//        pendingIntent = PendingIntent.getBroadcast(this,0, newIntent, 0);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        String repeat = setRepeat.getText().toString().trim();
        String timeReminder = addReminders.getText().toString().trim();
        String linkImage = takePhoto.getText().toString().trim();
        Task task = new Task(status,name, time, day, month, year, repeat,timeReminder, note,linkImage);
        return task;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_save){
            if(setDate.getText().toString().trim().equals(R.string.set_date)){
                Toast.makeText(AddNewTaskActivity.this, R.string.please_set_a_date, Toast.LENGTH_SHORT).show();
            }else
                if(setTime.getText().toString().trim().equals(getString(R.string.set_time))){
                    Toast.makeText(AddNewTaskActivity.this, R.string.please_set_a_time, Toast.LENGTH_SHORT).show();
                }else
                    if(edtName.getText().toString().trim().equals("")){
                Toast.makeText(AddNewTaskActivity.this, R.string.please_enter_character,Toast.LENGTH_SHORT).show();
            }else {
                Task task=createTask();
                database.addTask(task);

                alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                Intent newIntent = new Intent(this, AlarmReceiver.class);
                newIntent.putExtra(getString(R.string.alert_title), task.getNameTask());
                pendingIntent = PendingIntent.getBroadcast(this,0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                Intent intent = new Intent(AddNewTaskActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, R.string.add_success, Toast.LENGTH_SHORT).show();
            }
        }
        if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(AddNewTaskActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
