package com.example.radiosafa.MyServices;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

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
    private OnlineUserChecker userChecker;
    private TextView textView;
    private boolean isChecked;
    //if true, activity is dark them
    private boolean isDark;
    //    private boolean isPlay;
    private int counter = 0;

    /**
     * Initialize the fields and setting them.
     *
     * @param activity is a activity that should be setup components on its.
     */
    public Components(AppCompatActivity activity, boolean isDark) {
        this.activity = activity;
        this.isDark = isDark;
    }

    /**
     * Initialize connector and create a executor service for checking server connecting.
     * Than setting components.
     */
    public void setup() {
        connector = new Connector();
        setupViews();
        userChecker = new OnlineUserChecker(this);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(connector);
        executorService.execute(userChecker);
        isChecked = false;
    }

    /**
     * setting components and their listeners.
     */
    public void setupViews() {
        Listener listener = new Listener();
        if (isDark) {
            setSwitchCompat((SwitchCompat) activity.findViewById(R.id.switch_play_pause_dark));
            setRefreshButton((ImageButton) activity.findViewById(R.id.refresh_btn_dark_id), listener);
            setTextView((TextView) activity.findViewById(R.id.online_user_dark_id));
        } else {
            setSwitchCompat((SwitchCompat) activity.findViewById(R.id.switch_play_pause_light));
            setRefreshButton((ImageButton) activity.findViewById(R.id.refresh_btn_light_id), listener);
            setTextView((TextView) activity.findViewById(R.id.online_user_light_id));
        }
        setInfoButton((ImageButton) activity.findViewById(R.id.info_btn_id), listener);

        switchCompat.setOnCheckedChangeListener(listener);

        textView.setVisibility(View.GONE);
        mediaPlayer = connector.getMediaPlayer();

        if (isDark) {
            activity.findViewById(R.id.refresh_btn_dark_id).setVisibility(View.GONE);
        } else {
            activity.findViewById(R.id.refresh_btn_light_id).setVisibility(View.GONE);
        }
    }

    public void setText(String text) {
        textView.setText(text);
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

    @SuppressLint("SetTextI18n")
    public void showOnlineUser() {
        textView.setText(userChecker.text());
        textView.setVisibility(View.VISIBLE);
    }

    /**
     * Initialize the server connecting.
     * If server is connected, app play or not connect, show a alert and
     * show refresh button and hide switch.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initialize() {
        counter += 2;
        if (connector.isChecker() && userChecker.isConnect()) {
            showOnlineUser();
            mediaPlayer.start();
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
            alert.setMessage("کلاس هنوز شروع نشده، لطفا اندکی بعد تلاش کنید")
                    .setCancelable(false)
                    .setTitle("متأسفم").setPositiveButton("متوجه شدم", listener)
                    .show();
        }
    }

    /**
     * Media player play and switch is true
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void play() {
        counter++;
        showOnlineUser();
        showPlayButton();
        mediaPlayer.start();
    }

    /**
     * Media player stop and switch is false
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void stop() {
        counter--;
        textView.setVisibility(View.GONE);
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
        } else if (counter == 1) {
            play();
        }
    }

    /**
     * Switch checks that should play or stop now.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                case R.id.refresh_btn_light_id:
                case R.id.refresh_btn_dark_id:
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
