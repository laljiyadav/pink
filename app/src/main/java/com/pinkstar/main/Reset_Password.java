package com.pinkstar.main;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Reset_Password extends Activity {
    private EditText et_reset_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset__password);
        et_reset_password= (EditText) findViewById(R.id.et_reset_password);

    }
}
