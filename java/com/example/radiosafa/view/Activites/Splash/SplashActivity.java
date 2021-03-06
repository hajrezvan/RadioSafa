package com.example.radiosafa.view.Activites.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.radiosafa.view.Activites.Main.OnlinePlayerLight;
import com.example.radiosafa.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Create a object from inner class.
        MyHandler handler = new MyHandler();
        new Handler().postDelayed(handler, 3000);
    }

    private class MyHandler implements Runnable {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, OnlinePlayerLight.class);
            startActivity(intent);
            finish();
        }
    }
}