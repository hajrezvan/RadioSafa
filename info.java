package com.example.radiosafa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class info extends AppCompatActivity {
    DrawerLayout drawerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info2);
        drawerlayout= findViewById(R.id.drowerlayout);
    }

    public void clickmenu(View view) {
        //issue tab menu picture and logout at program is this section
        PageOne.odrower(drawerlayout);
    }
    public void clickinfo(View view){
        PageOne.redirect(this,info.class);
    }
    public void click_online_music(View view){

        PageOne.redirect(this,MainActivity.class);
    }
    public void clickhome(View view){

        PageOne.redirect(this,PageOne.class);
    }


    @Override
    public void onBackPressed() {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)){
            drawerlayout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }

}
