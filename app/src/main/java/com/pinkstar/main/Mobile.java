package com.pinkstar.main;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.GPSTracker;
import com.pinkstar.main.data.InternetStatus;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Mobile extends Activity implements View.OnClickListener {
    private EditText mobile_num;
    ProgressDialog mProgressDialog;
    JSONObject json;
    ArrayAdapter ad;
    ArrayList<String> std = new ArrayList<String>();
    String url = Apis.Base, message, mobile, std_code, udata;
    private TextView mobile_skip, mobile_terms, code;
    GPSTracker gpsTracker;
    View mDecorView;
    public final int MY_LOCATION_REQUEST_CODE = 10;
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CALL_PHONE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);

        inIt();
    }

    public void inIt() {
        mobile_num = (EditText) findViewById(R.id.mobile_num);
        code = (TextView) findViewById(R.id.code);
        mobile_skip = (TextView) findViewById(R.id.mobile_skip);
        mobile_terms = (TextView) findViewById(R.id.mobile_terms);


        Dialogs.hideSystemUI(mDecorView, Mobile.this);
        code.setText("+91");


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

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(Mobile.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        Log.e("log", "" + listPermissionsNeeded);
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_LOCATION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result for camera permission.
            for (int i = 0, len = permissions.length; i < len; i++) {
                String permission = permissions[i];


                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                    if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)) {

                        permisition(Mobile.this, "Allow Location Permission", "please allow the location for better featured", 1);

                    }
                }
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkPermissions()) {
            gpsTracker = new GPSTracker(Mobile.this);
            Location location = gpsTracker.getLocation();
            if (location == null) {
                alertDialog("Allow &#34;Pink Star&#34; to access your location while you use this app?", "Location is required", "Don't Allow", "Allow");
            }
        } else {

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.mobile_skip:
                //startActivity(new Intent(Mobile.this, Register.class));
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


            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("mobile", "" + std_code + "-" + mobile));
                nameValuePairs.add(new BasicNameValuePair("rquest", "checkMobile"));
                nameValuePairs.add(new BasicNameValuePair("api_token", Apis.Api_Token));


                // Create an array
                Parser perser = new Parser();
                json = perser.getJSONFromUrl(url, nameValuePairs);

                Log.e("url", ""+json);

                udata = json.getString("uData");


                if (udata.equals("1")) {
                    startActivity(new Intent(Mobile.this, OTP.class));
                    if (json.has("otp")) {
                        SaveSharedPreference.setUSERSSN(Mobile.this, json.getString("otp"));
                        SaveSharedPreference.setMobile(Mobile.this, "" + std_code + "-" + mobile);
                    }
                }
                if (udata.equals("17")) {
                    startActivity(new Intent(Mobile.this, OTP.class));
                    if (json.has("otp")) {
                        SaveSharedPreference.setUSERSSN(Mobile.this, json.getString("otp"));
                        SaveSharedPreference.setMobile(Mobile.this, "" + std_code + "-" + mobile);
                    }
                }
                if (udata.equals("5")) {
                    startActivity(new Intent(Mobile.this, Password.class));
                    SaveSharedPreference.setMobile(Mobile.this, "" + std_code + "-" + mobile);
                }

            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            mProgressDialog.dismiss();


        }
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
        dialog2.setCancelable(false);
        WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
        Window window = dialog2.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
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
                        dialog2.dismiss();
                    } else {
                        dialog2.dismiss();
                    }

                } else {

                    if (InternetStatus.isConnectingToInternet(Mobile.this)) {
                        dialog2.dismiss();
                        new AttempLogin().execute();
                    } else {
                        dialog2.dismiss();
                        showDialog("No Internet Connection");
                    }
                }
            }
        });


        dialog2.show();


    }

    public void permisition(final Context context, String txt_val, String txt_val1, final int request) {


        final Dialog dialog2 = new Dialog(context);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog2.setContentView(R.layout.permistion);
        dialog2.setCancelable(false);
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


        dont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog2.dismiss();


            }
        });

        allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                dialog2.dismiss();

            }
        });


        dialog2.show();


    }

    public void showDialog(String msg) {
        final Dialog dialog2 = new Dialog(Mobile.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog2.setContentView(R.layout.dia);
        dialog2.setCancelable(false);
        WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
        Window window = dialog2.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        lp.gravity = Gravity.CENTER;

        TextView tv_msg = (TextView) dialog2.findViewById(R.id.masg);
        TextView tv_ok = (TextView) dialog2.findViewById(R.id.ok);

        tv_msg.setText(msg);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InternetStatus.isConnectingToInternet(Mobile.this)) {
                    dialog2.dismiss();
                    new AttempLogin().execute();

                }
            }
        });
        dialog2.show();
    }


}
