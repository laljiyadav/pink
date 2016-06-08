package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pinkstar.main.adapter.HomeAdapter;
import com.pinkstar.main.adapter.OfferAdapter;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.GPSTracker;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {

    String url = Apis.Base, udata;
    JSONObject json;
    ImageView star_img;
    OfferAdapter offerAdapter;
    HomeAdapter homeAdapter;
    ViewPager img_pager;
    ArrayList<HashMap<String, String>> offerList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> venderList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> venderList1 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> tempvenderList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> tempvenderList1 = new ArrayList<HashMap<String, String>>();
    ArrayList<String> tabs = new ArrayList<String>();
    private Handler mHandler;
    Runnable runnable;
    int position;
    ListView list, list1;
    JSONArray jsonArray;
    int j = 0;
    LinearLayout top_tabs;
    GPSTracker gpsTracker;
    boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        star_img = (ImageView) findViewById(R.id.star_img);
        jsonArray = new JSONArray();

        top_tabs = (LinearLayout) findViewById(R.id.tabs);


        list = (ListView) findViewById(R.id.list);
        list1 = (ListView) findViewById(R.id.list1);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (flag) {

                    Intent in = new Intent(MainActivity.this, HomeDetail.class);
                    in.putExtra("company", tempvenderList.get(position).get("company_name"));
                    in.putExtra("img_array", tempvenderList.get(position).get("img_array"));
                    in.putExtra("unique_id", tempvenderList.get(position).get("unique_id"));

                    startActivity(in);
                } else {
                    Intent in = new Intent(MainActivity.this, HomeDetail.class);
                    in.putExtra("company", venderList.get(position).get("company_name"));
                    in.putExtra("img_array", venderList.get(position).get("img_array"));
                    in.putExtra("unique_id", venderList.get(position).get("unique_id"));

                    startActivity(in);
                }
            }
        });
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (flag) {

                    Intent in = new Intent(MainActivity.this, HomeDetail.class);
                    in.putExtra("company", tempvenderList1.get(position).get("company_name"));
                    in.putExtra("img_array", tempvenderList1.get(position).get("img_array"));
                    in.putExtra("unique_id", tempvenderList1.get(position).get("unique_id"));

                    startActivity(in);
                } else {
                    Intent in = new Intent(MainActivity.this, HomeDetail.class);
                    in.putExtra("company", venderList1.get(position).get("company_name"));
                    in.putExtra("img_array", venderList1.get(position).get("img_array"));
                    in.putExtra("unique_id", venderList1.get(position).get("unique_id"));
                    startActivity(in);
                }
            }
        });

        star_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.star_dialog(MainActivity.this);
            }
        });


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
        Location location = gpsTracker.getLocation();

        if (location == null) {
            Dialogs.alertDialog(MainActivity.this, "Allow &#34;Pink Star&#34; to access your location while you use this app?", "Location is required", "Don't Allow", "Allow");
        } else {
            new AttempProfile().execute();
        }


    }


    private class AttempProfile extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(MainActivity.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            StringBuilder strBuilder = new StringBuilder(url);
            strBuilder.append("token_id=" + SaveSharedPreference.getUserID(MainActivity.this));
            strBuilder.append("&rquest=get_profile_details");
            strBuilder.append("&mobile=" + SaveSharedPreference.getMobile(MainActivity.this));


            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl1(strBuilder.toString());

            Log.e("JSon",""+json);
            try {

                udata = json.getString("udata");
                JSONArray jarray = json.getJSONArray("result");
                if (udata.equals("1")) {
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject js = jarray.getJSONObject(i);
                        SaveSharedPreference.setUserName(MainActivity.this, js.getString("first_name"));
                        SaveSharedPreference.setLastName(MainActivity.this,js.getString("last_name"));
                        SaveSharedPreference.setUserEMAIL(MainActivity.this, js.getString("email"));
                        SaveSharedPreference.setBirth(MainActivity.this, js.getString("dob"));
                        SaveSharedPreference.setAnnversary(MainActivity.this, js.getString("anniversary"));
                        SaveSharedPreference.setUserIMAGE(MainActivity.this, js.getString("image_url"));
                        SaveSharedPreference.setTotal(MainActivity.this, js.getString("balance_star"));
                        SaveSharedPreference.setBalStar(MainActivity.this, js.getString("redeemable_star"));
                        SaveSharedPreference.setGender(MainActivity.this, js.getString("gender"));


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

            new AttempOffer().execute();
        }
    }

    private class AttempOffer extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(MainActivity.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            StringBuilder strBuilder = new StringBuilder(url);
            strBuilder.append("rquest=get_offer");


            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl1(strBuilder.toString());

            // Log.e("json", "" + json);
            try {

                udata = json.getString("udata");
                JSONArray js = json.getJSONArray("result");
                HashMap<String, String> map;
                for (int i = 0; i < js.length(); i++) {
                    map = new HashMap<String, String>();
                    JSONObject object = js.getJSONObject(i);
                    map.put("image", object.getString("image_url"));

                    offerList.add(map);

                }


            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            Dialogs.dismissDialog();


            offerAdapter = new OfferAdapter(MainActivity.this, offerList);
            img_pager.setAdapter(offerAdapter);

            new AttempVender().execute();

        }
    }


    private class AttempVender extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(MainActivity.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            StringBuilder strBuilder = new StringBuilder(url);
            strBuilder.append("token_id=" + SaveSharedPreference.getUserID(MainActivity.this));
            strBuilder.append("&rquest=vendor_list");
            strBuilder.append("&mobile=" + SaveSharedPreference.getMobile(MainActivity.this));


            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl1(strBuilder.toString());

            try {

                udata = json.getString("udata");
                JSONArray js = json.getJSONArray("result");


                for (int i = 0; i < js.length(); i++) {


                    JSONObject obj = new JSONObject();
                    // JSONObject object = js.getJSONObject(i);
                    obj.put("info", "" + js.getJSONObject(i));
                    i++;
                    obj.put("discount", "" + js.getJSONObject(i));
                    i++;
                    obj.put("image", "" + js.getJSONObject(i));


                    jsonArray.put(j, obj);


                    j++;
                }


            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            Dialogs.dismissDialog();


            HashMap<String, String> map1;

            try {
                for (int k = 0; k < jsonArray.length(); k++) {
                    map1 = new HashMap<String, String>();

                    JSONObject object = jsonArray.getJSONObject(k);

                    JSONObject info = new JSONObject("" + object.getString("info"));
                    JSONObject discount = new JSONObject("" + object.getString("discount"));
                    JSONObject image = new JSONObject("" + object.getString("image"));

                    JSONArray dis = discount.getJSONArray("discount");
                    JSONArray ima = image.getJSONArray("images");
                    map1.put("company_name", info.getString("company_name"));
                    map1.put("unique_id", info.getString("unique_id"));
                    map1.put("category", info.getString("category"));
                    map1.put("lat", info.getString("latitude"));
                    map1.put("long", info.getString("longitude"));
                    map1.put("ps_discount", dis.getJSONArray(0).getJSONObject(0).getString("ps_discount"));
                    map1.put("ps_vdiscount", dis.getJSONArray(0).getJSONObject(0).getString("ps_vdiscount"));
                    map1.put("img_array", "" + ima);

                    for (int h = 0; h < ima.length(); h++) {
                        if (ima.getJSONObject(h).getString("type").equals("home")) {
                            map1.put("image_url", ima.getJSONObject(h).getString("image_url"));
                        }
                    }

                    if (tabs.contains(info.getString("category"))) {

                    } else {
                        tabs.add(info.getString("category"));
                    }

                    if (k % 2 == 0) {
                        venderList.add(map1);

                    } else {

                        venderList1.add(map1);
                    }


                }
            } catch (Exception e) {

                Log.e("Log_Exception", e.toString());
            }


            homeAdapter = new HomeAdapter(MainActivity.this, venderList);
            list.setAdapter(homeAdapter);
            homeAdapter = new HomeAdapter(MainActivity.this, venderList1);
            list1.setAdapter(homeAdapter);


            createText();
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
        for (int p = 0; p < tabs.size(); p++) {
            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            // Create TextView
            final TextView product = new TextView(this);
            product.setId(p + 1);
            product.setHeight(40);
            product.setWidth(180);
            product.setText(tabs.get(p));
            product.setGravity(Gravity.CENTER);
            product.setPadding(10, 0, 10, 0);
            product.setTextColor(getResources().getColor(R.color.black));
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


                    //Toast.makeText(getApplicationContext(), "" + tabs.get(index), Toast.LENGTH_LONG).show();
                    flag = true;
                    sort(tabs.get(index));
                }
            });

            //Add button to LinearLayout
            ll.addView(btn);
            //Add button to LinearLayout defined in XML
            top_tabs.addView(ll);
        }


    }


    public void sort(String txt) {

        String searchString = txt;
        tempvenderList.clear();
        tempvenderList1.clear();
        for (int i = 0; i < venderList.size(); i++) {
            String playerName = venderList.get(i).get("category").toString();
            String playerName1 = venderList1.get(i).get("category").toString();

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


}






