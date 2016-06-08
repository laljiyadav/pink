package com.pinkstar.main;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.json.JSONObject;

public class Sendstar extends AppCompatActivity implements View.OnClickListener {
    ImageView send_contact;
    EditText send_mobile, et_stars;
    private static final int REQUEST_CODE = 1;
    Button send_btn;
    String stars, mobile, url = Apis.Base, udata;
    JSONObject json;
    int readable_star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendstar);

        send_contact = (ImageView) findViewById(R.id.send_contact);
        send_mobile = (EditText) findViewById(R.id.send_mobile);
        et_stars = (EditText) findViewById(R.id.amount);
        send_btn = (Button) findViewById(R.id.send_btn);

        send_contact.setOnClickListener(this);
        send_btn.setOnClickListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = intent.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

                Cursor cursor = getContentResolver().query(uri, projection,
                        null, null, null);
                cursor.moveToFirst();

                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberColumnIndex).replaceAll("[ +]", "").replace("-", "");

                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameColumnIndex);

                Log.e("log", "ZZZ number : " + number + " , name : " + name);

                send_mobile.setText("+91-" + number.substring(number.length() - 10).toString());

            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v == send_btn) {

            if (valid()) {
                new AttempSendStar().execute();
            }


        }

        if (v == send_contact) {
            Uri uri = Uri.parse("content://contacts");
            Intent intent = new Intent(Intent.ACTION_PICK, uri);
            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    public boolean valid() {
        mobile = send_mobile.getText().toString();
        stars = et_stars.getText().toString();
        readable_star = Integer.parseInt(SaveSharedPreference.getBalStar(Sendstar.this));
        int str = 0;

        if (mobile.equals("")) {

        } else {
            str = Integer.parseInt(stars);
        }

        if (mobile.equals("")) {
            send_mobile.setError("Enter Number");
            return false;
        } else if (mobile.length() < 10) {
            send_mobile.setError("Enter valid number");
            return false;
        } else if (stars.equals("")) {
            et_stars.setError("Enter Stars");
            return false;
        } else if (str >= readable_star) {
            et_stars.setError("Enter valid stars");
            return false;
        } else if (mobile.length() == 10) {
            mobile = "+91-" + mobile;
            return true;
        } else {
            return true;
        }
    }

    private class AttempSendStar extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(Sendstar.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {


            StringBuilder strBuilder = new StringBuilder(url);
            strBuilder.append("stars=" + stars);
            strBuilder.append("&shareno=" + mobile);
            strBuilder.append("&rquest=transferstar");
            strBuilder.append("&mobile=" + SaveSharedPreference.getMobile(Sendstar.this));
            strBuilder.append("&token_id=" + SaveSharedPreference.getUserID(Sendstar.this));

            Log.e("Log_tag", "" + strBuilder.toString());

            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl1(strBuilder.toString());
            Log.e("Log_tag", "" + json);
            try {

                udata = json.getString("udata");
                if (udata.equals("1")) {
                    SaveSharedPreference.setTotal(Sendstar.this, json.getJSONArray("result").getJSONObject(0).getString("balance_star"));
                    SaveSharedPreference.setBalStar(Sendstar.this, json.getJSONArray("result").getJSONObject(0).getString("redeemable_star"));

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
                    Dialogs.showCenterToast(Sendstar.this, json.getString("status"));
                } else {
                    Dialogs.showCenterToast(Sendstar.this, "Server Error");
                }
            } catch (Exception e) {

            }


        }
    }
}

