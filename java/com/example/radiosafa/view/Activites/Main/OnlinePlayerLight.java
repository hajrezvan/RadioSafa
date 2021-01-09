package com.example.radiosafa.view.Activites.Main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.radiosafa.view.MyServices.Components;
import com.example.radiosafa.R;

/**
 * OnlinePlayer have a online music player and work with a URL from its server.
 *
 * @author Haj Rezvan
 * @version 2.5
 */
public class OnlinePlayerLight extends AppCompatActivity {

    /**
     * When program starts, view begin from this method.
     * This method initialises field and set them in activity.
     *
     * @param savedInstanceState pass to onCreate method in its super class.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        Components components = new Components(this);
        components.setup();
    }
}