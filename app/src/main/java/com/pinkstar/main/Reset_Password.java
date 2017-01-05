package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class Reset_Password extends Activity {
    private EditText et_reset_password;
    Button reset;
    String mobile, udata, url = Apis.Base;
    final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset__password);

        inIt();

    }


    public void inIt() {
        et_reset_password = (EditText) findViewById(R.id.et_reset_password);
        reset = (Button) findViewById(R.id.btn_reset);

        et_reset_password.setText(SaveSharedPreference.getMobile(Reset_Password.this));
        et_reset_password.setEnabled(false);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile = et_reset_password.getText().toString();

                if (mobile.equals("")) {
                    et_reset_password.setError("Enter Mobile");
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

            Dialogs.showProDialog(Reset_Password.this, "Wait...");
        }

        // http://pinkstarapp.com/api/restservices.php?mobile=%@&rquest=forget_password
        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
            strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(Reset_Password.this)));
            strBuilder.add(new BasicNameValuePair("rquest", "forgetPassword"));
            strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));


            Log.e("Log_tag", "" + strBuilder.toString());

            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl(url, strBuilder);
            Log.e("Log_tag", "" + json);
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

            if (udata.equals("1")) {
                Dialogs.showCenterToast(Reset_Password.this, "Password send on your mobile");
            } else {
                Dialogs.showDialog(Reset_Password.this, "Server Failed");
            }


        }
    }
}
