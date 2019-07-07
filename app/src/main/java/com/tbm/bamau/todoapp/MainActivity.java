package com.tbm.bamau.todoapp;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tbm.bamau.todoapp.Adapter.TaskAdapter;
import com.tbm.bamau.todoapp.Models.Task;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    Database database;
    ListView listTask;
    ArrayList<Task> arrayList;
    TaskAdapter adapter;

    private DrawerLayout drawer;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listTask = (ListView) findViewById(R.id.id_listView);
        arrayList = new ArrayList<>();
        adapter = new TaskAdapter(this,R.layout.item_task, arrayList);
        listTask.setAdapter(adapter);

        // tao database
        database = new Database(this, "todoapp.sqlite",null,1);
        //tao bang
        database.QueryData("CREATE TABLE IF NOT EXISTS Task(Id INTEGER PRIMARY KEY AUTOINCREMENT, NameTask VARCHAR(200))");
        // insert data
       // database.QueryData("INSERT INTO Task VALUES (null, 'Tối nay đá banh')");


        GetDataTasks();


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            DialogAdd();
        }

        return super.onOptionsItemSelected(item);
    }

    private void GetDataTasks(){
        // get data
        Cursor dataTask = database.GetData("SELECT * FROM Task");
        arrayList.clear();
        while (dataTask.moveToNext()){
            String name = dataTask.getString(1);
            int id = dataTask.getInt(0);
            arrayList.add(new Task(id,name,null));
        }
        adapter.notifyDataSetChanged();
    }

    private void DialogAdd(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_task);

        final EditText edtName = (EditText) dialog.findViewById(R.id.edit_text_nameTask);
        Button btnAdd = (Button) dialog.findViewById(R.id.btn_add);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTask = edtName.getText().toString();
                if(nameTask.equals("")){
                    Toast.makeText(MainActivity.this,"Please enter characters!",Toast.LENGTH_SHORT).show();
                }else{
                    database.QueryData("INSERT INTO Task VALUES (null, '"+nameTask+"')");
                    Toast.makeText(MainActivity.this, "Add success!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataTasks();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_work) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_home) {

        } else if (id == R.id.nav_add_new_list) {

        } else if (id == R.id.nav_done_tasks) {

        } else if (id == R.id.nav_later_tasks) {

        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
