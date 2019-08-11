package com.tbm.bamau.todoapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.tbm.bamau.todoapp.Fragment.ViewDay_Fragment;
import com.tbm.bamau.todoapp.Fragment.ViewDoneTask_Fragment;
import com.tbm.bamau.todoapp.Fragment.ViewLaterTask_Fragment;
import com.tbm.bamau.todoapp.Fragment.ViewMonth_Fragment;
import com.tbm.bamau.todoapp.Models.Task;
import com.tbm.bamau.todoapp.Notification.AlarmReceiver;
import com.tbm.bamau.todoapp.Notification.BootReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ViewMonth_Fragment.OnMessageReadListenerMonth, ViewDay_Fragment.OnMessageReadListenerDay {


    private long backPressedTime;

    DbHelper database;
    FloatingActionButton floatingActionButton;
    private DrawerLayout drawer;
    Toolbar toolbar;
    private AlarmReceiver mAlarmReceiver;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
    Calendar calendar = Calendar.getInstance();
    final  String currentDate = dateFormat.format(calendar.getTime());

    ViewDay_Fragment viewDay_fragment = new ViewDay_Fragment();
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
        getSupportActionBar().setTitle(R.string.day);
        try {
            checkListWithDate(0,currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mAlarmReceiver = new AlarmReceiver();
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
            Bundle bundle = new Bundle();
            bundle.putString("CHANGE_DATE", currentDate);
            viewDay_fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    viewDay_fragment).commit();
            navigationView.setCheckedItem(R.id.nav_day);
        }

    }

    private void checkListWithDate(int status, String currentDate) throws ParseException {
        database = new DbHelper(this);
        Date curDate = formatDate(currentDate);
        List<Task> list = new ArrayList<>();
        list = database.getListTaskWithStatusOrderByYear(status);
        list = database.getListTaskWithStatusOrderByMonth(status);
        list = database.getListTaskWithStatusOrderByDay(status);
        list = database.getListTaskWithStatusOrderByTime(status);
        String lDay=null, lMonth=null, lYear=null, lDate=null;
        int check;
        for(int i =0 ; i< list.size(); i++){
            lDay = list.get(i).getDayTask();
            lMonth = list.get(i).getMonthTask();
            lYear = list.get(i).getYearTask();
            lDate = lDay+" "+lMonth+" "+lYear;
            Date tDate = formatDate(lDate);
            check = curDate.compareTo(tDate);
            if (check > 0){
                database.UpdateStatusToLater(list.get(i));
            }
        }
    }

    private Date formatDate (String date) throws ParseException {
        Date fDate = dateFormat.parse(date);
        return fDate;
    }

    public void Initialization(){
        floatingActionButton = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
    }

    boolean twice = false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (backPressedTime + 2000 > System.currentTimeMillis()){
                super.onBackPressed();
                return;
            }else{
                Toast.makeText(getBaseContext(), R.string.backpress, Toast.LENGTH_SHORT).show();
            }
            backPressedTime = System.currentTimeMillis();
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
            Bundle bundle = new Bundle();
            bundle.putString("CHANGE_DATE", currentDate);
            viewDay_fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    viewDay_fragment).commit();
            getSupportActionBar().setTitle(R.string.day);
        } else if (id == R.id.nav_month) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    viewMonth_fragment).commit();
            getSupportActionBar().setTitle(R.string.month);
        } else if (id == R.id.nav_done_tasks) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    viewDoneTask_fragment).commit();
            getSupportActionBar().setTitle(R.string.done_task);
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_later_tasks) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    viewLaterTask_fragment).commit();
            getSupportActionBar().setTitle(R.string.later_task);
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
        getSupportActionBar().setTitle(R.string.day);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
