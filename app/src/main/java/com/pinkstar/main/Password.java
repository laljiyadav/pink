package com.pinkstar.main;

import android.*;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.GPSTracker;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Password extends Activity {
    Button pas_submit;
    EditText ed_pass;
    String pass, udata, url = Apis.Base;
    JSONObject json;
    TextView forgot;
    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        inIt();


    }

    public void inIt() {
        forgot = (TextView) findViewById(R.id.txt_forgot);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Password.this, Reset_Password.class));
            }
        });


        if (ContextCompat.checkSelfPermission(Password.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(Password.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            gpsTracker = new GPSTracker(Password.this);
            Location location = gpsTracker.getLocation();

            if (location == null) {
                Dialogs.alertDialog(Password.this, "Allow &#34;Pink Star&#34; to access your location while you use this app?", "Location is required", "Don't Allow", "Allow");
            }
        } else {

            Toast.makeText(Password.this, "not", Toast.LENGTH_SHORT).show();

        }

        pas_submit = (Button) findViewById(R.id.btnpass);
        ed_pass = (EditText) findViewById(R.id.ed_pass);
        pas_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = ed_pass.getText().toString();

                if (pass.equals("")) {
                    ed_pass.setError("Enter Password");
                } else {
                    new AttempLogin().execute();
                }


            }
        });
    }


    private class AttempLogin extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(Password.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            // http://pinkstarapp.com/api/restservices.php?email=%@&password=%@&rquest=email_signup
            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
            strBuilder.add(new BasicNameValuePair("password", pass));
            strBuilder.add(new BasicNameValuePair("rquest", "userLogin"));
            strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(Password.this)));
            strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));

            Log.e("Log_tag", "" + strBuilder.toString());

            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl(url, strBuilder);

            try {

                udata = json.getString("uData");

            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            Dialogs.dismissDialog();
            try {

                if (udata.equals("1")) {
                    startActivity(new Intent(Password.this, MainActivity.class));
                    SaveSharedPreference.setUserID(Password.this, json.getJSONObject("result").getString("id"));
                    SaveSharedPreference.setUSERAuth(Password.this, json.getString("token_id"));
                } else if (udata.equals("13")) {
                    Dialogs.showDialog(Password.this, "" + json.getJSONObject("result").getString("message"));
                } else if (udata.equals("14")) {
                    Dialogs.showDialog(Password.this, "" + json.getJSONObject("result").getString("message"));
                } else if (udata.equals("4")) {
                    Dialogs.showDialog(Password.this, "" + json.getJSONObject("result").getString("message"));
                } else {
                    Dialogs.showDialog(Password.this, "Server Failed");
                }
            } catch (Exception e) {

            }

        }
    }
}
