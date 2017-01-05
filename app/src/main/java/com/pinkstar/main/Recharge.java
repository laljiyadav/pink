package com.pinkstar.main;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pinkstar.main.adapter.EstoreAdapter;
import com.pinkstar.main.adapter.HomeAdapter;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;
import com.pinkstar.main.fragment.OneFragment;
import com.pinkstar.main.fragment.ThreeFragment;
import com.pinkstar.main.fragment.TwoFragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Recharge extends FragmentActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    JSONObject json;
    String udata, url = Apis.Base;
    ArrayList<HashMap<String, String>> city_array = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> tempvenderList = new ArrayList<HashMap<String, String>>();
    ArrayList<String> category = new ArrayList<String>();
    ViewPager gridView;
    LinearLayout layout;
    ImageView star_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        inIt();
    }

    public void inIt() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        gridView = (ViewPager) findViewById(R.id.grid);
        layout = (LinearLayout) findViewById(R.id.line);
        star_img = (ImageView) findViewById(R.id.star_img);

        star_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.star_dialog(Recharge.this);
            }
        });

        Dialogs.Touch(Recharge.this, star_img);

        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        new AttempEstore().execute();
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "Mobile");
        adapter.addFrag(new TwoFragment(), "DTH");
        adapter.addFrag(new ThreeFragment(), "Data Card");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private class AttempEstore extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(Recharge.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {


            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


            nameValuePairs.add(new BasicNameValuePair("rquest", "estoreList"));
            nameValuePairs.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(Recharge.this)));
            nameValuePairs.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(Recharge.this)));
            nameValuePairs.add(new BasicNameValuePair("api_token", Apis.Api_Token));



            try {// Create an array
                Parser perser = new Parser();
                json = perser.getJSONFromUrl(url, nameValuePairs);


                udata = json.getString("uData");
                JSONArray jarray = json.getJSONArray("result");

                if (udata.equals("1")) {

                    HashMap<String, String> map;
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        map = new HashMap<String, String>();

                        map.put("name", object.getString("name"));
                        map.put("description", object.getString("description"));
                        map.put("price", object.getString("price"));
                        map.put("id", object.getString("id"));
                        map.put("discount_price", object.getString("discount_price"));
                        map.put("category_id", object.getString("category_id"));
                        map.put("sub_category_id", object.getString("sub_category_id"));
                        map.put("product_quantity", object.getString("product_quantity"));
                        map.put("product_image", testtoimage(object.getString("product_image")));
                        map.put("images", object.getString("product_image"));

                        if (category.contains(object.getString("category_id"))) {
                        } else {
                            category.add(object.getString("category_id"));
                        }

                        city_array.add(map);
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
            createText();

            EstoreAdapter estoreAdapter = new EstoreAdapter(Recharge.this, city_array);
            gridView.setAdapter(estoreAdapter);
            sort(category.get(0));


        }
    }

    public void createText() {
        for (int p = 0; p < category.size(); p++) {
            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            // Create TextView
            final TextView product = new TextView(this);
            product.setId(p + 1);
            product.setHeight(40);
            product.setWidth(250);
            product.setText(category.get(p));
            product.setGravity(Gravity.CENTER_VERTICAL);
            product.setPadding(20, 0, 20, 0);
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

                    sort(category.get(index));
                }
            });

            //Add button to LinearLayout
            ll.addView(btn);
            //Add button to LinearLayout defined in XML
            layout.addView(ll);
        }


    }

    public void sort(String txt) {

        String searchString = txt;
        tempvenderList.clear();
        for (int i = 0; i < city_array.size(); i++) {
            String playerName = city_array.get(i).get("category_id").toString();

            if (playerName.equals(searchString)) {
                tempvenderList.add(city_array.get(i));
            }


        }
        EstoreAdapter estoreAdapter = new EstoreAdapter(Recharge.this, tempvenderList);
        gridView.setAdapter(estoreAdapter);


    }

    public String testtoimage(String image) {


        String array[] = image.split(",");


        return array[0];
    }
}
