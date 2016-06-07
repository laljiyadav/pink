package com.pinkstar.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Account extends AppCompatActivity implements View.OnClickListener {

    TextView ac_profile, ac_order, ac_stars, ac_change, ac_invite, ac_share;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ac_profile = (TextView) findViewById(R.id.ac_profile);
        ac_order = (TextView) findViewById(R.id.ac_order);
        ac_stars = (TextView) findViewById(R.id.ac_stars);
        ac_change = (TextView) findViewById(R.id.ac_change);
        ac_invite = (TextView) findViewById(R.id.ac_invite);
        ac_share = (TextView) findViewById(R.id.ac_share);
        logout = (Button) findViewById(R.id.logout);

        ac_profile.setOnClickListener(this);
        ac_order.setOnClickListener(this);
        ac_stars.setOnClickListener(this);
        ac_change.setOnClickListener(this);
        ac_invite.setOnClickListener(this);
        ac_share.setOnClickListener(this);
        logout.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v == ac_profile) {
            startActivity(new Intent(Account.this, Profile.class));
        }
        if (v == ac_order) {
           // startActivity(new Intent(Account.this, Profile.class));

        }
        if (v == ac_stars) {
            startActivity(new Intent(Account.this, Sendstar.class));

        }
        if (v == ac_change) {
            startActivity(new Intent(Account.this, ChangePassword.class));

        }
        if (v == ac_invite) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("image/png");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>This is the text that will be shared.</p>"));
            startActivity(Intent.createChooser(sharingIntent,"Share using"));

        }
        if (v == ac_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("image/png");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>This is the text that will be shared.</p>"));
            startActivity(Intent.createChooser(sharingIntent,"Share using"));

        }
        if (v == logout) {
            startActivity(new Intent(Account.this, Mobile.class));

        }


    }
}
