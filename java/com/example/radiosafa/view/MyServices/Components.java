package com.example.radiosafa.view.MyServices;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.radiosafa.control.ExoPlayerConnector;
import com.example.radiosafa.control.OnlineUserChecker;
import com.example.radiosafa.control.VersionChecker;
import com.example.radiosafa.model.DataCenter;
import com.example.radiosafa.view.Activites.Info.InfoActivity;
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

    private final AppCompatActivity activity;
    private SwitchCompat switchCompat;
    private ImageButton refreshButton;
    private ImageView headphone;
    private ImageView liveOn;
    private ImageView liveOff;
    private ExoPlayerConnector exoPlayerConnector;
    private OnlineUserChecker userChecker;
    private TextView textView;
    private DataCenter dataCenter;

    private static final String TEXT = "text";
    private String aboutUs;

    private boolean isChecked;
    private int counter = 0;

    /**
     * Initialize the fields and setting them.
     *
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
        dataCenter = new DataCenter(this);

        Thread data = new Thread(dataCenter);
        data.start();

        ExecutorService executorService = Executors.newCachedThreadPool();

        String exoplayerUrl = dataCenter.getOnlinePlayer();
        String listenerChecker = dataCenter.getOnlineListener();

        System.out.println(exoplayerUrl);
        System.out.println(listenerChecker);

        exoPlayerConnector = new ExoPlayerConnector(activity);

        setupViews();
        boolean isConnect;

        userChecker = new OnlineUserChecker(this, activity);

        isConnect = dataCenter.isConnect();

        if (isConnect) {
            exoPlayerConnector.setUrl(exoplayerUrl);
            userChecker.setUrl(listenerChecker);
            System.out.println(exoplayerUrl);
            System.out.println(listenerChecker);

            executorService.execute(userChecker);
            executorService.execute(exoPlayerConnector);
        }
        isChecked = false;
    }

    public void setExoplayerUrl(String exoplayerUrl) {
        exoPlayerConnector.setUrl(exoplayerUrl);
        if (!exoPlayerConnector.isChecker()) {
            exoPlayerConnector.run();
        }
    }

    public void setListenerChecker(String listenerChecker) {
        userChecker.setUrl(listenerChecker);
        if (!userChecker.isConnect()) {
            userChecker.run();
        }
    }

    /**
     * setting components and their listeners.
     */
    public void setupViews() {
        Listener listener = new Listener();
        setSwitchCompat((SwitchCompat) activity.findViewById(R.id.switch_play_pause_light));
        setRefreshButton((ImageButton) activity.findViewById(R.id.refresh_btn_light_id), listener);
        setTextView((TextView) activity.findViewById(R.id.online_user_light_id));
        setInfoButton((ImageButton) activity.findViewById(R.id.info_btn_id), listener);

        headphone = activity.findViewById(R.id.headphone_id);
        liveOff = activity.findViewById(R.id.live_off);
        liveOn = activity.findViewById(R.id.live_on);

        headphone.setVisibility(View.GONE);
        liveOn.setVisibility(View.GONE);

        switchCompat.setOnCheckedChangeListener(listener);

        textView.setVisibility(View.GONE);
//        mediaPlayer = mediaPlayerConnector.getMediaPlayer();
        activity.findViewById(R.id.refresh_btn_light_id).setVisibility(View.GONE);
    }

    /**
     * @param refreshButton a button for refreshing server connecting.
     * @param listener      a listener that button set its to itself.
     */
    public void setRefreshButton(ImageButton refreshButton, Listener listener) {
        this.refreshButton = refreshButton;
        this.refreshButton.setOnClickListener(listener);
    }

    /**
     * @param infoButton a button for showing info page.
     * @param listener   a listener that button set its to itself.
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

    public void setTextView(TextView textView) {
        this.textView = textView;
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

    public void textChange() {
        String i = userChecker.text();
        System.out.println(i);
        showOnlineUser();
    }

    @SuppressLint("SetTextI18n")
    public void showOnlineUser() {
        textView.setText(userChecker.text());
    }

    private void offline() {
        liveOn.setVisibility(View.GONE);
        headphone.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);

        liveOff.setVisibility(View.VISIBLE);
    }

    private void online() {
        liveOn.setVisibility(View.VISIBLE);
        headphone.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);

        liveOff.setVisibility(View.GONE);
    }

    /**
     * Initialize the server connecting.
     * If server is connected, app play or not connect, show a alert and
     * show refresh button and hide switch.
     */

    public void initialize() {
        if (userChecker.isPlay()) {
            counter += 2;
            showOnlineUser();
            online();
            exoPlayerConnector.play();
            refreshButton.setVisibility(View.GONE);
            switchCompat.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
            switchCompat.setVisibility(View.GONE);
            refreshButton.setVisibility(View.VISIBLE);

            //Show alert.
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            };
            AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            alert.setMessage("برنامه هنوز شروع نشده، لطفا اندکی بعد تلاش کنید")
                    .setCancelable(false)
                    .setTitle("متأسفم").setPositiveButton("متوجه شدم", listener)
                    .show();
        }
        VersionChecker versionChecker = new VersionChecker(dataCenter.getNowVersion(), dataCenter.getNewVersion(), activity, dataCenter.getLink());
    }

    /**
     * Media player play and switch is true
     */

    public void play() {
        counter++;
        showOnlineUser();
        showPlayButton();
        online();
        exoPlayerConnector.play();
    }

    /**
     * Media player stop and switch is false
     */

    public void stop() {
        counter--;
        offline();
        showPauseButton();
        exoPlayerConnector.pause();
    }

    /**
     * Checking for initialize or play or stop.
     */

    public void checkPlay() {
        if (counter == 0) {
            initialize();
        } else if (counter == 1) {
            play();
        }
    }

    public void switchCheck() {
        if (isChecked) {
            checkPlay();
        } else {
            stop();
        }
    }

    /**
     * Intent to info page
     */
    public void showInfoPage() {
        aboutUs = dataCenter.getAboutUs();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = activity.getSharedPreferences(TEXT, Context.MODE_PRIVATE);
                @SuppressLint
                        ("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("TEXT", aboutUs);
                editor.apply();
                Intent intent = new Intent(activity, InfoActivity.class);
                activity.startActivity(intent);
            }
        }, 0);
    }

    private class Listener implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            int viewID = view.getId();

            switch (viewID) {
                case R.id.info_btn_id:
                    showInfoPage();
                    break;
                case R.id.refresh_btn_light_id:
                    if (exoPlayerConnector.isChecker()) {
                        exoPlayerConnector.stop();
                    }
                    exoPlayerConnector.refresh();
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
