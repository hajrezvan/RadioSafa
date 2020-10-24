package com.example.radiosafa.MyServices;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.radiosafa.Activites.Info.InfoActivity;
import com.example.radiosafa.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Components class, setup views for MainActivity activity and MainActivityDark activity.
 * This class setup components in view and handling them.
 *
 * @author Haj Rezvan
 * @version 1.2.4
 */
public class Components {
    private AppCompatActivity activity;
    private SwitchCompat switchCompat;
    private ImageButton refreshButton;
    private MediaPlayer mediaPlayer;
    private Connector connector;
    private boolean isChecked;
    private int counter = 0;

    /**
     * Initialize the fields and setting them.
     * @param activity is a activity that should be setup components on its.
     */
    public Components(AppCompatActivity activity) {
        this.activity = activity;
    }

    /**
     * Initialize connector and create a executor service for checking server connecting.
     * Than setting components.
     */
    public void setup() {
        connector = new Connector();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(connector);
        isChecked = false;
        setupViews();
    }

    /**
     * setting components and their listeners.
     */
    public void setupViews() {
        Listener listener = new Listener();
        setInfoButton((ImageButton) activity.findViewById(R.id.info_btn_id), listener);
        setRefreshButton((ImageButton) activity.findViewById(R.id.refresh_btn_id), listener);
        setSwitchCompat((SwitchCompat) activity.findViewById(R.id.switch_play_pause2));
        switchCompat.setOnCheckedChangeListener(listener);

        mediaPlayer = connector.getMediaPlayer();

        activity.findViewById(R.id.refresh_btn_id).setVisibility(View.GONE);
    }

    /**
     * @param refreshButton a button for refreshing server connecting.
     * @param listener a listener that button set its to itself.
     */
    public void setRefreshButton(ImageButton refreshButton, Listener listener) {
        this.refreshButton = refreshButton;
        this.refreshButton.setOnClickListener(listener);
    }

    /**
     * @param infoButton a button for showing info page.
     * @param listener a listener that button set its to itself.
     */
    public void setInfoButton(ImageButton infoButton, Listener listener) {
        infoButton.setOnClickListener(listener);
    }

    /**
     * @param switchCompat a button for playing and pausing the media.
     */
    public void setSwitchCompat(SwitchCompat switchCompat) {
        this.switchCompat = switchCompat;
    }

    /**
     * set switch to play style.
     */
    public void showPlayButton() {
        switchCompat.setChecked(true);
    }

    /**
     * set switch to pause style.
     */
    public void showPauseButton() {
        switchCompat.setChecked(false);
    }

    /**
     * Initialize the server connecting.
     * If server is connected, app play or not connect, show a alert and
     *  show refresh button and hide switch.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initialize() {
        counter += 2;
        if (connector.isChecker()) {
            mediaPlayer.start();
            showPlayButton();
        } else {
            System.err.println("We have a problem in play file");
            switchCompat.setVisibility(View.INVISIBLE);
            AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            alert.setMessage("کلاس هنوز شروع نشده، لطفا اندکی بعد تلاش کنید").show();
            refreshButton.setVisibility(View.VISIBLE);
        }
    }


    /**
     * Media player play and switch is true
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void play() {
        counter++;
        showPlayButton();
        mediaPlayer.start();
    }

    /**
     * Media player stop and switch is false
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void stop() {
        counter--;
        showPauseButton();
        mediaPlayer.pause();
    }

    /**
     * Checking for initialize or play or stop.
     */
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

    /**
     * Switch checks that should play or stop now.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void switchCheck() {
        if (switchCompat.isChecked()) {
            if (isChecked) {
                checkPlay();
            }
        }
    }

    /**
     * Intent to info page
     */
    public void showInfoPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(activity, InfoActivity.class);
                activity.startActivity(intent);
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
