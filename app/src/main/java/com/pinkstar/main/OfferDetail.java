package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pinkstar.main.adapter.OfferAdapter;
import com.pinkstar.main.data.Apis;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import me.relex.circleindicator.CircleIndicator;

public class OfferDetail extends Activity implements View.OnClickListener {

    TextView txt_title, txt_date, txt_detail, txt_othdet, txt_adddet, txt_address, txt_phone, txt_email;
    Button btn_route, btn_call;
    ArrayList<HashMap<String, String>> offerList = new ArrayList<HashMap<String, String>>();
    String img_array, udata, url = Apis.Base;
    ViewPager view_pager;
    private Handler mHandler;
    Runnable runnable;
    int position;
    HashMap<String, String> map = new HashMap<String, String>();
    TextView txt_addtional, txt_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_detail);
        inIt();
        offer();

    }

    public void inIt() {

        txt_adddet = (TextView) findViewById(R.id.de_adddet);
        txt_detail = (TextView) findViewById(R.id.tx_details);
        txt_othdet = (TextView) findViewById(R.id.tx_othdet);
        txt_title = (TextView) findViewById(R.id.de_title);
        txt_date = (TextView) findViewById(R.id.txt_start);
        txt_address = (TextView) findViewById(R.id.tx_add);
        txt_phone = (TextView) findViewById(R.id.tx_contact);
        txt_email = (TextView) findViewById(R.id.tx_email);
        btn_route = (Button) findViewById(R.id.route);
        btn_call = (Button) findViewById(R.id.phn);
        view_pager = (ViewPager) findViewById(R.id.image_pager);
        txt_addtional = (TextView) findViewById(R.id.txt_addtional);
        txt_other = (TextView) findViewById(R.id.txt_other);

        map = (HashMap<String, String>) getIntent().getSerializableExtra("arraylist");

        txt_address.setText(map.get("address"));
        txt_phone.setText(map.get("phone"));
        txt_email.setText(map.get("email"));

        txt_detail.setText(map.get("des1"));
        txt_title.setText(map.get("title"));
        txt_date.setText("Offer Valid: " + map.get("start") + " to " + map.get("end"));
        img_array = map.get("images");

        if (!map.get("des3").equalsIgnoreCase("")) {
            txt_othdet.setText(map.get("des3"));

        } else {

            txt_othdet.setVisibility(View.GONE);
            txt_other.setVisibility(View.GONE);
        }
        if (!map.get("des2").equalsIgnoreCase("")) {
            txt_adddet.setText(map.get("des2"));

        } else {

            txt_addtional.setVisibility(View.GONE);
            txt_adddet.setVisibility(View.GONE);
        }

        btn_route.setOnClickListener(this);
        btn_call.setOnClickListener(this);

        txt_phone.setOnClickListener(this);
        txt_email.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btn_call) {
            try {
                String uri = "tel:" + map.get("phone");
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));

                startActivity(dialIntent);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Your call has failed...",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
                Log.e("Exception ", ": " + e);
            }
        }
        if (v == btn_route) {
            Intent in = new Intent(OfferDetail.this, MapActivity.class);
            in.putExtra("lat", Double.parseDouble(map.get("lat")));
            in.putExtra("lang", Double.parseDouble(map.get("long")));
            in.putExtra("company", map.get("title"));
            in.putExtra("add", map.get("address"));

            startActivity(in);
        }

        if (v == txt_phone) {
            try {
                String uri = "tel:" + map.get("phone");
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));

                startActivity(dialIntent);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Your call has failed...",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
                Log.e("Exception ", ": " + e);
            }
        }

        if (v == txt_email) {
            sendEmail();
        }

    }

    protected void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
        intent.putExtra(Intent.EXTRA_TEXT, "Body of email");
        intent.setData(Uri.parse("mailto:info@gmail.com")); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        startActivity(intent);
    }

    public void offer() {


        String array[] = img_array.split(",");


        try {

            HashMap<String, String> map;
            for (int i = 0; i < array.length; i++) {
                map = new HashMap<String, String>();

                map.put("image", testtoimage(i,array[i]));
                Log.e("Image", testtoimage(i,array[i]));

                offerList.add(map);

            }

        } catch (Exception e) {

        }

        view_pager.setAdapter(new OfferAdapter(OfferDetail.this, offerList));

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);

        indicator.setViewPager(view_pager);

        mHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                if (position >= offerList.size()) {
                    position = 0;
                } else {
                    position = position + 1;
                }
                view_pager.setCurrentItem(position, true);
                mHandler.postDelayed(runnable, 7000);

            }
        };
    }

    public String testtoimage(int pos, String image) {

        String str = image.replace("\"", "");
        String first = str.replace("[", "").replace("]","");



        return first.replace("pk", "pinkstarapp.com");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mHandler != null) {
            mHandler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        if (offerList.size() == 0) {
            mHandler.postDelayed(runnable, 7000);
        }
    }
}
