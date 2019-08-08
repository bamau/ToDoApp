package com.tbm.bamau.todoapp.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tbm.bamau.todoapp.DbHelper;
import com.tbm.bamau.todoapp.Models.Task;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    SimpleDateFormat tFormat = new SimpleDateFormat("KK:mm:a", Locale.ENGLISH);
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, MusicNotification.class);
        DbHelper database = new DbHelper(context);
        List<Task> listTime = new ArrayList<>();
        listTime = database.getListTaskWithStatus(0);
        for (int i = 0; i<listTime.size(); i++){
            String time = listTime.get(i).getTimeTask();
            Time time_task = null;
            try {
                time_task = new Time(tFormat.parse(listTime.get(i).getTimeTask()).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String day = listTime.get(i).getDayTask();
            String month = listTime.get(i).getMonthTask();
            String year = listTime.get(i).getYearTask();
            Log.e("Toi trong Recwiver", "Xin chao");
            myIntent = new Intent(context, MusicNotification.class);

        }
        context.startService(myIntent);
    }
}
