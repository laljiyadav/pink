package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class RechargeHistoryDetail extends Activity implements View.OnClickListener {

    TextView order_number, time, rupee, descri, payment,txt_title,txt_email,txt_mobile;
    ImageView img;
    ProgressBar progress;
    HashMap<String, String> map = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_history_detail);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            map = (HashMap<String, String>) getIntent().getSerializableExtra("map");
        }

        inIt();
    }

    public void inIt() {
        order_number = (TextView) findViewById(R.id.txt_order_number);
        time = (TextView) findViewById(R.id.time_date);
        rupee = (TextView) findViewById(R.id.txt_repee);
        descri = (TextView) findViewById(R.id.description);
        payment = (TextView) findViewById(R.id.payment_type);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_email = (TextView) findViewById(R.id.txt_mail);
        txt_mobile = (TextView) findViewById(R.id.txt_phone);
        img = (ImageView) findViewById(R.id.image);
        progress = (ProgressBar) findViewById(R.id.progressBar);

        txt_email.setOnClickListener(this);
        txt_mobile.setOnClickListener(this);

        time.setText(parseDateToddMMyyyy(map.get("order_date")));
        rupee.setText(getResources().getString(R.string.rs) + " " + map.get("order_cost"));

        txt_title.setText("Payment of Rs. "+map.get("order_cost")+" received by PinkStar !");
        order_number.setText(map.get("orderid"));
        descri.setText(map.get("name"));

        if (map.get("order_status").equals("0")) {
            payment.setText("Your Order is successful");

        } else {
            payment.setText("Payment Failed");
        }

        Picasso.with(RechargeHistoryDetail.this)
                .load(map.get("image"))
                .into(img, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (progress != null) {
                            progress.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {


                    }
                });
    }


    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "dd.MM.yyyy HH:mm:ss";
        String outputPattern = "EEE dd MMM yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public void onClick(View v) {

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
}
