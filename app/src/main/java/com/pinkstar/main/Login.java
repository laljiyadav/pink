package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity {
    private EditText et_email, et_password;
    private TextView forgotpassword;
    private Button btn_login;
    private String str_email, str_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_email = (EditText) findViewById(R.id.login_email);
        et_password = (EditText) findViewById(R.id.login_password);
        btn_login= (Button) findViewById(R.id.btn_login);
        forgotpassword= (TextView) findViewById(R.id.forgotpassword);
        str_email = et_email.getText().toString();
        str_password = et_password.getText().toString();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startActivity(new Intent(Login.this,Mobile.class));
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Reset_Password.class));
            }
        });


    }
}
