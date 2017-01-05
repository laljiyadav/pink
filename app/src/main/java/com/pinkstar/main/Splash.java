package com.pinkstar.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.InternetStatus;
import com.pinkstar.main.data.SaveSharedPreference;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(5 * 1000);

                    // After 5 seconds redirect to another intent
                   // if (InternetStatus.isConnectingToInternet(Splash.this)) {
                        if (SaveSharedPreference.getUserID(Splash.this).equals("")) {
                            startActivity(new Intent(Splash.this, Mobile.class));
                            finish();
                        } else {
                            startActivity(new Intent(Splash.this, MainActivity.class));
                            finish();
                        }



                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();


    }


}
