package com.pinkstar.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pinkstar.main.adapter.BrawseAdapter;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;
import com.pinkstar.main.fragment.OneFragment;
import com.pinkstar.main.fragment.TopUp;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Browser_plas extends FragmentActivity {

    private TabLayout tabLayout;
    private ListView list;
    JSONObject json;
    String url = Apis.Base, operator_code, circle_code;
    ArrayList<HashMap<String, String>> array_topup = new ArrayList<HashMap<String, String>>();
    String type = "TUP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_plas);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            circle_code = getIntent().getExtras().getString("circle");
            operator_code = getIntent().getExtras().getString("operator");
        }

        inIt();

        new AttempTopUP().execute();

    }

    public void inIt() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        list = (ListView) findViewById(R.id.list);

        tabLayout.addTab(tabLayout.newTab().setText("Top-up Recharge"));
        tabLayout.addTab(tabLayout.newTab().setText("Full Talk-time Recharge"));
        tabLayout.addTab(tabLayout.newTab().setText("2G Data Recharge"));
        tabLayout.addTab(tabLayout.newTab().setText("3G/4G Data Recharge"));
        tabLayout.addTab(tabLayout.newTab().setText("SMS Pack Recharge"));
        tabLayout.addTab(tabLayout.newTab().setText("Local/STD/ISD Call Recharge"));
        tabLayout.addTab(tabLayout.newTab().setText("Other Recharge"));
        tabLayout.addTab(tabLayout.newTab().setText("National/International Roaming Recharge"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                tabselect();


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    public void tabselect() {
        if (tabLayout.getSelectedTabPosition() == 0) {

            type = "TUP";
        } else if (tabLayout.getSelectedTabPosition() == 1) {

            type = "FTT";
        } else if (tabLayout.getSelectedTabPosition() == 2) {

            type = "2G";
        } else if (tabLayout.getSelectedTabPosition() == 3) {

            type = "3G";
        } else if (tabLayout.getSelectedTabPosition() == 4) {

            type = "SMS";
        } else if (tabLayout.getSelectedTabPosition() == 5) {

            type = "LSC";
        } else if (tabLayout.getSelectedTabPosition() == 6) {

            type = "OTR";
        } else if (tabLayout.getSelectedTabPosition() == 7) {

            type = "RMG";
        }

        //Dialogs.showCenterToast(Browser_plas.this, type);
        new AttempTopUP().execute();
    }


    private class AttempTopUP extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(Browser_plas.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
            strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));
            strBuilder.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(Browser_plas.this)));
            strBuilder.add(new BasicNameValuePair("rquest", "planOffer"));
            strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(Browser_plas.this)));
            strBuilder.add(new BasicNameValuePair("offer_type", type));
            strBuilder.add(new BasicNameValuePair("circle_code", circle_code));
            strBuilder.add(new BasicNameValuePair("operator_code", operator_code));

            Log.e("Json_list", "" + strBuilder.toString());

            try {
                Parser perser = new Parser();
                json = perser.getJSONFromUrl(Apis.Base, strBuilder);
                Log.e("Json_list", "" + json);


            } catch (Exception e) {
                Log.e("exp", e.toString());

            }


            return null;

        }

        @Override
        protected void onPostExecute(String args) {
            // Locate the listview in listview_main.xml
            Dialogs.dismissDialog();

            try {
                array_topup.clear();
                if (json.getString("uData").equals("1")) {

                    String jString = json.getString("result");
                    JSONArray result = new JSONArray(jString);


                    HashMap<String, String> map;
                    for (int i = 0; i < result.length(); i++) {
                        map = new HashMap<String, String>();
                        JSONObject js = result.getJSONObject(i);


                        map.put("amount", js.getString("Amount"));
                        map.put("validity", js.getString("Validity"));
                        map.put("detail", js.getString("Detail"));

                        array_topup.add(map);


                    }
                }

                list.setAdapter(new BrawseAdapter(Browser_plas.this, array_topup));

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        OneFragment.setAmount(array_topup.get(position).get("amount"));
                        finish();


                    }
                });


            } catch (Exception e) {

                Log.e("exp", e.toString());

            }

        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
