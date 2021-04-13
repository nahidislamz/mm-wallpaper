package com.dragonlfy.mmwallpaper;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dragonlfy.mmwallpaper.ui.common.Common;
import com.dragonlfy.mmwallpaper.ui.settings.SharedPref;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

         SharedPref sharedpref = new SharedPref(getApplicationContext());

        if(sharedpref.loadNightModeState()) {

            setTheme(R.style.darktheme);
        }
        else  {
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_splashscreen);

        if(Common.isConnectToInternet(getApplicationContext())){
            new Handler().postDelayed(() -> {

                startActivity(new Intent(SplashscreenActivity.this,HomeActivity.class));
                finish();
                },3000);
        }
        else {
            showNoInternet("You need active internet to use this app");
        }


    }

    private void showNoInternet(String s) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You don't have internet");
        builder.setMessage(s);
        builder.setIcon(R.drawable.no_internet24dp);
        builder.setPositiveButton("OK", (dialog, which) -> {
           new Handler().postDelayed(() -> finish(),1000);
        });

        builder.setCancelable(false);
        builder.show();
    }

}
