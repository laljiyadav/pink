package com.pinkstar.main;

import android.app.Activity;
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
    LinearLayout contact_us,lin_notification;
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
        contact_us = (LinearLayout) findViewById(R.id.lin_logout);
        lin_notification = (LinearLayout) findViewById(R.id.lin_noti);
        star_img = (ImageView) findViewById(R.id.star_img);


        ac_profile.setOnClickListener(this);
        ac_order.setOnClickListener(this);
        ac_stars.setOnClickListener(this);
        ac_change.setOnClickListener(this);
        ac_invite.setOnClickListener(this);
        ac_share.setOnClickListener(this);
        contact_us.setOnClickListener(this);
        lin_notification.setOnClickListener(this);
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
        if (v == lin_notification) {
            startActivity(new Intent(Account.this, Notifition.class));

        }


        if (v == star_img) {
            Dialogs.star_dialog(Account.this);
        }


    }


}
