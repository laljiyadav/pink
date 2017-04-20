package com.pinkstar.main;

import android.app.Activity;
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
import android.view.View;
import android.view.WindowManager;

import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.fragment.TopUp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Browser_plas extends FragmentActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    JSONObject json;
    String url = Apis.Base, operator_code, circle_code;
    ArrayList<HashMap<String, String>> array_topup = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> array_fulltalk = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> array_sms = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> array_std = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> array_roaming = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> array_two = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> array_three = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_plas);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        circle_code = getIntent().getExtras().getString("circle");
        operator_code = getIntent().getExtras().getString("operator");

        new AttempTopUP().execute();

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);
        Log.e("item", "" + viewPager.getCurrentItem());
    }

    private class AttempTwoG extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {

            // http://pinkstarapp.com/api/restservices.php?rquest=plan_list&operator=28&circle=17&plancode=2G
            StringBuilder builder = new StringBuilder(url);
            builder.append("rquest=plan_list");
            builder.append("&operator=" + operator_code);
            builder.append("&circle=" + circle_code);
            builder.append("&plancode=2G");

            try {
                Parser perser = new Parser();
                json = perser.getJSONFromUrl1(builder.toString());
                Log.e("Json_list", "" + json);
                JSONArray result = json.getJSONArray("result");


                HashMap<String, String> map;
                for (int i = 0; i < result.length(); i++) {
                    map = new HashMap<String, String>();
                    JSONObject js = result.getJSONObject(i);

                    map.put("amount", js.getString("Amount"));
                    map.put("validity", js.getString("Validity"));
                    map.put("detail", js.getString("Detail"));


                    array_two.add(map);

                }


            } catch (Exception e) {

            }


            return null;

        }

        @Override
        protected void onPostExecute(String args) {
            // Locate the listview in listview_main.xml
            //Dialogs.dismissDialog();

            new AttempThreeG().execute();

        }
    }

    private class AttempTopUP extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(Browser_plas.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            // http://pinkstarapp.com/api/restservices.php?rquest=plan_list&operator=28&circle=17&plancode=2G
            StringBuilder builder = new StringBuilder(url);
            builder.append("rquest=plan_list");
            builder.append("&operator=" + operator_code);
            builder.append("&circle=" + circle_code);

            try {
                Parser perser = new Parser();
                json = perser.getJSONFromUrl1(builder.toString());
                Log.e("Json_list", "" + json);
                Log.e("url", "" + builder.toString());
                JSONArray result = json.getJSONArray("result");


                HashMap<String, String> map;
                for (int i = 0; i < result.length(); i++) {
                    map = new HashMap<String, String>();
                    JSONObject js = result.getJSONObject(i);

                    if (js.getString("Detail").contains("Talktime")) {

                        map.put("amount", js.getString("Amount"));
                        map.put("validity", js.getString("Validity"));
                        map.put("detail", js.getString("Detail"));

                        array_topup.add(map);
                    }

                    if (js.getString("Detail").contains("Full Talktime")) {

                        map.put("amount", js.getString("Amount"));
                        map.put("validity", js.getString("Validity"));
                        map.put("detail", js.getString("Detail"));

                        array_fulltalk.add(map);
                    }

                    if (js.getString("Detail").contains("SMS")) {

                        map.put("amount", js.getString("Amount"));
                        map.put("validity", js.getString("Validity"));
                        map.put("detail", js.getString("Detail"));

                        array_sms.add(map);
                    }

                    if (js.getString("Detail").contains("Local")) {

                        map.put("amount", js.getString("Amount"));
                        map.put("validity", js.getString("Validity"));
                        map.put("detail", js.getString("Detail"));

                        array_std.add(map);
                    }
                    if (js.getString("Detail").contains("std")) {

                        map.put("amount", js.getString("Amount"));
                        map.put("validity", js.getString("Validity"));
                        map.put("detail", js.getString("Detail"));

                        array_std.add(map);
                    }

                    if (js.getString("Detail").contains("Roaming")) {

                        map.put("amount", js.getString("Amount"));
                        map.put("validity", js.getString("Validity"));
                        map.put("detail", js.getString("Detail"));

                        array_roaming.add(map);
                    }


                }


            } catch (Exception e) {

            }


            return null;

        }

        @Override
        protected void onPostExecute(String args) {
            // Locate the listview in listview_main.xml
            // Dialogs.dismissDialog();

            new AttempTwoG().execute();


        }
    }

    private class AttempThreeG extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {

            // http://pinkstarapp.com/api/restservices.php?rquest=plan_list&operator=28&circle=17&plancode=2G
            StringBuilder builder = new StringBuilder(url);
            builder.append("rquest=plan_list");
            builder.append("&operator="+operator_code);
            builder.append("&circle="+circle_code);
            builder.append("&plancode=3G");

            try {
                Parser perser = new Parser();
                json = perser.getJSONFromUrl1(builder.toString());
                Log.e("Json_list", "" + json);
                JSONArray result = json.getJSONArray("result");


                HashMap<String, String> map;
                for (int i = 0; i < result.length(); i++) {
                    map = new HashMap<String, String>();
                    JSONObject js = result.getJSONObject(i);

                    map.put("amount", js.getString("Amount"));
                    map.put("validity", js.getString("Validity"));
                    map.put("detail", js.getString("Detail"));


                    array_three.add(map);

                }


            } catch (Exception e) {

            }


            return null;

        }

        @Override
        protected void onPostExecute(String args) {
            // Locate the listview in listview_main.xml
            Dialogs.dismissDialog();

            setupViewPager(viewPager);
            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);


        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(final int position) {
            switch (position) {
                case 0:
                    return new TopUp().newInstance(0, array_topup, new TopUp());
                case 1:
                    return new TopUp().newInstance(1, array_two, new TopUp());
                case 2:
                    return new TopUp().newInstance(0, array_three, new TopUp());
                case 3:
                    return new TopUp().newInstance(0, array_fulltalk, new TopUp());
                case 4:
                    return new TopUp().newInstance(0, array_sms, new TopUp());
                case 5:
                    return new TopUp().newInstance(0, array_std, new TopUp());
                case 6:
                    return new TopUp().newInstance(0, array_roaming, new TopUp());

                default:
                    break;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 7;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            if (object != null) {
                return ((Fragment) object).getView() == view;
            } else {
                return false;
            }
        }

        @Override
        public CharSequence getPageTitle(final int position) {
            switch (position) {
                case 0:
                    return "Top Up";
                case 1:
                    return "2G Data";
                case 2:
                    return "3G/4G Data";
                case 3:
                    return "Full Talktime";
                case 4:
                    return "SMS Packs";
                case 5:
                    return "Local/ISD Calls";
                case 6:
                    return "Roaming";
                default:
                    break;
            }
            return null;
        }
    }
}
