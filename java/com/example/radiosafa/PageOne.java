package com.example.radiosafa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.radiosafa.Activites.Main.OnlinePlayerLight;

//TODO We must getting resources from mr.Dehghan
public class PageOne extends AppCompatActivity {

    DrawerLayout drawerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dark2);

        drawerlayout = findViewById(R.id.drawer_layout_dark);

    }

    public void clickMenu(View view) {
        oDrawer(drawerlayout);

    }

    public static void oDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }


    public void clickHome(View view) {

        redirect(this, PageOne.class);

    }

    public void clickInfo(View view) {
        redirect(this, Info.class);
    }

    public void click_online_music(View view) {
        redirect(this, OnlinePlayerLight.class);
    }

    public static void redirect(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

}
