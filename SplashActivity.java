package com.example.radiosafa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Create a object from inner class.
        MyHandler handler = new MyHandler();
        new Handler().postDelayed(handler,4000);
    }

    private class MyHandler implements Runnable {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, page_one.class);
            startActivity(intent);
            finish();
        }
    }

}