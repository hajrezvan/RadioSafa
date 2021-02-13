package com.example.radiosafa.control;

import android.content.Context;

import android.app.AlertDialog.Builder;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

public class ExoPlayerConnector implements Runnable {

    private final SimpleExoPlayer simpleExoPlayer;
    private boolean isChecker;
    private final Context activity;
    private String url;

    public ExoPlayerConnector(Context activity) {
        this.activity = activity;
        simpleExoPlayer = new SimpleExoPlayer.Builder(activity).build();
    }

    private void initializer() {
        try {
            simpleExoPlayer.prepare();
            isChecker = true;
        } catch (Exception exception) {
            new Builder(activity)
                    .setTitle("Error!")
                    .setMessage("I'm so sorry!!\nWe have a problem in connecting to server...")
                    .setCancelable(true)
                    .show();
            exception.printStackTrace();
        }
    }

    public boolean isChecker() {
        return isChecker;
    }

    public void play() {
        try {
            simpleExoPlayer.play();
        } catch (Exception exception) {
            new Builder(activity)
                    .setTitle("Error!")
                    .setMessage("I'm so sorry!!")
                    .setCancelable(true)
                    .show();
            exception.printStackTrace();
        }
    }

    public void stop() {
        try {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        } catch (Exception exception) {
            new Builder(activity)
                    .setTitle("Error!")
                    .setMessage("I'm so sorry!!")
                    .setCancelable(true)
                    .show();
            exception.printStackTrace();
        }
    }

    public void pause() {
        try {
            simpleExoPlayer.pause();
        } catch (Exception exception) {
            new Builder(activity)
                    .setTitle("Error!")
                    .setMessage("I'm so sorry!!")
                    .setCancelable(true)
                    .show();
            exception.printStackTrace();
        }
    }

    public void refresh() {
        initializer();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        if (url != null) {
            setMediaItem();
            initializer();
        }
        isChecker = false;
    }

    private synchronized void setMediaItem() {
        try {
            MediaItem mediaItem = MediaItem.fromUri(url);
            simpleExoPlayer.setThrowsWhenUsingWrongThread(false);
            simpleExoPlayer.setMediaItem(mediaItem);
        } catch (IllegalStateException exception) {
            System.err.println("Player is accessed on the wrong thread.");
        }
    }
}
