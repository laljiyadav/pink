package com.pinkstar.main;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity {
    private EditText et_email, et_password, et_name, et_phone, et_confrm_password;
    private Button btn_login;
    private String str_email, str_password, str_name, str_phone, str_confrm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_name= (EditText) findViewById(R.id.et_name);
        et_email= (EditText) findViewById(R.id.et_email);
        et_password= (EditText) findViewById(R.id.et_password);
        et_phone= (EditText) findViewById(R.id.et_phone);
        et_confrm_password= (EditText) findViewById(R.id.et_confirm_password);
        str_email = et_email.getText().toString();
        str_password = et_password.getText().toString();
        str_name = et_name.getText().toString();
        str_confrm_password = et_confrm_password.getText().toString();
        str_phone = et_phone.getText().toString();
    }
}
