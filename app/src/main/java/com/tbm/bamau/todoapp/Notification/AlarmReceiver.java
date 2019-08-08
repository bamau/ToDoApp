package com.tbm.bamau.todoapp.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tbm.bamau.todoapp.DbHelper;
import com.tbm.bamau.todoapp.Models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    SimpleDateFormat tFormat = new SimpleDateFormat("K:mm a", Locale.ENGLISH);
    SimpleDateFormat hFormat = new SimpleDateFormat("K", Locale.ENGLISH);
    SimpleDateFormat mFormat = new SimpleDateFormat("mm", Locale.ENGLISH);
    @Override
    public void onReceive(Context context, Intent intent) {
        DbHelper database = new DbHelper(context);
        List<Task> listTime = new ArrayList<>();
        listTime = database.getListTaskWithStatus(0);
//        for (int i = 0; i<listTime.size(); i++){
//            String time = listTime.get(i).getTimeTask();
//            String hour = time.split(" ")[0];
//            String minute = time.split(" ")[1];
//            String day = listTime.get(i).getDayTask();
//            String month = listTime.get(i).getMonthTask();
//            String year = listTime.get(i).getYearTask();
//            alarmManager.set(AlarmManager.RTC_WAKEUP,,pendingIntent);
//        }
        Log.e("Toi trong Recwiver", "Xin chao");
        Intent myIntent = new Intent(context, MusicNotification.class);
        context.startService(myIntent);
    }
}
