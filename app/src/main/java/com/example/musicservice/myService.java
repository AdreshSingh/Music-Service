package com.example.musicservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;

public class myService extends Service {
    MediaPlayer mp;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    // defining media player with service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        if (mp != null) {
            mp.setLooping(true);
            mp.start();
        }
        else{
            Log.e("bug", "onStartCommand: mp is null");
        }
        return START_NOT_STICKY;
    }


    // services get off or closed when activity get destroy
    @Override
    public void onDestroy() {
        mp.stop();
        super.onDestroy();

    }
}
