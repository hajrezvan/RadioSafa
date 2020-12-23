package com.example.radiosafa.MyServices;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class OnlineUserChecker implements Runnable {

    private URL ipApi;
    private Components components;
    private AppCompatActivity activityCompat;
    private int numberOfMembers;
    private boolean isConnect;

    public OnlineUserChecker(Components components, AppCompatActivity activity) {
        activityCompat = activity;
        numberOfMembers = 0;
        isConnect = false;
        this.components = components;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void initUrl() {
        try {
            ipApi = new URL("https://radio.masjedsafa.com/stats?sid=2");
            isConnect = true;
        } catch (MalformedURLException e) {
            System.err.println("We can not setting URL");
        }
    }

    public void refresh() {
        try {
            URLConnection c = ipApi.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
            String line = reader.readLine().trim();
            String[] api = line.split(">");
            String[] numberSplit = api[3].split("<");
            numberOfMembers = (Integer.parseInt(numberSplit[0]));
            isConnect = true;
        } catch (IOException e) {
            isConnect = false;
            numberOfMembers = 0;
        } catch (Exception e) {
            System.out.println("I don't know!!");
        }
    }

    public String text() {
        return "تعداد افراد حاضر در کلاس: " + (numberOfMembers);
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

    @Override
    public void run() {
        initUrl();
        refresh();
        show();
    }
}