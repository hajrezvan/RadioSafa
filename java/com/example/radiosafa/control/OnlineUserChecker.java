package com.example.radiosafa.control;

import androidx.appcompat.app.AppCompatActivity;

import com.example.radiosafa.view.MyServices.Components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class OnlineUserChecker implements Runnable {

    private URL ipApi;
    private final Components components;
    private final AppCompatActivity activityCompat;
    private int numberOfMembers;
    private boolean isConnect;
    private boolean isPlay;
    private String url;

    public OnlineUserChecker(Components components, AppCompatActivity activity) {
        activityCompat = activity;
        numberOfMembers = 0;
        isConnect = false;
        this.components = components;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void initUrl() {
        try {
            ipApi = new URL(url);
            isConnect = true;
        } catch (MalformedURLException e) {
            System.err.println("We can not setting URL");
        }
    }

    public void setNumberOfMembers(String line) {
        String[] api = line.split(">");
        String[] numberSplit = api[3].split("<");
        numberOfMembers = (Integer.parseInt(numberSplit[0]));
    }

    public void setStatus(String line) {
        String[] api = line.split(">");
        String[] numberSplit = api[31].split("<");
        int status = (Integer.parseInt(numberSplit[0]));
        if (status != 0) {
            isPlay = true;
        } else {
            isPlay = false;
        }
    }

    public void refresh() {
        try {
            URLConnection c = ipApi.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
            String line = reader.readLine().trim();
            setNumberOfMembers(line);
            setStatus(line);
            isConnect = true;
        } catch (IOException e) {
            isConnect = false;
            numberOfMembers = 0;
        } catch (Exception e) {
            System.out.println("I don't know!!");
        }
    }

    public String text() {
        return "" + (numberOfMembers);
    }

    public void show() {
        try {
            while (isConnect) {
                activityCompat.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        components.textChange();
                    }
                });
                refresh();
                Thread.sleep(4000);
            }
        } catch (InterruptedException e) {
            System.out.println("We have an error in refreshing");
        } catch (Exception e) {
            System.out.println("We have a problem");
            refresh();
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public synchronized void run() {
        if (url != null) {
            System.out.println(url);
            initUrl();
            refresh();
            show();
        }
    }
}