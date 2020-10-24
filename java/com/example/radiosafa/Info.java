package com.example.radiosafa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.radiosafa.Activites.Main.MainActivity;

//TODO We must getting resources from mr.Dehghan
public class Info extends AppCompatActivity {
    DrawerLayout drawerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info2);
        drawerlayout= findViewById(R.id.drowerlayout);
    }

    public void clickMenu(View view) {
        //issue tab menu picture and logout at program is this section
        PageOne.odrower(drawerlayout);
    }
    public void clickInfo(View view){
        PageOne.redirect(this,Info.class);
    }
    public void clickOnlinePlayer(View view){

        PageOne.redirect(this, MainActivity.class);
    }
    public void clickHome(View view){

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
