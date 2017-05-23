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

import com.pinkstar.main.data.SaveSharedPreference;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class OrderDetail extends Activity implements View.OnClickListener {

    TextView order_number, time, rupee, descri, payment, txt_title, txt_email, txt_mobile, txt_des;
    TextView txt_price, txt_shi_price, txt_tex_price, txt_total, txt_name, txt_address;
    ImageView img;
    ProgressBar progress;
    HashMap<String, String> map = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);


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
        txt_price = (TextView) findViewById(R.id.txt_price);
        txt_shi_price = (TextView) findViewById(R.id.txt_shi_price);
        txt_tex_price = (TextView) findViewById(R.id.txt_tex_price);
        txt_total = (TextView) findViewById(R.id.txt_total_price);
        txt_des = (TextView) findViewById(R.id.txt_des);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_address = (TextView) findViewById(R.id.txt_address);
        img = (ImageView) findViewById(R.id.image);
        progress = (ProgressBar) findViewById(R.id.progressBar);

        txt_email.setOnClickListener(this);
        txt_mobile.setOnClickListener(this);

        time.setText(parseDateToddMMyyyy(map.get("order_date")));
        rupee.setText(getResources().getString(R.string.rs) + " " + map.get("order_cost"));
        txt_des.setText(map.get("des"));
        txt_title.setText("Payment of Rs. " + map.get("order_cost") + " received by PinkStar !");
        order_number.setText(map.get("orderid"));
        descri.setText(map.get("name"));
        txt_price.setText(getResources().getString(R.string.rs) + " " + map.get("order_cost"));
        txt_shi_price.setText(getResources().getString(R.string.rs) + " " + map.get("ship_price"));
        txt_tex_price.setText(getResources().getString(R.string.rs) + " 0");
        txt_total.setText("Total      " + getResources().getString(R.string.rs) + " " + map.get("order_cost"));
        txt_name.setText(SaveSharedPreference.getUserName(OrderDetail.this) + " " + SaveSharedPreference.getLastName(OrderDetail.this));

        if (map.get("address").contains(",")) {
            String addre[] = map.get("address").split(",");
            try {
                txt_address.setText("" + addre[0] + "\n" + addre[1] + "\n" + addre[2] + "\n" + addre[3] + "\n" + addre[4]);
            } catch (Exception e) {

            }
        } else {
            txt_address.setText(map.get("address"));
        }

        payment.setText("Status-: " + map.get("order_status"));
        Picasso.with(OrderDetail.this)
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
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
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
