package com.example.radiosafa;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivityDark extends AppCompatActivity {

    private SwitchCompat switchCompat;
    private ImageButton refreshButton;
    private MediaPlayer mediaPlayer;
    private Connector connector;
    private Spinner spinner;
    private boolean isChecked;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.MainActivityDark);
        connector = new Connector();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(connector);
        isChecked = false;
        setupViews();
    }

    public void setupViews() {
        MainActivityDark.Listener listener = new MainActivityDark.Listener();
        SpinnerListener spinnerListener = new SpinnerListener();
        setInfoButton((ImageButton) findViewById(R.id.info_btn_id), listener);
        setRefreshButton((ImageButton) findViewById(R.id.refresh_btn_id), listener);
        setSwitchCompat((SwitchCompat) findViewById(R.id.switch_play_pause2));
        setSpinner((Spinner) findViewById(R.id.them_setting));
        getSpinner().setOnItemSelectedListener(spinnerListener);

        switchCompat.setOnCheckedChangeListener(listener);

        mediaPlayer = connector.getMediaPlayer();

        findViewById(R.id.refresh_btn_id).setVisibility(View.GONE);
    }

    public void setRefreshButton(ImageButton refreshButton, MainActivityDark.Listener listener) {
        this.refreshButton = refreshButton;
        this.refreshButton.setOnClickListener(listener);
    }

    public void setInfoButton(ImageButton infoButton, MainActivityDark.Listener listener) {
        infoButton.setOnClickListener(listener);
    }

    public void setSwitchCompat(SwitchCompat switchCompat) {
        this.switchCompat = switchCompat;
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public void setSpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.thems,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        this.spinner = spinner;
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
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivityDark.this);
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
                Intent intent = new Intent(MainActivityDark.this, InfoActivity.class);
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

    private class SpinnerListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String themSelected = adapterView.getItemAtPosition(i).toString();
            if ("روشن".equals(themSelected)) {
                //TODO go to MainActivity for light
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivityDark.this, MainActivity.class);
                        startActivity(intent);
                    }
                }, 0);
                finish();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}