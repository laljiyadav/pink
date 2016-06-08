package com.pinkstar.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.pinkstar.main.data.SaveSharedPreference;

public class MyWallet extends AppCompatActivity {

    TextView txt_total,txt_read;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        txt_total=(TextView)findViewById(R.id.txt_total);
        txt_read=(TextView)findViewById(R.id.txt_read);

        txt_total.setText(SaveSharedPreference.getTotal(MyWallet.this));
        txt_read.setText(SaveSharedPreference.getBalStar(MyWallet.this));
    }
}
