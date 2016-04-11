package com.pinkstar.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import others.Apis;
import others.Parser;


public class Mobile extends Activity implements View.OnClickListener {
    private EditText mobile_num;
    ProgressDialog mProgressDialog;
    JSONObject json;
    ArrayAdapter ad;
    ArrayList<String> std = new ArrayList<String>();
    String url = Apis.Base, message, mobile, std_code;
    private TextView mobile_skip, mobile_terms, code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        mobile_num = (EditText) findViewById(R.id.mobile_num);
        code = (TextView) findViewById(R.id.code);
        mobile_skip = (TextView) findViewById(R.id.mobile_skip);
        mobile_terms = (TextView) findViewById(R.id.mobile_terms);

        code.setText("+91");

        mobile_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Toast.makeText(getApplicationContext(), "hello"+count, Toast.LENGTH_SHORT).show();
                if (mobile_num.getText().length() < 10) {

                } else if (mobile_num.getText().length() == 10) {
                   // Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
                    mobile = mobile_num.getText().toString();
                    std_code = code.getText().toString();
                    new AttempLogin().execute();

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
            strBuilder.append("mobile="+std_code+"-"+ mobile);
            strBuilder.append("&resend=" + 0);
            strBuilder.append("&rquest=validateNum");

            Log.e("Log_tag", "" + strBuilder.toString());

            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl1(strBuilder.toString());
            Log.e("Log_tag", "" + json);
            try {




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

}
