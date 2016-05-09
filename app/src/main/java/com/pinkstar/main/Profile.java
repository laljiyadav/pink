package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Profile extends Activity {

    ToggleButton toggleButton;
    TextView txt_gender,birth,annversary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toggleButton=(ToggleButton)findViewById(R.id.toggleButton1);
        txt_gender=(TextView)findViewById(R.id.gender);
        birth=(TextView)findViewById(R.id.birth);


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txt_gender.setText("Male");
                }
                else {
                    txt_gender.setText("Female");
                }
            }
        });






    }
}
