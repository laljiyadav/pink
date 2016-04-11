package com.pinkstar.main;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


public class ChangePassword extends Activity {
    private EditText change_confirm, change_new, change_old;
    private String str_change_confirm, str_change_new, str_change_old;
    private Button change_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        change_new = (EditText) findViewById(R.id.change_new);
        change_confirm = (EditText) findViewById(R.id.change_confirm);
        change_old = (EditText) findViewById(R.id.change_old);
        change_submit = (Button) findViewById(R.id.change_submit);
    }
}
