package com.example.radiosafa.MainActivityComponents;

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

import com.example.radiosafa.InfoActivity;
import com.example.radiosafa.MyServices.Connector;
import com.example.radiosafa.R;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Components {
    private AppCompatActivity activity;
    private SwitchCompat switchCompat;
    private ImageButton refreshButton;
    private MediaPlayer mediaPlayer;
    private Connector connector;
    private boolean isChecked;
    private int counter = 0;

    public Components(AppCompatActivity activity) {
        this.activity = activity;
        setup();
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    public void setup() {
        connector = new Connector();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(connector);
        isChecked = false;
        setupViews();
    }

    public void setupViews() {
        Listener listener = new Listener();
        setInfoButton((ImageButton) activity.findViewById(R.id.info_btn_id), listener);
        setRefreshButton((ImageButton) activity.findViewById(R.id.refresh_btn_id), listener);
        setSwitchCompat((SwitchCompat) activity.findViewById(R.id.switch_play_pause2));
        switchCompat.setOnCheckedChangeListener(listener);

        mediaPlayer = connector.getMediaPlayer();

        activity.findViewById(R.id.refresh_btn_id).setVisibility(View.GONE);
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
            showPlayButton();
            System.out.println("*******************************************");
            System.out.println(Arrays.toString(mediaPlayer.getTrackInfo()));
            System.out.println("*******************************************");
        } else {
            System.err.println("We have a problem in play file");
            switchCompat.setVisibility(View.INVISIBLE);
            AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            alert.setMessage("کلاس هنوز شروع نشده، لطفا اندکی بعد تلاش کنید").show();
            refreshButton.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void play() {
        counter++;
        showPlayButton();
        mediaPlayer.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void stop() {
        counter--;
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
