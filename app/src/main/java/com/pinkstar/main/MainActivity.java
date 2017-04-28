package com.pinkstar.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pinkstar.main.adapter.HomeAdapter;
import com.pinkstar.main.adapter.HomeOfferAdapter;
import com.pinkstar.main.adapter.OfferAdapter;
import com.pinkstar.main.adapter.SubcatAdapter;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.GPSTracker;
import com.pinkstar.main.data.InternetStatus;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends Activity {

    String url = Apis.Base, udata, token;
    JSONObject json, json1;
    ImageView star_img;
    HomeAdapter homeAdapter;
    ViewPager img_pager;
    ArrayList<HashMap<String, String>> offerList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> venderList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> venderList1 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> tempvenderList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> tempvenderList1 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> cate_list = new ArrayList<HashMap<String, String>>();
    HashMap<String, ArrayList<String>> subArray = new HashMap<String, ArrayList<String>>();
    HashMap<String, ArrayList<String>> subArrayid = new HashMap<String, ArrayList<String>>();
    ArrayList<String> namesub;
    ArrayList<String> idsub;
    private Handler mHandler;
    Runnable runnable;
    Handler mHandler1;
    int position1 = 0;
    int position;
    ListView list, list1;
    LinearLayout top_tabs;
    GPSTracker gpsTracker;
    Location location;
    Spinner subcat;
    int page = 0;
    String cate_id = "", cate_name = "";
    boolean flag = false;
    ImageView tap_imageView;
    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        star_img = (ImageView) findViewById(R.id.star_img);
        top_tabs = (LinearLayout) findViewById(R.id.tabs);
        list = (ListView) findViewById(R.id.list);
        list1 = (ListView) findViewById(R.id.list1);
        frameLayout = (FrameLayout) findViewById(R.id.lay_pager);
        subcat = (Spinner) findViewById(R.id.subcat);
        mHandler1 = new Handler();
        FirebaseMessaging.getInstance().subscribeToTopic("PinkStar");
        token = FirebaseInstanceId.getInstance().getToken();

        Log.e("token","abc"+ token);

        if (!SaveSharedPreference.gethomefirst(MainActivity.this).equals("1")) {
            dialog();
        }


        subcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (flag) {
                    if (subArrayid.get(cate_id).get(position).equalsIgnoreCase("-1")) {
                        homeAdapter = new HomeAdapter(MainActivity.this, venderList);
                        list.setAdapter(homeAdapter);
                        homeAdapter = new HomeAdapter(MainActivity.this, venderList1);
                        list1.setAdapter(homeAdapter);

                    } else {

                        sort("" + subArrayid.get(cate_id).get(position));

                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent in = new Intent(MainActivity.this, HomeDetail.class);
                in.putExtra("company", venderList.get(position).get("company_display_name"));
                in.putExtra("img_array", venderList.get(position).get("img_array"));
                in.putExtra("unique_id", venderList.get(position).get("unique_id"));

                startActivity(in);
            }

        });
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent in = new Intent(MainActivity.this, HomeDetail.class);
                in.putExtra("company", venderList1.get(position).get("company_display_name"));
                in.putExtra("img_array", venderList1.get(position).get("img_array"));
                in.putExtra("unique_id", venderList1.get(position).get("unique_id"));
                startActivity(in);
            }


        });

        star_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.star_dialog(MainActivity.this);
            }
        });

        Dialogs.Touch(MainActivity.this, star_img);

        img_pager = (ViewPager) findViewById(R.id.image_pager);


        mHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                if (position >= offerList.size()) {
                    position = 0;
                } else {
                    position = position + 1;
                }
                img_pager.setCurrentItem(position, true);
                mHandler.postDelayed(runnable, 7000);

            }
        };

        gpsTracker = new GPSTracker(MainActivity.this);
        location = gpsTracker.getLocation();

        if (location == null) {
            Dialogs.alertDialog(MainActivity.this, "Allow &#34;Pink Star&#34; to access your location while you use this app?", "Location is required", "Don't Allow", "Allow");
        } else {
            if (InternetStatus.isConnectingToInternet(MainActivity.this)) {
                new AttempRegister().execute();
                gpsTracker = new GPSTracker(MainActivity.this);
                location = gpsTracker.getLocation();
            } else {
                showDialog("No Internet Connection");
            }
        }


    }


    private class AttempRegister extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(MainActivity.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
            strBuilder.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(MainActivity.this)));
            strBuilder.add(new BasicNameValuePair("rquest", "userDeviceToken"));
            strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(MainActivity.this)));
            strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));
            strBuilder.add(new BasicNameValuePair("device_token", token));


            // Create an array


            try {
                Parser perser = new Parser();
                json = perser.getJSONFromUrl(url, strBuilder);

            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {

            Log.e("tokenlog", "" + json);

            try {
                if (json.getString("uData").equals("6")) {
                    Intent intent = new Intent(MainActivity.this, Mobile.class);
                    startActivity(intent);
                } else {
                    new AttempProfile().execute();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private class AttempProfile extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Dialogs.showProDialog(MainActivity.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
            strBuilder.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(MainActivity.this)));
            strBuilder.add(new BasicNameValuePair("rquest", "profileDetail"));
            strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(MainActivity.this)));
            strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));


            // Create an array


            try {
                Parser perser = new Parser();
                json = perser.getJSONFromUrl(url, strBuilder);

                Log.e("json", "" + json);

                udata = json.getString("uData");
                if (udata.equals("1")) {
                    SaveSharedPreference.setUserName(MainActivity.this, json.getJSONObject("result").getString("first_name"));
                    SaveSharedPreference.setLastName(MainActivity.this, json.getJSONObject("result").getString("last_name"));
                    SaveSharedPreference.setUserEMAIL(MainActivity.this, json.getJSONObject("result").getString("email"));
                    SaveSharedPreference.setBirth(MainActivity.this, json.getJSONObject("result").getString("dob"));
                    SaveSharedPreference.setAnnversary(MainActivity.this, json.getJSONObject("result").getString("anniversary"));
                    SaveSharedPreference.setUserIMAGE(MainActivity.this, json.getJSONObject("result").getString("image_url"));
                    SaveSharedPreference.setBalStar(MainActivity.this, json.getJSONObject("star").getString("redeemable_star"));
                    SaveSharedPreference.setTotal(MainActivity.this, json.getJSONObject("star").getString("balance_star"));
                    SaveSharedPreference.setGender(MainActivity.this, json.getJSONObject("result").getString("gender"));


                }


            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {

            new AttempOffer().execute();
        }
    }

    private class AttempOffer extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
            strBuilder.add(new BasicNameValuePair("rquest", "allOffer"));
            strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));


            // Create an array


            try {
                Parser perser = new Parser();
                json = perser.getJSONFromUrl(url, strBuilder);

                //Log.e("json",""+json);

                udata = json.getString("uData");
                if (udata.equals("1")) {
                    JSONArray js = json.getJSONArray("result");
                    HashMap<String, String> map;
                    for (int i = 0; i < js.length(); i++) {
                        map = new HashMap<String, String>();
                        JSONObject object = js.getJSONObject(i);

                        map.put("image", testtoimage(i, object.getString("image")));
                        map.put("images", object.getString("image"));
                        map.put("type", object.getString("offer_type"));
                        map.put("des1", object.getString("description1"));
                        map.put("des2", object.getString("description2"));
                        map.put("des3", object.getString("description3"));
                        map.put("title", object.getString("title"));
                        map.put("lat", object.getString("latitude"));
                        map.put("long", object.getString("longitude"));
                        map.put("address", object.getString("address"));
                        map.put("phone", object.getString("phone"));
                        map.put("email", object.getString("email"));
                        map.put("start", object.getString("start_date"));
                        map.put("end", object.getString("end_date"));
                        map.put("product_id", object.getString("product_id"));


                        offerList.add(map);


                    }
                }


            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            if (udata.equals("1")) {

                img_pager.setAdapter(new HomeOfferAdapter(MainActivity.this, offerList));
                CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
                indicator.setViewPager(img_pager);
            }
            if (udata.equals("15")) {
                frameLayout.setVisibility(View.GONE);
            }

            new AttempVender().execute();

        }
    }


    private class AttempVender extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
            strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));
            strBuilder.add(new BasicNameValuePair("rquest", "featuredVendorList"));
            strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(MainActivity.this)));
            strBuilder.add(new BasicNameValuePair("lat", "" + location.getLatitude()));
            strBuilder.add(new BasicNameValuePair("long", "" + location.getLongitude()));
            strBuilder.add(new BasicNameValuePair("page", "" + page));


            try {

                // Create an array
                Parser perser = new Parser();
                json = perser.getJSONFromUrl(url, strBuilder);

                // Log.e("jsonven",""+json);
                Log.e("jsonven", "" + strBuilder.toString());

                udata = json.getString("uData");


                if (udata.equals("1")) {
                    JSONArray category = json.getJSONObject("result").getJSONArray("category");


                    HashMap<String, String> map;
                    map = new HashMap<String, String>();
                    map.put("id", "0");
                    map.put("name", "Featured");
                    cate_list.add(map);


                    for (int i = 0; i < category.length(); i++) {
                        map = new HashMap<String, String>();
                        map.put("id", category.getJSONObject(i).getString("id"));
                        map.put("name", category.getJSONObject(i).getString("name"));
                        cate_list.add(map);
                        JSONArray jsub = category.getJSONObject(i).getJSONArray("subcat");

                        namesub = new ArrayList<String>();
                        idsub = new ArrayList<String>();

                        namesub.add("All");
                        idsub.add("-1");
                        for (int j = 0; j < jsub.length(); j++) {

                            namesub.add(jsub.getJSONObject(j).getString("name"));
                            idsub.add(jsub.getJSONObject(j).getString("id"));


                        }
                        subArray.put(category.getJSONObject(i).getString("name"), namesub);
                        subArrayid.put(category.getJSONObject(i).getString("id"), idsub);


                    }


                }


            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            Dialogs.dismissDialog();


            homeAdapter = new HomeAdapter(MainActivity.this, venderList);
            list.setAdapter(homeAdapter);
            homeAdapter = new HomeAdapter(MainActivity.this, venderList1);
            list1.setAdapter(homeAdapter);


            createText();
            Featured();


        }
    }

    private class AttempCateVender extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(MainActivity.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
            strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));
            strBuilder.add(new BasicNameValuePair("rquest", "getVendorCategoryList"));
            strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(MainActivity.this)));
            strBuilder.add(new BasicNameValuePair("lat", "" + location.getLatitude()));
            strBuilder.add(new BasicNameValuePair("long", "" + location.getLongitude()));
            strBuilder.add(new BasicNameValuePair("page", "" + page));
            strBuilder.add(new BasicNameValuePair("category", cate_id));


            // Create an array

            try {

                Parser perser = new Parser();
                json1 = perser.getJSONFromUrl(url, strBuilder);


                udata = json1.getString("uData");


                if (udata.equals("1")) {

                    //JSONArray js1 = json1.getJSONObject("result").getJSONArray("featured");

                    HashMap<String, String> map1;
                    /*for (int i = 0; i < js1.length(); i++) {
                        map1 = new HashMap<String, String>();
                        JSONObject object = js1.getJSONObject(i);
                        JSONArray image = object.getJSONArray("image");

                        map1.put("unique_id", object.getString("unique_id"));
                        map1.put("company_display_name", object.getString("company_display_name"));
                        map1.put("discount_amount", object.getString("discount_amount"));
                        map1.put("vdiscount_amount", object.getString("vdiscount_amount"));
                        map1.put("lat", object.getString("lat"));
                        map1.put("subcategory", object.getString("subcategory"));
                        map1.put("long", object.getString("long"));
                        map1.put("img_array", "" + image);

                        for (int h = 0; h < image.length(); h++) {
                            if (image.getJSONObject(h).getString("type").equals("home")) {
                                map1.put("image_url", image.getJSONObject(h).getString("image_url"));
                            }
                        }

                        if (i % 2 == 0) {
                            venderList.add(map1);

                        } else {

                            venderList1.add(map1);
                        }

                    }*/


                    JSONArray js = json1.getJSONObject("result").getJSONArray("vendor");
                    for (int i = 0; i < js.length(); i++) {
                        map1 = new HashMap<String, String>();
                        JSONObject object = js.getJSONObject(i);
                        JSONArray image = object.getJSONArray("image");

                        map1.put("unique_id", object.getString("unique_id"));
                        map1.put("company_display_name", object.getString("company_display_name"));
                        map1.put("discount_amount", object.getString("discount_amount"));
                        map1.put("vdiscount_amount", object.getString("vdiscount_amount"));
                        map1.put("subcategory", object.getString("subcategory"));
                        map1.put("lat", object.getString("lat"));
                        map1.put("long", object.getString("long"));
                        map1.put("img_array", "" + image);

                        for (int h = 0; h < image.length(); h++) {
                            if (image.getJSONObject(h).getString("type").equals("home")) {
                                map1.put("image_url", image.getJSONObject(h).getString("image_url"));
                            }
                        }

                        if (i % 2 == 0) {
                            venderList1.add(map1);

                        } else {

                            venderList.add(map1);
                        }

                    }
                }


            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            Dialogs.dismissDialog();


            homeAdapter = new HomeAdapter(MainActivity.this, venderList);
            list.setAdapter(homeAdapter);
            homeAdapter = new HomeAdapter(MainActivity.this, venderList1);
            list1.setAdapter(homeAdapter);
            flag = true;


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
        if (offerList.size() == 0) {
            mHandler.postDelayed(runnable, 7000);
        }
    }

    public void createText() {
        try {
            for (int p = 0; p < cate_list.size(); p++) {
                // Create LinearLayout
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);

                // Create TextView
                final TextView product = new TextView(this);
                product.setId(p + 1);
                product.setHeight(60);
                product.setWidth(250);
                product.setText(cate_list.get(p).get("name"));
                product.setGravity(Gravity.CENTER);
                product.setPadding(10, 0, 10, 0);
                product.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                product.setTextSize(16);

                ll.addView(product);


                final TextView btn = new TextView(this);
                btn.setHeight(50);
                btn.setWidth(4);
                btn.setBackgroundColor(Color.parseColor("#FD6387"));

                final int index = p;
                // Set click listener for button
                product.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {


                        cate_name = cate_list.get(index).get("name");
                        if (cate_name.equalsIgnoreCase("Featured")) {
                            cate_id = "";

                            venderList.clear();
                            venderList1.clear();
                            page = 0;
                            Featured();
                        } else {
                            cate_id = cate_list.get(index).get("id");

                            venderList.clear();
                            venderList1.clear();
                            page = 0;
                            new AttempCateVender().execute();
                            subcat.setAdapter(new SubcatAdapter(MainActivity.this, subArray.get(cate_list.get(index).get("name"))));
                        }
                    }
                });

                ll.addView(btn);
                top_tabs.addView(ll);
            }
        } catch (Exception e) {

        }
    }


    public void sort(String txt) {

        String searchString = txt;
        tempvenderList.clear();
        tempvenderList1.clear();
        for (int i = 0; i < venderList.size(); i++) {
            String playerName = venderList.get(i).get("subcategory").toString();
            String playerName1 = venderList1.get(i).get("subcategory").toString();


            if (playerName.equals(searchString)) {
                tempvenderList.add(venderList.get(i));
            }
            if (playerName1.equals(searchString)) {
                tempvenderList1.add(venderList1.get(i));
            }

        }
        homeAdapter = new HomeAdapter(MainActivity.this, tempvenderList);
        list.setAdapter(homeAdapter);
        homeAdapter = new HomeAdapter(MainActivity.this, tempvenderList1);
        list1.setAdapter(homeAdapter);


    }

    @Override
    public void onBackPressed() {

        finish();

    }

    public void Featured() {

        try {

            udata = json.getString("uData");


            if (udata.equals("1")) {
                JSONArray js = json.getJSONObject("result").getJSONArray("vendor");
                JSONArray category = json.getJSONObject("result").getJSONArray("category");
                // Log.e("logtagArray", "" + category);

                HashMap<String, String> map;
                map = new HashMap<String, String>();
                map.put("id", "0");
                map.put("name", "Featured");
                cate_list.add(map);


                for (int i = 0; i < category.length(); i++) {
                    map = new HashMap<String, String>();
                    map.put("id", category.getJSONObject(i).getString("id"));
                    map.put("name", category.getJSONObject(i).getString("name"));
                    cate_list.add(map);


                }

                HashMap<String, String> map1;
                for (int i = 0; i < js.length(); i++) {
                    map1 = new HashMap<String, String>();
                    JSONObject object = js.getJSONObject(i);
                    JSONArray image = object.getJSONArray("image");

                    map1.put("unique_id", object.getString("unique_id"));
                    map1.put("company_display_name", object.getString("company_display_name"));
                    map1.put("discount_amount", object.getString("discount_amount"));
                    map1.put("vdiscount_amount", object.getString("vdiscount_amount"));
                    map1.put("lat", object.getString("lat"));
                    map1.put("long", object.getString("long"));
                    map1.put("img_array", "" + image);

                    for (int h = 0; h < image.length(); h++) {
                        if (image.getJSONObject(h).getString("type").equals("home")) {
                            map1.put("image_url", image.getJSONObject(h).getString("image_url"));
                        }
                    }

                    if (i % 2 == 0) {
                        venderList.add(map1);

                    } else {

                        venderList1.add(map1);
                    }

                }
            }
            homeAdapter = new HomeAdapter(MainActivity.this, venderList);
            list.setAdapter(homeAdapter);
            homeAdapter = new HomeAdapter(MainActivity.this, venderList1);
            list1.setAdapter(homeAdapter);


        } catch (Exception e) {
            Log.e("Log_Exception", e.toString());

        }
    }


    public void showDialog(String msg) {
        final Dialog dialog2 = new Dialog(MainActivity.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog2.setContentView(R.layout.dia);
        dialog2.setCancelable(false);
        WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
        Window window = dialog2.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        lp.gravity = Gravity.CENTER;

        TextView tv_msg = (TextView) dialog2.findViewById(R.id.masg);
        TextView tv_ok = (TextView) dialog2.findViewById(R.id.ok);

        tv_msg.setText(msg);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InternetStatus.isConnectingToInternet(MainActivity.this)) {
                    dialog2.dismiss();
                    new AttempProfile().execute();

                }
            }
        });
        dialog2.show();
    }


    public String testtoimage(int pos, String image) {

        String str = image.replace("\"", "");
        String first = str.replace("[", "").replace("]", "");

        String array[] = first.split(",");


        return array[0].replace("pk", "pinkstarapp.com");
    }

    //8860837529
    public void dialog() {
        final Dialog dialog2 = new Dialog(MainActivity.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#90000000")));
        dialog2.setContentView(R.layout.home_dialog);
        WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
        lp.dimAmount = 0.0f;
        dialog2.setCancelable(false);
        Window window = dialog2.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        lp.gravity = Gravity.CENTER;
        mHandler1.postDelayed(runnable1, 2000);
        RelativeLayout layout = (RelativeLayout) dialog2.findViewById(R.id.reletive);
        tap_imageView = (ImageView) dialog2.findViewById(R.id.img_hand);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveSharedPreference.sethomefirst(MainActivity.this, "1");
                dialog2.dismiss();
                mHandler1.removeCallbacks(runnable1);
            }
        });


        dialog2.show();


    }

    Runnable runnable1 = new Runnable() {
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
            mHandler1.postDelayed(runnable1, 2000);
            position1++;


        }
    };

}






