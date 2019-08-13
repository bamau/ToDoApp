package com.tbm.bamau.todoapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tbm.bamau.todoapp.Models.Task;
import com.tbm.bamau.todoapp.Notification.AlarmReceiver;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static android.support.constraint.motion.MotionScene.TAG;
import static com.tbm.bamau.todoapp.MainActivity.hideSoftKeyboard;

public class UpdateTaskActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    DbHelper database;
    TextView due, setDate, setTime, setRepeat, reminders, addReminders, note, addNote, image, takePhoto;
    EditText edtName, editNote;
    Button btnOk, btnCancel;
    Calendar calendar = Calendar.getInstance();

    int nowTime, currTime;
    Date curDate, nowDate;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH);
    SimpleDateFormat tFormat = new SimpleDateFormat("KK:mm:a", Locale.ENGLISH);

    final String[] Reminder = { "Due", "Before 5 minutes", "Before 10 minutes", "Before 15 minutes", "Before 20 minutes"};
    final String[] Repeat = { "No Repeat", "Every day", "Every week", "Every month", "Every year"};

    public static final String EXTRA_TASK_ID = "Task_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        getSupportActionBar().setTitle(R.string.update_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Initialization();
        setupUI(findViewById(R.id.viewUpdate));

        Bundle bundle = getIntent().getExtras();
        final int idTask = bundle.getInt("ID",0);

        final String currentDate = dateFormat.format(calendar.getTime());
        try {
            curDate = formatDate(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        database = new DbHelper(this);
        Log.d(TAG, "'"+idTask+"'");
        Task task = database.getTaskById(idTask);
        getDetailTask(task);

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(setDate.getContext(), R.style.Theme_AppCompat_DayNight_Dialog
                        , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        calendar.setTimeZone(TimeZone.getDefault());
                        String task_date = dateFormat.format(calendar.getTime());
                        try {
                            nowDate = formatDate(task_date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        setDate.setText(task_date);
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });
        currTime = (int) calendar.getTimeInMillis();
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
                        nowTime = (int) calendar.getTimeInMillis();
                        setTime.setText(task_time);

                    }
                },hours,minute,false);
                timePickerDialog.show();
            }
        });
        setRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTaskActivity.this);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTaskActivity.this);
                builder.setTitle("Pick a reminder");
                builder.setItems(Reminder, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int h = calendar.get(Calendar.HOUR);
                        int m = calendar.get(Calendar.MINUTE);
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

    }

    private void dialogAddNote() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_note);
        editNote = dialog.findViewById(R.id.editNote);
        btnOk = dialog.findViewById(R.id.btn_ok);
        btnCancel = dialog.findViewById(R.id.btn_cancel);
        if(!addNote.getText().toString().trim().equals("Add a note"))
        {
            editNote.setText(addNote.getText().toString().trim());
            edtName.setSelection(edtName.getText().length());
        }
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editNote.getText().toString().trim().equals(""))
                    Toast.makeText(UpdateTaskActivity.this, R.string.you_can_not_add_empty_note, Toast.LENGTH_SHORT).show();
                if(addNote.getText().toString().trim().equals("Add a note"))
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


    public Task createTaskWithOldId(int id) {
        int status = 0;
        String name = edtName.getText().toString().trim();
        String note;
        if (addNote.getText().toString().trim().equals(R.string.add_a_note))
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
        Task task = new Task(id,status,name, time, day, month, year, repeat,timeReminder, note,linkImage);
        return task;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = getIntent().getExtras();
        int idTask = bundle.getInt("ID");
        if (id == R.id.action_save) {
            if(edtName.getText().toString().trim().equals("")) {
                Toast.makeText(UpdateTaskActivity.this, R.string.please_enter_character, Toast.LENGTH_SHORT).show();
            }else if(setDate.getText().toString().trim().equals(R.string.set_date)) {
                Toast.makeText(UpdateTaskActivity.this, R.string.please_set_a_date, Toast.LENGTH_SHORT).show();
            }else if(setTime.getText().toString().trim().equals(getString(R.string.set_time))) {
                Toast.makeText(UpdateTaskActivity.this, R.string.please_set_a_time, Toast.LENGTH_SHORT).show();
            }else if (curDate.compareTo(nowDate)>0){
                Toast.makeText(UpdateTaskActivity.this, R.string.please_set_a_date_higher, Toast.LENGTH_SHORT).show();
            }else if (curDate.compareTo(nowDate)==0) {
                if (nowTime == 0){
                    Toast.makeText(UpdateTaskActivity.this, R.string.please_set_a_time_higher, Toast.LENGTH_SHORT).show();
                }else  if (currTime>nowTime){
                    Toast.makeText(UpdateTaskActivity.this, R.string.please_set_a_time_higher, Toast.LENGTH_SHORT).show();
                }else if (currTime<nowTime){
                    Task task = createTaskWithOldId(idTask);
                    database.Update(task);
                    setAlarm(UpdateTaskActivity.this,calendar,task.getNameTask());
                    Intent intent = new Intent(UpdateTaskActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(this, R.string.update_success, Toast.LENGTH_SHORT).show();
                }
            }else {
                Task task = createTaskWithOldId(idTask);
                database.Update(task);
                setAlarm(UpdateTaskActivity.this,calendar,task.getNameTask());
                Intent intent = new Intent(UpdateTaskActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(this, R.string.update_success, Toast.LENGTH_SHORT).show();
            }
        }
        if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDetailTask(Task task){

        int i = task.getIdTask();
        edtName.setText(task.getNameTask());
        edtName.setSelection(edtName.getText().length());
        String day, month, year;
        day = task.getDayTask();
        month = task.getMonthTask();
        year = task.getYearTask();
        setDate.setText(day+" "+month+" "+year);
        try {
            nowDate = formatDate(day+" "+month+" "+year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setTime.setText(task.getTimeTask());

        setRepeat.setText(task.getRepeat());
        addReminders.setText(task.getTimeReminder());
        addNote.setText(task.getNote());
        takePhoto.setText(task.getLinkImage());
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

    public void setAlarm(Context context, Calendar calendar, String name){
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent newIntent = new Intent(this, AlarmReceiver.class);
        newIntent.putExtra(getString(R.string.alert_title), name);
        pendingIntent = PendingIntent.getBroadcast(this,0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(UpdateTaskActivity.this);
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

    public Date formatDate (String date) throws ParseException {
        Date fDate = dateFormat.parse(date);
        return fDate;
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
