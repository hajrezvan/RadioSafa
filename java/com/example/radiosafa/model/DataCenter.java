package com.example.radiosafa.model;

import com.example.radiosafa.BuildConfig;
import com.example.radiosafa.view.MyServices.Components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DataCenter implements Runnable {

    private String onlinePlayer;
    private String onlineListener;
    //TODO add this filed to info page.
    private String aboutUs;
    //TODO add dialog to main page
    private String link;
    private URL url;

    private final Components components;

    private boolean connect;

    private final long nowVersion;
    private long newVersion;

    public DataCenter(Components components) {
        this.components = components;
        connect = false;

        nowVersion = BuildConfig.VERSION_CODE;

        aboutUs = null;
        onlineListener = null;
        onlinePlayer = null;
        link = null;
    }

    @Override
    public void run() {
        initializer();
        connector();
    }

    private void initializer() {
        try {
            url = new URL("https://id-iran.com/radio/radio-metadata.json");
            connect = true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void connector() {
        try {
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            final String[] input = new String[7];
            for (int i = 0; i < 7; i++) {
                input[i] = reader.readLine().trim();
            }
            for (int i = 0; i < 7; i++) {
                System.out.println(input[i]);
            }

            newVersion = version(input[1]);
            onlinePlayer = setOnlinePlayer(input[2]);
            onlineListener = setOnlineListener(input[3]);
            aboutUs = setAboutUs(input[4]);
            link = setLink(input[5]);

            System.out.println("\nMedia source: " + onlineListener);
            System.out.println("Listener source: " + onlinePlayer);

            System.out.println("Now version: " + nowVersion);
            System.out.println("Last version: " + newVersion);

            System.out.println("Download link: " + link);

            components.setExoplayerUrl(onlinePlayer);
            components.setListenerChecker(onlineListener);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getOnlinePlayer() {
        return onlinePlayer;
    }

    public String getOnlineListener() {
        return onlineListener;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public String getLink() {
        return link;
    }

    public long getNowVersion() {
        return nowVersion;
    }

    public long getNewVersion() {
        return newVersion;
    }

    public boolean isConnect() {
        return connect;
    }

    private String setAboutUs(String input) {
        String[] array = input.split(" : ");
        return array[1];
    }

    private long version(String input) {
        String[] array = input.split(" : ");
        StringBuilder stringBuilder = new StringBuilder(array[1]);
        stringBuilder.deleteCharAt(1);
        return Long.parseLong(stringBuilder.toString());
    }

    private String setOnlinePlayer(String input) {
        String[] array = input.split(" : ");
        StringBuilder stringBuilder = new StringBuilder(array[1]);
        stringBuilder.deleteCharAt(0);
        stringBuilder.deleteCharAt(55);
        stringBuilder.deleteCharAt(54);
        return stringBuilder.toString();
    }

    private String setOnlineListener(String input) {
        String[] array = input.split(" : ");
        StringBuilder stringBuilder = new StringBuilder(array[1]);
        stringBuilder.deleteCharAt(0);
        stringBuilder.deleteCharAt(41);
        stringBuilder.deleteCharAt(40);
        return stringBuilder.toString();
    }

    private String setLink(String input) {
        String[] array = input.split(" : ");
        StringBuilder stringBuilder = new StringBuilder(array[1]);
        stringBuilder.deleteCharAt(0);
        stringBuilder.deleteCharAt(40);
        return stringBuilder.toString();
    }
}
