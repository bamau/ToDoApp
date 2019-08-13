package com.tbm.bamau.todoapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
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
        ViewMonth_Fragment.OnMessageReadListenerMonth, ViewDay_Fragment.OnMessageReadListenerDay,
        ViewLaterTask_Fragment.OnMessageReadListenerLater, ViewDoneTask_Fragment.OnMessageReadListenerDone {


    private long backPressedTime;
    TextView curDay;
    DbHelper database;
    FloatingActionButton floatingActionButton;
    private DrawerLayout drawer;
    Toolbar toolbar;
    FrameLayout frameLayout;
    Calendar calendar = Calendar.getInstance();
    private AlarmReceiver mAlarmReceiver;
    ImageButton nextButton, previousButton;
    TextView currentDate;
    int check;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMM yyyy",Locale.ENGLISH);
    final  String currDate = dateFormat.format(calendar.getTime());
    final  String currDay = dateFormat.format(calendar.getTime());
    final  String mDay = monthFormat.format(calendar.getTime());

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
        String[] cutDay =dateFormat.format(calendar.getTime()).split(" ");
        curDay.setText(cutDay[0]);
        setSupportActionBar(toolbar);
        currentDate.setText(currDate);
        try {
            checkListWithDate(0,currDate);
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


        nextButton.setOnClickListener(new View.OnClickListener() {
            String cDate, mDate, lDate, dDate;
            @Override
            public void onClick(View v) {
                if (check==0){
                    calendar.add(calendar.DAY_OF_MONTH,1);
                    cDate = dateFormat.format(calendar.getTime());
                    currentDate.setText(cDate);
                    viewDay_fragment.updateListTaskWithChangeDate(cDate,0);
                    viewDay_fragment.updateListTaskDoneWithChangeDate(cDate,1);
                }
                if (check==1){
                    calendar.add(calendar.MONTH,1);
                    mDate = monthFormat.format(calendar.getTime());
                    currentDate.setText(mDate);
                    viewMonth_fragment.setUpCalendarWithCurrentDate(mDate);
                }
                if(check == 2){
                    calendar.add(calendar.MONTH,1);
                    dDate = monthFormat.format(calendar.getTime());
                    currentDate.setText(dDate);
                    String[] cutMonth = dDate.split(" ");
                    viewDoneTask_fragment.getListDoneTaskOneMonth(cutMonth[0],cutMonth[1],1);
                }
                if(check == 3){
                    calendar.add(calendar.MONTH,1);
                    lDate = monthFormat.format(calendar.getTime());
                    currentDate.setText(lDate);
                    String[] cutMonth = lDate.split(" ");
                    viewLaterTask_fragment.getListLaterTaskOneMonth(cutMonth[0],cutMonth[1],2);
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            String cDate, mDate, lDate, dDate;
            @Override
            public void onClick(View v) {
                if (check==0){
                    calendar.add(calendar.DAY_OF_MONTH,-1);
                   cDate = dateFormat.format(calendar.getTime());
                    currentDate.setText(cDate);
                    viewDay_fragment.updateListTaskWithChangeDate(cDate,0);
                    viewDay_fragment.updateListTaskDoneWithChangeDate(cDate,1);
                }
                if (check==1){
                    calendar.add(calendar.MONTH,-1);
                    mDate = monthFormat.format(calendar.getTime());
                    currentDate.setText(mDate);
                    viewMonth_fragment.setUpCalendarWithCurrentDate(mDate);
                }
                if(check == 2){
                    calendar.add(calendar.MONTH,-1);
                    dDate = monthFormat.format(calendar.getTime());
                    currentDate.setText(dDate);
                    String[] cutMonth = dDate.split(" ");
                    viewDoneTask_fragment.getListDoneTaskOneMonth(cutMonth[0],cutMonth[1],1);
                }
                if(check == 3){
                    calendar.add(calendar.MONTH,-1);
                    lDate = monthFormat.format(calendar.getTime());
                    currentDate.setText(lDate);
                    String[] cutMonth = lDate.split(" ");
                    viewLaterTask_fragment.getListLaterTaskOneMonth(cutMonth[0],cutMonth[1],2);
                }
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewTaskActivity.class);
                startActivity(intent);
            }
        });

        if(savedInstanceState == null){
            Bundle bundle = new Bundle();
            bundle.putString("CHANGE_DATE", currDate);
            viewDay_fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, viewDay_fragment).commit();
            check=0;
            navigationView.setCheckedItem(R.id.nav_day);
        }

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check==0){
                    currentDate.setText(currDay);
                    try {
                        calendar.setTime(formatDate(currDay));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    viewDay_fragment.getListTaskOneDate(currDay,0);
                    viewDay_fragment.getListTaskDoneOneDate(currDay,1);
                }
                if (check==1){
                    currentDate.setText(mDay);
                    try {
                        calendar.setTime(formatDate(currDay));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, viewMonth_fragment).commit();
                    viewMonth_fragment.setUpCalendarWithCurrentDate(mDay);
                }
                if (check==2){
                    currentDate.setText(mDay);
                    String[] cutmDay=mDay.split(" ");
                    try {
                        calendar.setTime(formatDate(currDay));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, viewDoneTask_fragment).commit();
                    viewDoneTask_fragment.getListDoneTaskOneMonth(cutmDay[0],cutmDay[1],1);
                }
                if (check==3){
                    currentDate.setText(mDay);
                    String[] cutmDay=mDay.split(" ");
                    try {
                        calendar.setTime(formatDate(currDay));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, viewLaterTask_fragment).commit();
                    viewLaterTask_fragment.getListLaterTaskOneMonth(cutmDay[0],cutmDay[1],2);
                }
            }
        });

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
    private Date formatMonth (String date) throws ParseException {
        Date fDate = monthFormat.parse(date);
        return fDate;
    }
    public void Initialization(){
        nextButton = findViewById(R.id.nextBtn);
        previousButton = findViewById(R.id.previousBtn);
        currentDate = findViewById(R.id.currentDate);
        curDay = findViewById(R.id.curDay);
        floatingActionButton = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        frameLayout = findViewById(R.id.frameCurrentday);
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_day) {
            if (check==1 || check == 2 || check == 3){
                currentDate.setText(currDate);
                try {
                    calendar.setTime(formatDate(currDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, viewDay_fragment).commit();
                check=0;
            }else{
                check=0;
            }
        } else if (id == R.id.nav_month) {
            if (check==0 || check == 2 || check == 3){
                currentDate.setText(mDay);
                try {
                    calendar.setTime(formatMonth(mDay));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, viewMonth_fragment).commit();
                check =1;
            }else{
                check=1;
            }
        } else if (id == R.id.nav_done_tasks) {
            if (check==0 || check == 1 || check == 3){
                currentDate.setText(mDay);
                try {
                    calendar.setTime(formatMonth(mDay));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, viewDoneTask_fragment).commit();
                check=2;
            } else {
                check=2;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, viewDoneTask_fragment).commit();
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_later_tasks) {
            if (check==0 || check == 1 || check == 2){
                currentDate.setText(mDay);
                try {
                    calendar.setTime(formatMonth(mDay));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, viewLaterTask_fragment).commit();
                check=3;
            }else {
                check=3;
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMessageReadMonth(CharSequence input) {
        currentDate.setText(input);
        try {
            calendar.setTime(formatDate((String) input));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putString("CHANGE_DATE", (String) input);
        viewDay_fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,viewDay_fragment).commit();
        check=0;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    @Override
    public void onMessageReadDay(CharSequence input) {
    }
    @Override
    public void onMessageReadLater(CharSequence input) {
    }
    @Override
    public void onMessageReadDone(CharSequence input) {
    }
}
