package com.pinkstar.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout;

import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class Account extends Activity implements View.OnClickListener {

    LinearLayout ac_profile, ac_order, ac_stars, ac_change, ac_invite, ac_share;
    LinearLayout contact_us,lin_logout,lin_legal;
    JSONObject json;
    String url = Apis.Base, udata;
    ImageView star_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        inIt();
    }

    public void inIt() {
        ac_profile = (LinearLayout) findViewById(R.id.lin_profile);
        ac_order = (LinearLayout) findViewById(R.id.lin_order);
        ac_stars = (LinearLayout) findViewById(R.id.lin_send);
        ac_change = (LinearLayout) findViewById(R.id.lin_change);
        ac_invite = (LinearLayout) findViewById(R.id.lin_invite);
        ac_share = (LinearLayout) findViewById(R.id.lin_what);
        contact_us = (LinearLayout) findViewById(R.id.lin_help);
        lin_logout = (LinearLayout) findViewById(R.id.lin_logout);
        lin_legal = (LinearLayout) findViewById(R.id.lin_legal);
        star_img = (ImageView) findViewById(R.id.star_img);


        ac_profile.setOnClickListener(this);
        ac_order.setOnClickListener(this);
        ac_stars.setOnClickListener(this);
        ac_change.setOnClickListener(this);
        ac_invite.setOnClickListener(this);
        ac_share.setOnClickListener(this);
        contact_us.setOnClickListener(this);
        lin_legal.setOnClickListener(this);
        lin_logout.setOnClickListener(this);

        star_img.setOnClickListener(this);

        Dialogs.Touch(Account.this, star_img);

    }

    @Override
    public void onClick(View v) {

        if (v == ac_profile) {
            startActivity(new Intent(Account.this, Profile.class));
        }
        if (v == ac_order) {
            startActivity(new Intent(Account.this, OrderActivity.class));

        }
        if (v == ac_stars) {
            startActivity(new Intent(Account.this, Sendstar.class));

        }
        if (v == ac_change) {
            startActivity(new Intent(Account.this, ChangePassword.class));

        }
        if (v == ac_invite) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "https://play.google.com/store/apps/details?id=com.pinkstar.main";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        }
        if (v == ac_share) {
            try {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.pinkstar.main");
                startActivity(whatsappIntent);
            } catch (Exception ex) {
                Dialogs.showCenterToast(Account.this, "WhatsApp have not been installed.");
            }

        }
        if (v == contact_us) {
            startActivity(new Intent(Account.this, Contact_Us.class));

        }



        if (v == star_img) {
            Dialogs.star_dialog(Account.this,true);
        }
        if(v==lin_logout)
        {
            open();
        }


    }

    public void open() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You want to logout ?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        new AttempLogout().execute();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private class AttempLogout extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(Account.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
            strBuilder.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(Account.this)));
            strBuilder.add(new BasicNameValuePair("rquest", "logout"));
            strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(Account.this)));
            strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));


            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl(url, strBuilder);

            Log.e("url", "" + strBuilder.toString());
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

            if (udata.equals("6")) {
                startActivity(new Intent(Account.this, Mobile.class));

                SaveSharedPreference.setUserID(Account.this, "");
                SaveSharedPreference.setfirst(Account.this, "");
                SaveSharedPreference.setLastName(Account.this, "");
                SaveSharedPreference.setUserIMAGE(Account.this, "");
                SaveSharedPreference.setUserEMAIL(Account.this, "");
                SaveSharedPreference.setBirth(Account.this, "");
                SaveSharedPreference.setAnnversary(Account.this, "");
                SaveSharedPreference.setUSERAuth(Account.this, "");
                SaveSharedPreference.setGender(Account.this, "");
                finish();
            } else {
                Dialogs.showCenterToast(Account.this, "Try Again");
            }

        }
    }


}
