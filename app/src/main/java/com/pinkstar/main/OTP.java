package com.pinkstar.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class OTP extends Activity {
    private EditText otp_resend;
    private String status;
    private TextView otp_submit, txt_num;
    JSONObject json;
    String udata, url = Apis.Base;
    int chance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        inIt();
    }

    public void inIt() {
        otp_resend = (EditText) findViewById(R.id.ed_otp);
        otp_submit = (TextView) findViewById(R.id.otp_resend);
        txt_num = (TextView) findViewById(R.id.txt_num);

        txt_num.setText(SaveSharedPreference.getMobile(OTP.this));


        otp_resend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (otp_resend.getText().length() < 4) {

                } else if (otp_resend.getText().length() == 4) {

                    if (otp_resend.getText().toString().equals(SaveSharedPreference.getUSERSSN(OTP.this)))

                    {
                        //new AttempLogin().execute();
                        startActivity(new Intent(OTP.this, Register.class));
                    } else {
                        Dialogs.showDialog(OTP.this, "OTP incorrect");
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        otp_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                chance++;
                if (chance <= 1) {
                    new AttempLogin().execute();
                } else {

                }


            }
        });
    }

    private class AttempLogin extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(OTP.this, "Verify...");
        }

        @Override
        protected String doInBackground(Void... params) {


            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(OTP.this)));
                nameValuePairs.add(new BasicNameValuePair("rquest", "checkMobile"));
                nameValuePairs.add(new BasicNameValuePair("api_token", Apis.Api_Token));

                Log.e("json", "" + nameValuePairs.toString());

                // Create an array
                Parser perser = new Parser();
                json = perser.getJSONFromUrl(url, nameValuePairs);
                Log.e("json", "" + json);


                udata = json.getString("uData");

                if (udata.equals("1")) {
                    if (json.has("otp")) {
                        SaveSharedPreference.setUSERSSN(OTP.this, json.getString("otp"));
                    }
                }
                if (udata.equals("17")) {
                    if (json.has("otp")) {
                        SaveSharedPreference.setUSERSSN(OTP.this, json.getString("otp"));
                    }
                }
                if (udata.equals("5")) {
                    if (json.has("otp")) {
                        SaveSharedPreference.setUSERSSN(OTP.this, json.getString("otp"));
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

            if (udata.equals("1")) {
                Dialogs.showCenterToast(OTP.this, "OTP has been send on you mobile");
            }


        }
    }


    @Override
    public void onBackPressed() {

    }
}
