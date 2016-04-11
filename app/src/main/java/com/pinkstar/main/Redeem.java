package com.pinkstar.main;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Redeem extends Activity {
    private EditText redeem_code;
    private Button btn_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);
        redeem_code = (EditText) findViewById(R.id.redeem_code);
        btn_continue = (Button) findViewById(R.id.btn_continue);
    }
}
