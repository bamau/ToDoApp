package com.tbm.bamau.todoapp.Notification;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.tbm.bamau.todoapp.AddNewTaskActivity;
import com.tbm.bamau.todoapp.DbHelper;
import com.tbm.bamau.todoapp.Models.Task;
import com.tbm.bamau.todoapp.R;
import com.tbm.bamau.todoapp.UpdateTaskActivity;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {

    AlarmManager mAlarmManager;
    PendingIntent mPendingIntent;
    MediaPlayer mediaPlayer;
    DbHelper database;

    @Override
    public void onReceive(Context context, Intent intent) {
//        int mReceivedID = Integer.parseInt(intent.getStringExtra(AddNewTaskActivity.EXTRA_TASK_ID));
//        DbHelper db = new DbHelper(context);
//        final Task task = db.getTaskById(mReceivedID);
//        String mTitle = task.getNameTask();

        // Create intent to open ReminderEditActivity on notification click
//        Intent editIntent = new Intent(context, AddNewTaskActivity.class);
//        editIntent.putExtra(AddNewTaskActivity.EXTRA_TASK_ID, Integer.toString(mReceivedID));
//        PendingIntent mClick = PendingIntent.getActivity(context, mReceivedID, editIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        // Create Notification
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "NOTIFICATION")
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
//                .setSmallIcon(R.drawable.ic_access_alarms_black_24dp)
//                .setContentTitle(context.getResources().getString(R.string.app_name))
//                .setTicker(mTitle)
//                .setContentText(mTitle)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
//                .setContentIntent(mClick)
//                .setAutoCancel(true)
//                .setOnlyAlertOnce(true);
//
//        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        nManager.notify(mReceivedID, mBuilder.build());
//        database = new DbHelper(context);
//        mediaPlayer = MediaPlayer.create(context,R.raw.nhac_chuong);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(R.string.app_name);
//        builder.setMessage(mTitle);
//        builder.setNegativeButton(R.string.not_yet, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                database.UpdateStatusToLater(task);
//                dialog.cancel();
//
//            }
//        });
//        builder.setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                database.UpdateStatus(task);
//                dialog.cancel();
//            }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();
//        mediaPlayer.start();


        String Title = intent.getStringExtra(context.getString(R.string.alert_title));
        Intent x = new Intent(context, MusicNotification.class);
        x.putExtra(context.getString(R.string.alert_title), Title);
        x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(x);
    }

//    public void setAlarm(Context context, Calendar calendar, int ID) {
//        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        // Put Reminder ID in Intent Extra
//        Intent intent = new Intent(context, MusicNotification.class);
//        intent.putExtra(AddNewTaskActivity.EXTRA_TASK_ID, Integer.toString(ID));
//        mPendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//
//        // Calculate notification time
//        Calendar c = Calendar.getInstance();
//        long currentTime = c.getTimeInMillis();
//        long diffTime = calendar.getTimeInMillis() - currentTime;
//
//        // Start alarm using notification time
//        mAlarmManager.set(AlarmManager.ELAPSED_REALTIME,
//                SystemClock.elapsedRealtime() + diffTime,
//                mPendingIntent);
//
//        //Restart alarm if device is rebooted
//        ComponentName receiver = new ComponentName(context, BootReceiver.class);
//        PackageManager pm = context.getPackageManager();
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);
//    }
}
