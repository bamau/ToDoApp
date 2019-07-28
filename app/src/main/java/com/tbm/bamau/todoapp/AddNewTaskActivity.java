package com.tbm.bamau.todoapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tbm.bamau.todoapp.Adapter.AddTaskAdapter;
import com.tbm.bamau.todoapp.Adapter.SettingAdapter;
import com.tbm.bamau.todoapp.Models.AddTask;
import com.tbm.bamau.todoapp.Models.Settings;
import com.tbm.bamau.todoapp.Models.Task;

import java.util.ArrayList;

public class AddNewTaskActivity extends AppCompatActivity {

    TimePicker picker;
    TimePickerDialog timePickerDialog;
    DbHelper database;
    private ArrayList<AddTask> addTasksList;
    ListView listView;
    TextView edtName;
    AddTaskAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        getSupportActionBar().setTitle("New Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        database = new DbHelper(this);

        listView = findViewById(R.id.listView);
        addTasksList = new ArrayList<AddTask>();
        adapter = new AddTaskAdapter(this, R.layout.item_add_task, addTasksList);
        listView.setAdapter(adapter);

        addTasksList.add(new AddTask(0,"DUE","Set a due date and time",R.drawable.ic_event_black_24dp));
        addTasksList.add(new AddTask(0,"REMINDERS","Add time or location reminders",R.drawable.ic_notifications_black_24dp));
        addTasksList.add(new AddTask(0,"NOTE","Add a note",R.drawable.ic_note_black_24dp));
        addTasksList.add(new AddTask(0,"IMAGE","Take a photo or choose an image",R.drawable.ic_image_black_24dp));
        addTasksList.add(new AddTask(0,"AUDIO","Record an audio note",R.drawable.ic_keyboard_voice_black_24dp));
        adapter.notifyDataSetChanged();

        edtName = findViewById(R.id.editText);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:
                        dialogAddNote();
                        break;

                }
            }
        });

    }

    private void dialogAddNote() {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_task);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }


    public Task createTask() {
        String name = edtName.getText().toString();

        Task task = new Task(0,name,null,null,null,null,null,null,null,null,null);
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
