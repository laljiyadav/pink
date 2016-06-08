package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.GPSTracker;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

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
        forgot = (TextView) findViewById(R.id.txt_forgot);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Password.this, Reset_Password.class));
            }
        });

        gpsTracker = new GPSTracker(Password.this);
        Location location = gpsTracker.getLocation();

        if (location == null) {
            Dialogs.alertDialog(Password.this,"Allow &#34;Pink Star&#34; to access your location while you use this app?", "Location is required", "Don't Allow", "Allow");
        }
        else {

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
            StringBuilder strBuilder = new StringBuilder(url);
            strBuilder.append("password=" + pass);
            strBuilder.append("&rquest=email_signup");
            strBuilder.append("&mobile=" + SaveSharedPreference.getMobile(Password.this));


            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl1(strBuilder.toString());
            try {

                udata = json.getString("udata");
                JSONArray jsaary = json.getJSONArray("result");
                if (udata.equals("1")) {
                    for (int i = 0; i < jsaary.length(); i++) {
                        JSONObject js = jsaary.getJSONObject(i);
                        SaveSharedPreference.setMobile(Password.this, "+"+js.getString("std_code") + "-" + js.getString("mobile"));
                        SaveSharedPreference.setUserID(Password.this, js.getString("token_id"));
                        SaveSharedPreference.setUSERSSN(Password.this, js.getString("otp"));

                    }


                }


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
                } else if (udata.equals("0")) {
                    Dialogs.showDialog(Password.this, "" + json.getString("result"));
                } else {
                    Dialogs.showDialog(Password.this, "Server Failed");
                }
            } catch (Exception e) {

            }

        }
    }
}
