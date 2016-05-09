package com.pinkstar.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        ac_order = (TextView) findViewById(R.id.ac_profile);
        ac_stars = (TextView) findViewById(R.id.ac_profile);
        ac_change = (TextView) findViewById(R.id.ac_profile);
        ac_invite = (TextView) findViewById(R.id.ac_profile);
        ac_share = (TextView) findViewById(R.id.ac_profile);
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
            startActivity(new Intent(Account.this, Profile.class));

        }
        if (v == ac_stars) {
            startActivity(new Intent(Account.this, Sendstar.class));

        }
        if (v == ac_change) {
            startActivity(new Intent(Account.this, ChangePassword.class));

        }
        if (v == ac_invite) {
            startActivity(new Intent(Account.this, Profile.class));

        }
        if (v == ac_share) {
            startActivity(new Intent(Account.this, Profile.class));

        }
        if (v == logout) {
            startActivity(new Intent(Account.this, Mobile.class));

        }


    }
}
