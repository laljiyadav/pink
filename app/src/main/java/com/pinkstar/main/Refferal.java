package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
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

import org.json.JSONObject;

public class Refferal extends Activity {
    Button refferal_submit;
    EditText ed_phone;
    String number, udata, url = Apis.Base;
    JSONObject json;
    ImageView mob_contact;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refferal);
        refferal_submit = (Button) findViewById(R.id.refferal_submit);
        ed_phone = (EditText) findViewById(R.id.et_phone);
        mob_contact = (ImageView) findViewById(R.id.ref_contact);

        refferal_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = ed_phone.getText().toString();

                if (number.equals("")) {
                    ed_phone.setError("Enter Mobile Number");
                } else {
                    new AttempLogin().execute();
                }


            }
        });

        mob_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Uri uri = Uri.parse("content://contacts");
                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    private class AttempLogin extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(Refferal.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            // http://pinkstarapp.com/api/restservices.php?rquest=reffered_by&token_id=%@&reffered_by=%@&mobile=%@
            StringBuilder strBuilder = new StringBuilder(url);
            strBuilder.append("reffered_by=" + number);
            strBuilder.append("&rquest=reffered_by");
            strBuilder.append("&mobile=" + SaveSharedPreference.getMobile(Refferal.this));
            strBuilder.append("&token_id=" + SaveSharedPreference.getUserID(Refferal.this));

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
                startActivity(new Intent(Refferal.this, MainActivity.class));
            } else {
                Dialogs.showDialog(Refferal.this, "Server Failed");
            }


        }
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

                ed_phone.setText("+91 " + number.substring(number.length() - 10).toString());

            }
        }
    }

    ;
}
