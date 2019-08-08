package com.tbm.bamau.todoapp;

import android.app.AlarmManager;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.tbm.bamau.todoapp.Adapter.TaskAdapter;
import com.tbm.bamau.todoapp.Fragment.ViewDay_Fragment;
import com.tbm.bamau.todoapp.Fragment.ViewDoneTask_Fragment;
import com.tbm.bamau.todoapp.Fragment.ViewLaterTask_Fragment;
import com.tbm.bamau.todoapp.Fragment.ViewMonth_Fragment;
import com.tbm.bamau.todoapp.Fragment.ViewWeek_Fragment;
import com.tbm.bamau.todoapp.Models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ViewMonth_Fragment.OnMessageReadListenerMonth, ViewDay_Fragment.OnMessageReadListenerDay {


    DbHelper database;
    FloatingActionButton floatingActionButton;
    private DrawerLayout drawer;
    Toolbar toolbar;
    AlarmManager alarmManager;

    ViewDay_Fragment viewDay_fragment = new ViewDay_Fragment();
    ViewWeek_Fragment viewWeek_fragment = new ViewWeek_Fragment();
    ViewMonth_Fragment viewMonth_fragment = new ViewMonth_Fragment();
    ViewDoneTask_Fragment viewDoneTask_fragment = new ViewDoneTask_Fragment();
    ViewLaterTask_Fragment viewLaterTask_fragment = new ViewLaterTask_Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DbHelper(this);
        Initialization();
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
                    viewDay_fragment).commit();
            navigationView.setCheckedItem(R.id.nav_day);
        }

    }

    public void Initialization(){
        floatingActionButton = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_day) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    viewDay_fragment).commit();
            getSupportActionBar().setTitle("Day");
        } else if (id == R.id.nav_week) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    viewWeek_fragment).commit();
            getSupportActionBar().setTitle("Week");
        } else if (id == R.id.nav_month) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    viewMonth_fragment).commit();
            getSupportActionBar().setTitle("Month");
        } else if (id == R.id.nav_done_tasks) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    viewDoneTask_fragment).commit();
            getSupportActionBar().setTitle("Done Task");
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_later_tasks) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    viewLaterTask_fragment).commit();
            getSupportActionBar().setTitle("Later Task");
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMessageReadDay(CharSequence input) {

    }

    @Override
    public void onMessageReadMonth(CharSequence input) {
        Bundle bundle = new Bundle();
        bundle.putString("CHANGE_DATE", (String) input);
        viewDay_fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,viewDay_fragment).commit();
        getSupportActionBar().setTitle("Day");
    }
}
