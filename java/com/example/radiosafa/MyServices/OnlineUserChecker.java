package com.example.radiosafa.MyServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class OnlineUserChecker implements Runnable {

    private URL ipApi;
    private int numberOfMembers;
    private boolean isConnect;

    public OnlineUserChecker() {
        numberOfMembers = 0;
        isConnect = false;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public int getNumberOfMembers() {
        return numberOfMembers;
    }

    public void setNumberOfMembers(int numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    public void initUrl() {
        try {
            ipApi = new URL("https://radio.masjedsafa.com/stats?sid=2");

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
            setNumberOfMembers(Integer.parseInt(numberSplit[0]));
            setConnect(true);
        } catch (IOException e) {
            setConnect(false);
            setNumberOfMembers(0);
            System.err.println("We have a problem in connecting");
        }
    }

    @Override
    public void run() {
        initUrl();
        refresh();
    }
}