package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OTP extends Activity {
    private EditText otp_resend, otp_code;
    private String str_otp_resend, str_otp_code, str_otp_submit;
    private Button otp_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otp_submit= (Button) findViewById(R.id.otp_submit);
        otp_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OTP.this,Refferal.class));
            }
        });

    }
}
