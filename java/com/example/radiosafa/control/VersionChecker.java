package com.example.radiosafa.control;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

public class VersionChecker {
    private final long old;
    private final long newInt;
    private final AppCompatActivity activity;

    public VersionChecker(long old, long newInt, AppCompatActivity activity, String uri) {
        this.old = old;
        this.newInt = newInt;
        this.activity = activity;
        versionChecker(uri);
    }

    private void versionChecker(final String uri) {
        if (old < newInt) {
            DialogInterface.OnClickListener cancelDialog = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            };

            DialogInterface.OnClickListener acceptDialog = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    activity.startActivity(browserIntent);
                }
            };

            AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            alert.setMessage("نسخه جدید نرم افزار موجود است")
                    .setCancelable(false)
                    .setTitle("یه خبر خوب!")
                    .setNegativeButton("باشه برای بعدا", cancelDialog)
                    .setPositiveButton("الان دانلود میکنم", acceptDialog)
                    .show();

        }
    }
}
