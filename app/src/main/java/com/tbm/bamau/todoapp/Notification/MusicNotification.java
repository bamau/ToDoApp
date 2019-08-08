package com.tbm.bamau.todoapp.Notification;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tbm.bamau.todoapp.R;

public class MusicNotification extends Service {

    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer = MediaPlayer.create(this, R.raw.nhac_chuong);
        mediaPlayer.start();
        Log.e("Toi trong Music", "Xin chao");
        return START_NOT_STICKY;
    }
}
