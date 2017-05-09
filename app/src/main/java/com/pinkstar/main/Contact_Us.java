package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Contact_Us extends Activity implements View.OnClickListener{

    private EditText ed_name, ed_msg;
    String name, email, massage, mobile;
    TextView txt_email,txt_mobile;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__us);

        inIt();
    }

    private void inIt() {
        ed_name = (EditText) findViewById(R.id.ed_email);
        ed_msg = (EditText) findViewById(R.id.ed_msg);
        txt_email = (TextView) findViewById(R.id.txt_mail);
        txt_mobile = (TextView) findViewById(R.id.txt_phone);
        btn_submit = (Button) findViewById(R.id.btn_submit);


        btn_submit.setOnClickListener(this);
        txt_email.setOnClickListener(this);
        txt_mobile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        if (v == btn_submit) {
            if (valid()) {
                //getContactUs();
            }
        }
        if (v == txt_email) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@mysolutions4u.in"});
            i.putExtra(Intent.EXTRA_SUBJECT, "");
            i.putExtra(Intent.EXTRA_TEXT, "");
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Log.e("error", "There are no email clients installed.");
            }

        }
        if (v == txt_mobile) {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+918571038063"));
            startActivity(intent);

        }

    }

    private boolean valid() {
        name = ed_name.getText().toString();
        massage = ed_msg.getText().toString();
        //mobile = ed_mobile.getText().toString();

        if (name.equals("")) {
            ed_name.setError("Enter Subject");
            return false;
        } else if (massage.equals("")) {
            ed_msg.setError("Enter Massage");
            return false;
        } else {
            return true;
        }
    }
}
