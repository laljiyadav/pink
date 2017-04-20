package com.pinkstar.main;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pinkstar.main.adapter.OfferAdapter;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.GPSTracker;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import me.relex.circleindicator.CircleIndicator;

public class HomeDetail extends Activity implements View.OnClickListener {

    ViewPager viwe_pager;
    TextView tx_company, tx_address, tx_address1, tx_cash, tx_type, tx_cost, tc_contact, tx_time;
    RatingBar rating;
    ArrayList<HashMap<String, String>> offerList = new ArrayList<HashMap<String, String>>();
    String company, img_array, unique_id, udata, url = Apis.Base, phn_number;
    OfferAdapter offerAdapter;
    private Handler mHandler;
    Runnable runnable;
    int position;
    JSONObject json, obj;
    double lat, lang;
    Button phn, route;
    Handler mHandler2;
    int position1 = 0;
    public static String encodeString = "";
    ImageView bill_img;
    GPSTracker gpsTracker;
    Location location;
    ImageView tap_imageView;
    String star_type, dis_amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);

        inIt();


    }


    public void inIt() {
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
        bill_img = (ImageView) findViewById(R.id.bill_img);

        if (!SaveSharedPreference.getfirst(HomeDetail.this).equals("1")) {
            dialog();
        }
        bill_img.setImageResource(R.drawable.star1);
        bill_img.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        unique_id = getIntent().getExtras().getString("unique_id");


        new AttempDetails().execute();

        route.setOnClickListener(this);
        phn.setOnClickListener(this);
        bill_img.setOnClickListener(this);


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
                in.putExtra("company", tx_company.getText().toString());
                in.putExtra("add", tx_address.getText().toString());

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
                Log.e("Exception ", ": " + e);
            }

        }

        if (v == bill_img) {
            Intent in = new Intent(HomeDetail.this, CameraActivity.class);
            in.putExtra("type", star_type);
            in.putExtra("amount", dis_amount);
            in.putExtra("id", unique_id);
            in.putExtra("print", tx_type.getText().toString());
            startActivity(in);
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

            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
            strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));
            strBuilder.add(new BasicNameValuePair("rquest", "vendorDetail"));
            strBuilder.add(new BasicNameValuePair("vendor_id", unique_id));
            strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(HomeDetail.this)));


            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl(url, strBuilder);
            Log.e("json", "" + json);
            try {

                udata = json.getString("uData");
                obj = json.getJSONObject("result");


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

                    tx_address.setText(obj.getString("address_second"));
                    tx_address1.setText(obj.getString("address") + "," + obj.getString("address_second")
                            + "," + obj.getString("city") + "," + obj.getString("state")
                            + "," + obj.getString("country") + "," + obj.getString("pincode"));
                    tx_cash.setText(obj.getString("maxstar"));
                    tx_type.setText(obj.getString("bill_type"));
                    tx_time.setText(obj.getString("start_time") + " - " + obj.getString("end_time"));
                    tx_cost.setText(obj.getString("meal2") + " : Meal for two(approx)");
                    tc_contact.setText(obj.getString("phone"));
                    phn_number = obj.getString("phone");
                    if (obj.has("type"))
                        star_type = obj.getString("type");
                    rating.setRating(Float.valueOf(obj.getString("rating")));
                    if (obj.has("vdiscount_amount"))
                        dis_amount = obj.getString("vdiscount_amount");
                    lat = Double.parseDouble(obj.getString("lat"));
                    lang = Double.parseDouble(obj.getString("long"));
                    tx_company.setText(obj.getString("company_display_name"));
                    img_array = "" + obj.getJSONArray("image");

                    offer(img_array);

                }

            } catch (Exception e) {

                Log.e("exp", e.toString());

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
        Log.e("encode", encodeString);
        check();

    }

    public void check() {
        if (ContextCompat.checkSelfPermission(HomeDetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            permisition(HomeDetail.this, "Allow Call Permission", "Please allow the call permission for better featured ", 4);

        } else if (ContextCompat.checkSelfPermission(HomeDetail.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            permisition(HomeDetail.this, "Allow Camera Permission", "Please allow the camera permission for better featured ", 4);
        }

    }


    public static void permisition(final Context context, String txt_val, String txt_val1, final int request) {


        final Dialog dialog2 = new Dialog(context);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog2.setContentView(R.layout.permistion);
        dialog2.setCancelable(false);
        WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
        Window window = dialog2.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        lp.gravity = Gravity.CENTER;


        TextView txt = (TextView) dialog2.findViewById(R.id.txt_text);
        TextView txt1 = (TextView) dialog2.findViewById(R.id.txt_text1);

        final Button dont = (Button) dialog2.findViewById(R.id.dont);
        final Button allow = (Button) dialog2.findViewById(R.id.allow);


        txt.setText(Html.fromHtml(txt_val));
        txt1.setText(txt_val1);


        dont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
                dialog2.dismiss();

            }
        });


        dialog2.show();


    }


    public void offer(String img_array) {
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

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);

        indicator.setViewPager(viwe_pager);


    }

    public void dialog() {
        final Dialog dialog2 = new Dialog(HomeDetail.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#90000000")));
        dialog2.setContentView(R.layout.details_dialog);
        WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
        lp.dimAmount = 0.0f;
        dialog2.setCancelable(false);
        Window window = dialog2.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        lp.gravity = Gravity.CENTER;
        RelativeLayout layout = (RelativeLayout) dialog2.findViewById(R.id.reletive);
        tap_imageView = (ImageView) dialog2.findViewById(R.id.img_hand);
        mHandler2 = new Handler();
        mHandler2.postDelayed(runnable2, 2000);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveSharedPreference.setfirst(HomeDetail.this, "1");
                dialog2.dismiss();
                mHandler2.removeCallbacks(runnable2);
            }
        });

        dialog2.show();
    }

    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            Log.e("runnable ", "in");

            if (position1 == 0) {
                tap_imageView.setBackgroundResource(R.drawable.handicon1);
            } else if (position1 == 1) {
                tap_imageView.setBackgroundResource(R.drawable.handicon2);

            } else if (position1 == 2) {
                tap_imageView.setBackgroundResource(R.drawable.hand_icon3);

            } else if (position1 == 3) {
                position1 = 0;
                tap_imageView.setBackgroundResource(R.drawable.handicon1);

            }
            mHandler2.postDelayed(runnable2, 2000);
            position1++;


        }
    };
}
