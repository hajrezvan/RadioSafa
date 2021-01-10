package com.example.radiosafa.view.Activites.Info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.radiosafa.R;

public class InfoActivity extends AppCompatActivity {

    private static final String TEXT = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        TextView textView = findViewById(R.id.about_us);
        SharedPreferences sharedPreferences = getSharedPreferences(TEXT, Context.MODE_PRIVATE);
        String text = sharedPreferences.getString("TEXT", null);
        StringBuilder stringBuilder = new StringBuilder(text);
        stringBuilder.deleteCharAt(0);
        stringBuilder.deleteCharAt(text.length() - 2);
        stringBuilder.deleteCharAt(text.length() - 3);

        textView.setText(stringBuilder.toString());
    }
}