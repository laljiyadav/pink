package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.json.JSONObject;

public class Register extends Activity {
    private EditText et_email, et_password, et_name, et_phone, et_confrm_password;
    private Button btn_login, btn_fb;
    final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private String str_email, str_password, str_name, url = Apis.Base, udata, status,type="Personal";
    JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);

        btn_login = (Button) findViewById(R.id.btnRegister);
        btn_fb = (Button) findViewById(R.id.btn_fb);


        btn_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_email = et_email.getText().toString();
                str_password = et_password.getText().toString();
                str_name = et_name.getText().toString();

                if (str_name.equals("")) {
                    et_name.setError("Enter Name");
                } else if (str_email.equals("")) {
                    et_email.setError("Enter Email");
                } else if (!str_email.matches(EMAIL_PATTERN)) {
                    et_email.setError("Enter Valid Email");
                } else if (str_password.equals("")) {
                    et_password.setError("Enter Password");
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

            Dialogs.showProDialog(Register.this, "Sign Up...");
        }

        @Override
        protected String doInBackground(Void... params) {


            StringBuilder strBuilder = new StringBuilder(url);
            strBuilder.append("name=" + str_name);
            strBuilder.append("&email=" + str_email);
            strBuilder.append("&password=" + str_password);
            strBuilder.append("&rquest=registrationUp");
            strBuilder.append("&regtype="+type);
            strBuilder.append("&mobile=" + SaveSharedPreference.getMobile(Register.this));
            strBuilder.append("&token_id=" + SaveSharedPreference.getUserID(Register.this));

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
                startActivity(new Intent(Register.this, Refferal.class));
            } else {
                Dialogs.showDialog(Register.this, "Server Failed");
            }


        }
    }
}
