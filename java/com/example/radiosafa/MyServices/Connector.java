package com.example.radiosafa.MyServices;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

public class Connector implements Runnable {

    private MediaPlayer mediaPlayer;
    private Context activity;

    private boolean checker;

    public Connector(Context context) {
        this.activity = context;
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
        String url = "https://radio.masjedsafa.com/relay?type=http&nocache=4"; // your URL here
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
            System.err.println("**********\n\n\n\n\n\n\n\n\nWe have a problem" +
                    "\n\n\n\n\n\n\n\n\n**********");
            return false;
        } catch (Exception e) {

            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            };

            AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            alert.setMessage("خطا در اتصال به سرور، لطفا اندکی بعد تلاش کنید")
                    .setCancelable(false)
                    .setTitle("متأسفم").setPositiveButton("متوجه شدم", listener)
                    .show();
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

    public void reset() {
        mediaPlayer.reset();
    }
}
