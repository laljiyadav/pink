package com.pinkstar.main;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pinkstar.main.adapter.OfferAdapter;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeDetail extends AppCompatActivity implements View.OnClickListener {

    ViewPager viwe_pager;
    TextView tx_company, tx_address, tx_address1, tx_cash, tx_type, tx_cost, tc_contact, tx_time;
    RatingBar rating;
    ArrayList<HashMap<String, String>> offerList = new ArrayList<HashMap<String, String>>();
    String company, img_array, unique_id, udata, url = Apis.Base, phn_number;
    OfferAdapter offerAdapter;
    private Handler mHandler;
    Runnable runnable;
    int position;
    JSONObject json;
    double lat, lang;
    JSONArray details, count;
    Button phn, route;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);
        viwe_pager = (ViewPager) findViewById(R.id.image_pager);
        tx_company = (TextView) findViewById(R.id.de_company);
        tx_address = (TextView) findViewById(R.id.de_add);
        tx_address1 = (TextView) findViewById(R.id.de_add1);
        tx_cash = (TextView) findViewById(R.id.tx_cash);
        tx_cost = (TextView) findViewById(R.id.tx_cost);
        tx_type = (TextView) findViewById(R.id.tx_type);
        tc_contact = (TextView) findViewById(R.id.tx_contact);
        tx_time = (TextView) findViewById(R.id.tx_time);
        rating = (RatingBar) findViewById(R.id.ratingBar);
        route = (Button) findViewById(R.id.route);
        phn = (Button) findViewById(R.id.phn);

        company = getIntent().getExtras().getString("company");
        img_array = getIntent().getExtras().getString("img_array");
        unique_id = getIntent().getExtras().getString("unique_id");

        new AttempDetails().execute();

        route.setOnClickListener(this);
        phn.setOnClickListener(this);

        tx_company.setText(company);

        try {
            JSONArray array = new JSONArray(img_array);
            HashMap<String, String> map;
            for (int i = 0; i < array.length(); i++) {
                map = new HashMap<String, String>();
                JSONObject object = array.getJSONObject(i);
                map.put("image", object.getString("image_url"));
//// this for getting image from url
                offerList.add(map);

            }

        } catch (Exception e) {

        }

        offerAdapter = new OfferAdapter(HomeDetail.this, offerList);
        viwe_pager.setAdapter(offerAdapter);

        mHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                if (position >= offerList.size()) {
                    position = 0;
                } else {
                    position = position + 1;
                }
                viwe_pager.setCurrentItem(position, true);
                mHandler.postDelayed(runnable, 7000);

            }
        };


    }

    @Override
    public void onClick(View v) {
        if (v == route) {
            try {
                Intent in = new Intent(HomeDetail.this, MapActivity.class);
                in.putExtra("lat", lat);
                in.putExtra("lang", lang);
                in.putExtra("company", company);
                in.putExtra("add", details.getJSONObject(0).getString("address"));

                startActivity(in);
            } catch (Exception e) {

            }
        }
        if (v == phn) {
            try {
                String uri = "tel:" + phn_number;
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));

                startActivity(dialIntent);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Your call has failed...",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }
    }


    private class AttempDetails extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(HomeDetail.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            StringBuilder strBuilder = new StringBuilder(url);
            strBuilder.append("token_id=" + SaveSharedPreference.getUserID(HomeDetail.this));
            strBuilder.append("&rquest=vendor_details");
            strBuilder.append("&vendor_id=" + unique_id);
            strBuilder.append("&mobile=" + SaveSharedPreference.getMobile(HomeDetail.this));


            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl1(strBuilder.toString());
            Log.e("json", "" + json);
            try {

                udata = json.getString("udata");
                JSONObject obj = json.getJSONObject("result");
                details = obj.getJSONArray("details");
                count = obj.getJSONArray("contact");


            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            Dialogs.dismissDialog();
            try {
                if (udata.equals("1")) {

                    tx_address.setText(details.getJSONObject(0).getString("address_second"));
                    tx_address1.setText(details.getJSONObject(0).getString("address") + "," + details.getJSONObject(0).getString("address_second")
                            + "," + details.getJSONObject(0).getString("city") + "," + details.getJSONObject(0).getString("state")
                            + "," + details.getJSONObject(0).getString("country") + "-" + details.getJSONObject(0).getString("pincode"));
                    tx_cash.setText(details.getJSONObject(0).getString("maxstar") + " : Max star per visit");
                    tx_type.setText(details.getJSONObject(0).getString("type"));
                    tx_time.setText(details.getJSONObject(0).getString("start_time") + " - " + details.getJSONObject(0).getString("end_time"));
                    tx_cost.setText(details.getJSONObject(0).getString("meal2") + " : Meal for two(approx)");
                    tc_contact.setText(count.getJSONObject(0).getString("std_code") + "-" + count.getJSONObject(0).getString("number"));
                    phn_number = count.getJSONObject(0).getString("std_code") + count.getJSONObject(0).getString("number");
                    lat = Double.parseDouble(details.getJSONObject(0).getString("latitude"));
                    lang = Double.parseDouble(details.getJSONObject(0).getString("longitude"));


                }
            } catch (Exception e) {

            }
        }
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

        mHandler.postDelayed(runnable, 7000);

    }
}
