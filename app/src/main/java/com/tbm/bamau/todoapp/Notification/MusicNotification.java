package com.tbm.bamau.todoapp.Notification;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tbm.bamau.todoapp.R;

public class MusicNotification extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_notifications);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.nhac_chuong);
        mediaPlayer.start();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String msg = getString(R.string.alert) + getIntent().getExtras().getString(getString(R.string.title_msg));
        builder.setMessage(msg).setCancelable(
                false).setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        MusicNotification.this.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
    //
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        mediaPlayer = MediaPlayer.create(this, R.raw.nhac_chuong);
//        mediaPlayer.start();
//        Log.e("Toi trong Music", "Xin chao");
//        return START_NOT_STICKY;
//    }
}
