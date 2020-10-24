package com.example.radiosafa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//TODO We must getting resources from mr.Dehghan
/*
public class PageOne extends AppCompatActivity {

    DrawerLayout drawerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_one);

        drawerlayout= findViewById(R.id.drowerlayout);

            }
    public void clickmenu(View view){
        odrower(drawerlayout);

    }

    public static void odrower(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }


    public void clickhome(View view){

        redirect(this,page_one.class);

    }
    public void clickinfo(View view){
        redirect(this,info.class);
    }
    public void click_online_music(View view){
        redirect(this,onlin_sound.class);
    }

    public static void redirect(Activity activity , Class aclass) {
        Intent intent =new Intent(activity,aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)){
            drawerlayout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }

}*/
