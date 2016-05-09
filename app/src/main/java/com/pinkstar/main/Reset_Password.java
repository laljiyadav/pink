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

import org.json.JSONObject;

public class Reset_Password extends Activity {
    private EditText et_reset_password;
    Button reset;
    String mobile, udata, url = Apis.Base;
    JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset__password);
        et_reset_password = (EditText) findViewById(R.id.et_reset_password);
        reset = (Button) findViewById(R.id.btn_reset);

        et_reset_password.setText(SaveSharedPreference.getMobile(Reset_Password.this));

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile = et_reset_password.getText().toString();

                if (mobile.equals("")) {
                    et_reset_password.setError("Enter Password");
                } else {
                    new AttempLogin().execute();
                }
            }
        });

        et_reset_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mobile = et_reset_password.getText().toString();
                int lenth=mobile.length();
                //Toast.makeText(getApplicationContext(), ""+lenth, Toast.LENGTH_SHORT).show();
                char val=mobile.charAt(lenth-1);
                if(String.valueOf(val).equals("-"))
                {
                    Dialogs.showDialog(Reset_Password.this, "Std code can't be edit");
                }

                if(!mobile.contains("-"))
                {
                    et_reset_password.setText(mobile+"-");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

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

            StringBuilder strBuilder = new StringBuilder(url);
            strBuilder.append("mobile=" + mobile);
            strBuilder.append("&rquest=forget_password");

            Log.e("Log_tag", "" + strBuilder.toString());

            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl1(strBuilder.toString());
            Log.e("Log_tag", "" + json);
            try {

                udata = json.getString("udata");


            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            Dialogs.dismissDialog();

            if (udata.equals("1")) {
                startActivity(new Intent(Reset_Password.this, Password.class));
            } else {
                Dialogs.showDialog(Reset_Password.this, "Server Failed");
            }


        }
    }
}
