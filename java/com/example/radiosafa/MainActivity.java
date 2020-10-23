package com.example.radiosafa;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MainActivity have a online music player and work with a URL from its server.
 *
 * @author Haj Rezvan
 * @version 2.5
 */
public class MainActivity extends AppCompatActivity {

    private SwitchCompat switchCompat;
    private ImageButton refreshButton;
    private MediaPlayer mediaPlayer;
    private Chronometer chronometer;
    private Connector connector;
    private boolean isChecked;
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
        setContentView(R.layout.activity_main_2);
        connector = new Connector();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(connector);
        isChecked = false;
        setupViews();

    }

    public void setupViews() {
        Listener listener = new Listener();

        setInfoButton((ImageButton) findViewById(R.id.info_btn_id), listener);
        setRefreshButton((ImageButton) findViewById(R.id.refresh_btn_id), listener);
        setSwitchCompat((SwitchCompat) findViewById(R.id.switch_play_pause2));
        setChronometer((Chronometer) findViewById(R.id.chronometer_id));
        switchCompat.setOnCheckedChangeListener(listener);
        chronometer.setVisibility(View.VISIBLE);

        mediaPlayer = connector.getMediaPlayer();

        findViewById(R.id.refresh_btn_id).setVisibility(View.GONE);
    }

    public void setRefreshButton(ImageButton refreshButton, Listener listener) {
        this.refreshButton = refreshButton;
        this.refreshButton.setOnClickListener(listener);
    }

    public void setInfoButton(ImageButton infoButton, Listener listener) {
        infoButton.setOnClickListener(listener);
    }

    public void setSwitchCompat(SwitchCompat switchCompat) {
        this.switchCompat = switchCompat;
    }

    public void setChronometer(Chronometer chronometer) {
        this.chronometer = chronometer;
    }

    public void showPlayButton() {
        switchCompat.setChecked(true);
    }

    public void showPauseButton() {
        switchCompat.setChecked(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initialize() {
        counter += 2;
        if (connector.isChecker()) {
            mediaPlayer.start();
            chronometer.start();
            showPlayButton();
            System.out.println("*******************************************");
            System.out.println(Arrays.toString(mediaPlayer.getTrackInfo()));
            System.out.println("*******************************************");
        } else {
            System.err.println("We have a problem in play file");
            switchCompat.setVisibility(View.INVISIBLE);
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
        chronometer.stop();
        showPauseButton();
        mediaPlayer.pause();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void switchCheck() {
        if (switchCompat.isChecked()) {
            if (isChecked) {
                checkPlay();
            }
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

    private class Listener implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View view) {
            int viewID = view.getId();

            switch (viewID) {
                case R.id.info_btn_id:
                    showInfoPage();
                    break;
                case R.id.refresh_btn_id:
                    connector.refresh();
                    initialize();
                    break;
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switchCompat = (SwitchCompat) compoundButton;
            isChecked = b;
            switchCheck();
        }
    }

}