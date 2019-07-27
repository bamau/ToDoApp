package com.tbm.bamau.todoapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.tbm.bamau.todoapp.Adapter.TaskAdapter;
import com.tbm.bamau.todoapp.Fragment.ViewDay_Fragment;
import com.tbm.bamau.todoapp.Fragment.ViewMonth_Fragment;
import com.tbm.bamau.todoapp.Fragment.ViewWeek_Fragment;
import com.tbm.bamau.todoapp.Models.Task;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DbHelper database;
    FloatingActionButton floatingActionButton;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DbHelper(this);        AnhXa();


//        listTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Task task = arrayList.get(position);
//                int idTask = task.getIdTask();
//                Intent intent = new Intent(MainActivity.this, UpdateTaskActivity.class);
//                intent.putExtra("ID",idTask);
//                Toast.makeText(MainActivity.this, "Bam vao vi tri = '"+position+"'", Toast.LENGTH_SHORT).show();
//                startActivity(intent);
//
//            }
//        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Day");
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewTaskActivity.class);
                startActivity(intent);
            }
        });

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ViewDay_Fragment()).commit();
            navigationView.setCheckedItem(R.id.nav_day);
        }

    }

    public void AnhXa(){
        floatingActionButton = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
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
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.view_setting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

//    private void GetDataTasks(){
//        // get data
//        Cursor dataTask = database.GetData("SELECT * FROM Task");
//        arrayList.clear();
//        while (dataTask.moveToNext()){
//            String name = dataTask.getString(1);
//            int id = dataTask.getInt(0);
//            arrayList.add(new Task(id,name,null));
//        }
//     //   adapter.notifyDataSetChanged();
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_day) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ViewDay_Fragment()).commit();
            getSupportActionBar().setTitle("Day");
        } else if (id == R.id.nav_week) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ViewWeek_Fragment()).commit();
            getSupportActionBar().setTitle("Week");
        } else if (id == R.id.nav_month) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ViewMonth_Fragment()).commit();
            getSupportActionBar().setTitle("Month");
        } else if (id == R.id.nav_done_tasks) {
            Toast.makeText(this, "Done Task", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
