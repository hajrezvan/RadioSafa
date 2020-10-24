package com.example.radiosafa.Activites.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.radiosafa.MyServices.Components;
import com.example.radiosafa.R;

public class MainActivityDark extends AppCompatActivity {

    /**
     * When program starts, view begin from this method.
     * This method initialises field and set them in activity.
     *
     * @param savedInstanceState pass to onCreate method in its super class.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dark2);
        Components components = new Components(this,true);
        components.setup();
    }
}