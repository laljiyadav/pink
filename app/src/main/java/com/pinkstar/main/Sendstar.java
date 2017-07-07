package com.pinkstar.main;

import android.app.Activity;
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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class Sendstar extends Activity implements View.OnClickListener {
    ImageView send_contact, star_img;
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

        inIt();

    }

    public void inIt() {

        send_contact = (ImageView) findViewById(R.id.send_contact);
        star_img = (ImageView) findViewById(R.id.star_img);
        send_mobile = (EditText) findViewById(R.id.send_mobile);
        et_stars = (EditText) findViewById(R.id.amount);
        send_btn = (Button) findViewById(R.id.send_btn);

        send_contact.setOnClickListener(this);
        send_btn.setOnClickListener(this);

        star_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.star_dialog(Sendstar.this,true);
            }
        });

        Dialogs.Touch(Sendstar.this, star_img);
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


            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();

            strBuilder.add(new BasicNameValuePair("transfer_stars", stars));
            strBuilder.add(new BasicNameValuePair("transfer_number", mobile));
            strBuilder.add(new BasicNameValuePair("rquest", "starTransfer"));
            strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(Sendstar.this)));
            strBuilder.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(Sendstar.this)));
            strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));

            Log.e("Log_tag", "" + strBuilder.toString());

            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl(url, strBuilder);
            Log.e("Log_tag", "" + json);
            try {

                udata = json.getString("uData");
                if (udata.equals("1")) {
                    SaveSharedPreference.setTotal(Sendstar.this, json.getJSONObject("star").getString("balance_star"));
                    SaveSharedPreference.setBalStar(Sendstar.this, json.getJSONObject("star").getString("redeemable_star"));

                }
                if (udata.equals("8")) {
                    SaveSharedPreference.setTotal(Sendstar.this, json.getJSONObject("star").getString("balance_star"));
                    SaveSharedPreference.setBalStar(Sendstar.this, json.getJSONObject("star").getString("redeemable_star"));

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
                    Dialogs.showCenterToast(Sendstar.this, json.getJSONObject("result").getString("message"));
                    finish();
                } else if (udata.equals("8")) {
                    Dialogs.showCenterToast(Sendstar.this, json.getJSONObject("result").getString("message"));
                } else {
                    Dialogs.showCenterToast(Sendstar.this, "Server Error");
                }
            } catch (Exception e) {

            }


        }
    }
}

