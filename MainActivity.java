package com.example.radiosafa;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MainActivity have a online music player and work with a URL from its server.
 *
 * @author Haj Rezvan
 * @version 1.5
 */
public class MainActivity extends AppCompatActivity {

    private ImageButton playerButton;
    private ImageButton infoButton;
    private ImageButton pauseButton;
    private ImageButton refreshButton;
    private MediaPlayer mediaPlayer;
    private Chronometer chronometer;
    private Connector connector;
    private int counter = 0;

    /**
     * When program starts, view begin from this method.
     * This method initialises field and set them in activity.
     *
     * @param savedInstanceState pass to onCreate method in its super class.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connector = new Connector();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(connector);
        setupViews();
    }

    public void setupViews() {
        Listener listener = new Listener();

        setPlayerButton((ImageButton) findViewById(R.id.play_btn_id), listener);
        setInfoButton((ImageButton) findViewById(R.id.info_btn_id), listener);
        setPauseButton((ImageButton) findViewById(R.id.pause_btn_id), listener);
        setRefreshButton((ImageButton) findViewById(R.id.refresh_btn_id), listener);
        setChronometer((Chronometer) findViewById(R.id.chronometer_id));

        mediaPlayer = connector.getMediaPlayer();

        findViewById(R.id.pause_btn_id).setVisibility(View.GONE);
        findViewById(R.id.refresh_btn_id).setVisibility(View.GONE);
    }

    public void setChronometer(Chronometer chronometer) {
        this.chronometer = chronometer;
        this.chronometer.setFormat("%s مدت زمان گذشته از کلاس");
        this.chronometer.setText("%s مدت زمان گذشته از کلاس");
    }

    public void setPlayerButton(ImageButton playerButton, Listener listener) {
        this.playerButton = playerButton;
        this.playerButton.setOnClickListener(listener);
    }

    public void setRefreshButton(ImageButton refreshButton, Listener listener) {
        this.refreshButton = refreshButton;
        this.refreshButton.setOnClickListener(listener);
    }

    public void setInfoButton(ImageButton infoButton, Listener listener) {
        this.infoButton = infoButton;
        this.infoButton.setOnClickListener(listener);
    }

    public void setPauseButton(ImageButton pauseButton, Listener listener) {
        this.pauseButton = pauseButton;
        this.pauseButton.setOnClickListener(listener);
    }

    public void showPlayButton() {
        findViewById(R.id.pause_btn_id).setVisibility(View.VISIBLE);
        findViewById(R.id.play_btn_id).setVisibility(View.GONE);
    }

    public void showPauseButton() {
        findViewById(R.id.pause_btn_id).setVisibility(View.GONE);
        findViewById(R.id.play_btn_id).setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initialize() {
        counter += 2;
        if (connector.isChecker()){
            mediaPlayer.start();
            chronometer.start();
            showPlayButton();
            System.out.println("*******************************************");
            System.out.println(Arrays.toString(mediaPlayer.getTrackInfo()));
            System.out.println("*******************************************");
        } else {
            System.err.println("We have a problem in play file");
            findViewById(R.id.pause_btn_id).setVisibility(View.GONE);
            findViewById(R.id.play_btn_id).setVisibility(View.GONE);
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setMessage("کلاس هنوز شروع نشده، لطفا اندکی بعد تلاش کنید").show();
            refreshButton.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void play() {
        counter++;
        showPlayButton();
        mediaPlayer.start();
        chronometer.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void stop() {
        counter--;
        showPauseButton();
        mediaPlayer.pause();
        chronometer.stop();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void checkPlay() {
        if (counter == 0) {
            initialize();
        } else if (counter == 2) {
            stop();
        } else if (counter == 1) {
            play();
        }
    }

    public void showInfoPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        }, 0);
    }

    private class Listener implements View.OnClickListener {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View view) {
            int viewID = view.getId();

            switch (viewID) {
                case R.id.play_btn_id:
                case R.id.pause_btn_id:
                    checkPlay();
                    break;
                case R.id.info_btn_id:
                    showInfoPage();
                    break;
                case R.id.refresh_btn_id:
                    connector.refresh();
                    initialize();
                    break;
            }
        }
    }

}