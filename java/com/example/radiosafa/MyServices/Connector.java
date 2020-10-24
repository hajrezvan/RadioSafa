package com.example.radiosafa.MyServices;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

public class Connector implements Runnable{

    private MediaPlayer mediaPlayer;

    private boolean checker;

    public Connector() {
        mediaPlayer = new MediaPlayer();
        checker = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void run() {
        setURL();
        checker = initializer();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setURL() {
        String url = "URL"; // your URL here
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean initializer() {
        try {
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
            return true;
        } catch (IOException exception) {
            System.err.println("We have a problem in play file");
            return false;
        }
    }

    public boolean isChecker() {
        return checker;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void refresh() {
        checker = initializer();
    }
}
