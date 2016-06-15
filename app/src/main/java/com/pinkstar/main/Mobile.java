package com.pinkstar.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.GPSTracker;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Mobile extends Activity implements View.OnClickListener {
    private EditText mobile_num;
    ProgressDialog mProgressDialog;
    JSONObject json;
    ArrayAdapter ad;
    ArrayList<String> std = new ArrayList<String>();
    String url = Apis.Base, message, mobile, std_code, udata;
    private TextView mobile_skip, mobile_terms, code;
    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        mobile_num = (EditText) findViewById(R.id.mobile_num);
        code = (TextView) findViewById(R.id.code);
        mobile_skip = (TextView) findViewById(R.id.mobile_skip);
        mobile_terms = (TextView) findViewById(R.id.mobile_terms);

        code.setText("+91");
        gpsTracker = new GPSTracker(Mobile.this);
        Location location = gpsTracker.getLocation();

        if (location == null) {
            alertDialog("Allow &#34;Pink Star&#34; to access your location while you use this app?", "Location is required", "Don't Allow", "Allow");
        } else {

        }
        mobile_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Toast.makeText(getApplicationContext(), "hello"+count, Toast.LENGTH_SHORT).show();
                if (mobile_num.getText().toString().equals("0")) {
                    mobile_num.setError("Enter Valid Number");
                    mobile_num.setText("");
                } else {

                    if (mobile_num.getText().length() < 10) {

                    } else if (mobile_num.getText().length() == 10) {
                        // Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();


                        mobile = mobile_num.getText().toString();
                        std_code = code.getText().toString();
                        alertDialog("Is this the right mobile number?", std_code + "-" + mobile, "No,go Back", "Yes");
                        //new AttempLogin().execute();

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mobile_skip.setOnClickListener(this);
        mobile_terms.setOnClickListener(this);
        code.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.mobile_skip:
                // startActivity(new Intent(Mobile.this, Register.class));
                break;
            case R.id.mobile_terms:
                startActivity(new Intent(Mobile.this, TermWeb.class));
                break;
            case R.id.code:
                sdtCode();
                break;
            default:
                break;

        }


    }


    private class AttempLogin extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(Mobile.this);
            mProgressDialog.setMessage("Verify...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {


            StringBuilder strBuilder = new StringBuilder(url);
            strBuilder.append("mobile=" + std_code + "-" + mobile);
            strBuilder.append("&resend=" + 0);
            strBuilder.append("&rquest=validateNum");


            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl1(strBuilder.toString());
            Log.e("json", "" + json);
            Log.e("json", "" + strBuilder.toString());

            try {

                udata = json.getString("udata");
                Object itme = json.get("result");

                if (itme instanceof JSONArray) {
                    JSONArray jsaary = json.getJSONArray("result");
                    if (udata.equals("0")) {
                        for (int i = 0; i < jsaary.length(); i++) {
                            JSONObject js = jsaary.getJSONObject(i);
                            SaveSharedPreference.setMobile(Mobile.this, std_code + "-" + mobile);
                            SaveSharedPreference.setUserID(Mobile.this, js.getString("token_id"));
                            SaveSharedPreference.setUSERSSN(Mobile.this, js.getString("otp"));
                            message = js.getString("mobile_verify");
                            Log.e("json", "ifffff");
                        }

                        if (message.equals("0")) {
                            startActivity(new Intent(Mobile.this, OTP.class));
                        }
                        if (message.equals("1")) {
                            startActivity(new Intent(Mobile.this, Password.class));
                        }
                    }


                    if (udata.equals("1")) {
                        for (int i = 0; i < jsaary.length(); i++) {
                            JSONObject js = jsaary.getJSONObject(i);
                            SaveSharedPreference.setMobile(Mobile.this, std_code + "-" + mobile);
                            SaveSharedPreference.setUserID(Mobile.this, js.getString("token_id"));
                            SaveSharedPreference.setUSERSSN(Mobile.this, js.getString("otp"));
                            message = js.getString("mobile_verify");

                        }

                        if (message.equals("0")) {
                            startActivity(new Intent(Mobile.this, OTP.class));
                        }
                        if (message.equals("1")) {
                            startActivity(new Intent(Mobile.this, Password.class));
                        }
                    }
                } else {


                    if (udata.equals("0")) {
                        JSONObject js = json.getJSONObject("result");
                        SaveSharedPreference.setMobile(Mobile.this, std_code + "-" + mobile);
                        SaveSharedPreference.setUserID(Mobile.this, js.getString("token_id"));
                        SaveSharedPreference.setUSERSSN(Mobile.this, js.getString("otp"));
                        message = js.getString("mobile_verify");
                        Log.e("json", "elseeee");

                        if (message.equals("0")) {
                            startActivity(new Intent(Mobile.this, OTP.class));
                        }
                        if (message.equals("1")) {
                            startActivity(new Intent(Mobile.this, Password.class));
                        }
                    }


                    if (udata.equals("1")) {
                        JSONObject js = json.getJSONObject("result");
                        SaveSharedPreference.setMobile(Mobile.this, "+"+std_code + "-" + mobile);
                        SaveSharedPreference.setUserID(Mobile.this, js.getString("token_id"));
                        SaveSharedPreference.setUSERSSN(Mobile.this, js.getString("otp"));
                        message = js.getString("mobile_verify");


                        if (message.equals("0")) {
                            startActivity(new Intent(Mobile.this, OTP.class));
                        }
                        if (message.equals("1")) {
                            startActivity(new Intent(Mobile.this, Password.class));
                        }
                    }
                }

            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            mProgressDialog.dismiss();

           /* if (udata.equals("1")) {
                startActivity(new Intent(Mobile.this, Password.class));
            }*/
            if (udata.equals("2")) {
                getAlert("Number is not valid");
            }


        }
    }

    public void getAlert(String abc) {
        final AlertDialog alertDialog = new AlertDialog.Builder(Mobile.this).create();
        alertDialog.setMessage(abc);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }


    public void sdtCode() {

        try {
            JSONArray jsonArray = new JSONArray(Apis.std_code);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jso = jsonArray.getJSONObject(i);
                std.add(jso.getString("dial_code"));
            }

            final Dialog dialog2 = new Dialog(Mobile.this);
            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
            dialog2.setContentView(R.layout.list_dialog);
            WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
            Window window = dialog2.getWindow();
            lp.copyFrom(window.getAttributes());
            lp.width = WindowManager.LayoutParams.FILL_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
            lp.gravity = Gravity.CENTER;

            ListView txt = (ListView) dialog2.findViewById(R.id.list_dailog);

            ad = new ArrayAdapter<String>(Mobile.this, android.R.layout.simple_list_item_1, std) {

                @Override
                public View getView(int position, View convertView,
                                    ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = (TextView) view.findViewById(android.R.id.text1);
                    textView.setGravity(Gravity.CENTER);


                    return view;
                }
            };

            txt.setAdapter(ad);
            txt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    dialog2.dismiss();
                    code.setText(std.get(position));


                }
            });
            dialog2.show();


        } catch (Exception e) {

        }

    }

    public void alertDialog(String txt_val, String txt_val1, String btn, String btn1) {


        final Dialog dialog2 = new Dialog(Mobile.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog2.setContentView(R.layout.dialog_location);
        WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
        Window window = dialog2.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        lp.gravity = Gravity.CENTER;


        TextView txt = (TextView) dialog2.findViewById(R.id.txt_text);
        TextView txt1 = (TextView) dialog2.findViewById(R.id.txt_text1);

        final Button dont = (Button) dialog2.findViewById(R.id.dont);
        final Button allow = (Button) dialog2.findViewById(R.id.allow);


        txt.setText(Html.fromHtml(txt_val));
        txt1.setText(txt_val1);
        dont.setText(btn);
        allow.setText(btn1);

        dont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dont.getText().toString().equalsIgnoreCase("Don't Allow")) {

                } else {
                    dialog2.dismiss();

                }

            }
        });

        allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allow.getText().toString().equalsIgnoreCase("Allow")) {
                    GPSTracker gpsTracker = new GPSTracker(Mobile.this);
                    Location location = gpsTracker.getLocation();
                    if (location == null) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    } else {
                        dialog2.dismiss();
                    }

                } else {
                    dialog2.dismiss();
                    new AttempLogin().execute();
                }
            }
        });


        dialog2.show();


    }

}
