package com.tbm.bamau.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tbm.bamau.todoapp.Adapter.AddTaskAdapter;
import com.tbm.bamau.todoapp.Models.AddTask;
import com.tbm.bamau.todoapp.Models.Task;

import java.util.ArrayList;

public class UpdateTaskActivity extends AppCompatActivity {

    DbHelper database;
    private ArrayList<AddTask> addTasksList;
    ListView listView;
    TextView edtName;
    AddTaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        listView = findViewById(R.id.listView);
        addTasksList = new ArrayList<AddTask>();
        adapter = new AddTaskAdapter(this, R.layout.item_add_task, addTasksList);
        listView.setAdapter(adapter);
    }

    private void GetTaskDetail(){

        Intent intent = getIntent();
        int idTask =intent.getIntExtra("ID",0);
        Cursor dataTask = database.GetData("SELECT FORM Task WHERE Id='"+idTask+"'");
        edtName.setText(dataTask.getColumnIndex("1"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
