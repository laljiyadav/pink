package com.pinkstar.main;

import android.app.Activity;
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

import org.json.JSONArray;
import org.json.JSONObject;

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
                        new AttempLogin().execute();
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
                    if(chance<=2) {
                        new Attemp().execute();
                    }
                    else
                    {

                    }




            }
        });

    }

    private class Attemp extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           Dialogs.showProDialog(OTP.this,"Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {


            StringBuilder strBuilder = new StringBuilder(url);
            strBuilder.append("mobile=" +SaveSharedPreference.getMobile(OTP.this));
            strBuilder.append("&resend=" + 0);
            strBuilder.append("&rquest=validateNum");

            Log.e("Log_tag", "" + strBuilder.toString());

            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl1(strBuilder.toString());
            Log.e("Log_tag", "" + json);
            try {

                udata = json.getString("udata");
                JSONArray jsaary = json.getJSONArray("result");
                if (udata.equals("0")) {
                    for (int i = 0; i < jsaary.length(); i++) {
                        JSONObject js = jsaary.getJSONObject(i);
                        SaveSharedPreference.setUSERSSN(OTP.this, js.getString("otp"));

                    }


                }

                if (udata.equals("1")) {
                    for (int i = 0; i < jsaary.length(); i++) {
                        JSONObject js = jsaary.getJSONObject(i);
                        SaveSharedPreference.setUSERSSN(OTP.this, js.getString("otp"));

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
        }

    }


    private class AttempLogin extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(OTP.this, "Verify...");
        }

        @Override
        protected String doInBackground(Void... params) {


            StringBuilder strBuilder = new StringBuilder(url);
            strBuilder.append("rquest=optVerified");
            strBuilder.append("&mobile=" + SaveSharedPreference.getMobile(OTP.this));
            strBuilder.append("&token_id=" + SaveSharedPreference.getUserID(OTP.this));

            Log.e("Log_tag", "" + strBuilder.toString());

            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl1(strBuilder.toString());
            Log.e("Log_tag", "" + json);
            try {

                udata = json.getString("udata");

                if (udata.equals("1")) {
                    JSONObject js = json.getJSONObject("result");
                    status = js.getString("status");
                }


            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            Dialogs.dismissDialog();

            if (status.equals("Success")) {
                startActivity(new Intent(OTP.this, Register.class));
            } else {
                Dialogs.showDialog(OTP.this, "OPT Incorrect");
            }


        }
    }


}
