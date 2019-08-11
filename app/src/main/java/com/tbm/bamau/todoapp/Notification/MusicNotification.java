package com.tbm.bamau.todoapp.Notification;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.tbm.bamau.todoapp.AddNewTaskActivity;
import com.tbm.bamau.todoapp.DbHelper;
import com.tbm.bamau.todoapp.Fragment.ViewDay_Fragment;
import com.tbm.bamau.todoapp.MainActivity;
import com.tbm.bamau.todoapp.Models.Task;
import com.tbm.bamau.todoapp.R;

public class MusicNotification extends AppCompatActivity{

    ViewDay_Fragment viewDay_fragment = new ViewDay_Fragment();
    MediaPlayer mediaPlayer;
    DbHelper database;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_notifications);

//        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.nhac_chuong);
//        mediaPlayer.start();
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        String msg = getString(R.string.alert) + getIntent().getExtras().getString(getString(R.string.title_msg));
//        builder.setMessage(msg).setCancelable(
//                false).setPositiveButton(getString(R.string.ok),
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                        MusicNotification.this.finish();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();

//        String nameTask = getIntent().getExtras().getString(getString(R.string.title_msg));
//        String msg = getString(R.string.alert)+"  "+ nameTask;
//        DbHelper db = new DbHelper(getBaseContext());
//        final Task task = db.getTaskByNameTask(nameTask);
//        String mTitle = task.getNameTask();
//
//        mediaPlayer = MediaPlayer.create(getBaseContext(),R.raw.nhac_chuong);
//        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
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

        database = new DbHelper(getBaseContext());
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.nhac_chuong);

        String name = getIntent().getExtras().getString(getString(R.string.title_msg));
        final Task task = database.getTaskByNameTask(name);
        final String date = task.getDayTask()+" "+task.getMonthTask()+" "+task.getYearTask();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String msg = getString(R.string.alert) + " "+ name;
        builder.setTitle(R.string.app_name);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.not_yet, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.UpdateStatusToLater(task);
                dialog.cancel();
                MusicNotification.this.finish();
                Intent intent = new Intent(MusicNotification.this,MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.UpdateStatus(task);
                dialog.cancel();
                MusicNotification.this.finish();
                Intent intent = new Intent(MusicNotification.this,MainActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        mediaPlayer.start();
    }


    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

}
