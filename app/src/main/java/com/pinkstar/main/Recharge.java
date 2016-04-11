package com.pinkstar.main;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Recharge extends Activity {
    private EditText recharge_country_code, recharge_phone, recharge_operator, recharge_amount;
    private CheckBox recharge_fastforward;
    private Button recharge_proceed;
    private String str_country_code, str_phone, str_operator, str_amount, str_fastforward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        recharge_country_code = (EditText) findViewById(R.id.recharge_country_code);
        recharge_phone = (EditText) findViewById(R.id.recharge_phone);
        recharge_operator = (EditText) findViewById(R.id.recharge_operator);
        recharge_amount = (EditText) findViewById(R.id.recharge_amount);
        recharge_fastforward = (CheckBox) findViewById(R.id.recharge_fastforward);
        recharge_proceed = (Button) findViewById(R.id.recharge_proceed);
//        str_country_code = recharge_country_code.getText().toString();
//        str_phone = recharge_phone.getText().toString();
//        str_operator = recharge_operator.getText().toString();
//        str_amount = recharge_amount.getText().toString();
//        str_fastforward = recharge_fastforward.getText().toString();

    }
}
