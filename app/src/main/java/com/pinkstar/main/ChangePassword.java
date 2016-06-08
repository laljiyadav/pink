package com.pinkstar.main;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
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


public class ChangePassword extends Activity implements View.OnClickListener {
    EditText change_confirm, change_new, change_old;
    String str_change_confirm, str_change_new, str_change_old, udata, url = Apis.Base;
    Button change_submit;
    JSONObject json;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        change_new = (EditText) findViewById(R.id.change_new);
        change_confirm = (EditText) findViewById(R.id.change_confirm);
        change_old = (EditText) findViewById(R.id.change_old);
        change_submit = (Button) findViewById(R.id.change_submit);

        change_submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v == change_submit) {

            if (valid()) {
                new AttempChange().execute();
            }
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


            StringBuilder strBuilder = new StringBuilder(url);
            strBuilder.append("newpwd=" + str_change_new);
            strBuilder.append("&oldpwd=" + str_change_old);
            strBuilder.append("&rquest=change_password");
            strBuilder.append("&mobile=" + SaveSharedPreference.getMobile(ChangePassword.this));
            strBuilder.append("&token_id=" + SaveSharedPreference.getUserID(ChangePassword.this));

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
            try {
                if (udata.equals("1")) {
                    Dialogs.showCenterToast(ChangePassword.this, "Password Update Successfully");
                } else if (udata.equals("0")) {
                    Dialogs.showCenterToast(ChangePassword.this, "Old Password is incorrect");

                } else {
                    Dialogs.showCenterToast(ChangePassword.this, "Server Error");
                }
            } catch (Exception e) {

            }


        }
    }
}
