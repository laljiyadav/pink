package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;


public class ChangePassword extends Activity implements View.OnClickListener {
    EditText change_confirm, change_new, change_old;
    String str_change_confirm, str_change_new, str_change_old, udata, url = Apis.Base;
    Button change_submit;
    JSONObject json;
    ImageView star_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        inIt();
    }

    public void inIt() {
        change_new = (EditText) findViewById(R.id.change_new);
        change_confirm = (EditText) findViewById(R.id.change_confirm);
        change_old = (EditText) findViewById(R.id.change_old);
        change_submit = (Button) findViewById(R.id.change_submit);
        star_img = (ImageView) findViewById(R.id.star_img);

        change_submit.setOnClickListener(this);
        star_img.setOnClickListener(this);

        Dialogs.Touch(ChangePassword.this, star_img);
    }

    @Override
    public void onClick(View v) {

        if (v == change_submit) {

            if (valid()) {
                new AttempChange().execute();
            }
        }
        if (v == star_img) {
            Dialogs.star_dialog(ChangePassword.this);
        }

    }

    public boolean valid() {
        str_change_confirm = change_confirm.getText().toString();
        str_change_new = change_new.getText().toString();
        str_change_old = change_old.getText().toString();


        if (str_change_confirm.equals("")) {
            change_confirm.setError("Enter confirm password");
            return false;
        } else if (str_change_old.equals("")) {
            change_old.setError("Enter old password");
            return false;
        } else if (str_change_new.equals("")) {
            change_new.setError("Enter new password");
            return false;
        } else if (str_change_old.equals(change_new)) {
            Dialogs.showCenterToast(ChangePassword.this, "Old and new password same");
            return false;
        } else if (!str_change_new.matches(str_change_confirm)) {
            Dialogs.showCenterToast(ChangePassword.this, "New and Confirm password mismatch");
            return false;
        } else {
            return true;
        }
    }

    private class AttempChange extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(ChangePassword.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {


            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
            strBuilder.add(new BasicNameValuePair("new_password", str_change_new));
            strBuilder.add(new BasicNameValuePair("old_password", str_change_old));
            strBuilder.add(new BasicNameValuePair("rquest", "changePassword"));
            strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(ChangePassword.this)));
            strBuilder.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(ChangePassword.this)));
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
            try {
                if (udata.equals("1")) {
                    Dialogs.showCenterToast(ChangePassword.this, "Password Update Successfully");
                    startActivity(new Intent(ChangePassword.this, Mobile.class));
                    SaveSharedPreference.setUserID(ChangePassword.this, "");
                } else if (udata.equals("10")) {
                    Dialogs.showCenterToast(ChangePassword.this, "Old Password is incorrect");

                } else {
                    Dialogs.showCenterToast(ChangePassword.this, "Server Error");
                }
            } catch (Exception e) {

            }


        }
    }
}
