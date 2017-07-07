package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.pinkstar.main.adapter.EstoreAdapter;
import com.pinkstar.main.adapter.OrderAdapter;
import com.pinkstar.main.adapter.OrderListAdapter;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderActivity extends Activity {

    JSONObject json;
    String udata, url = Apis.Base;
    ArrayList<HashMap<String, String>> city_array = new ArrayList<HashMap<String, String>>();
    ListView listView;
    ImageView star_img;
    TabLayout tabLayout;
    String request = "rechargeHistory";
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        inIt();
    }

    public void inIt() {
        listView = (ListView) findViewById(R.id.order_list);
        star_img = (ImageView) findViewById(R.id.star_img);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Recharge"));
        tabLayout.addTab(tabLayout.newTab().setText("Shopping"));

        star_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.star_dialog(OrderActivity.this,true);
            }
        });
        Dialogs.Touch(OrderActivity.this, star_img);

        new AttempOrder().execute();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabLayout.getSelectedTabPosition() == 0) {

                    page = 0;
                    city_array.clear();
                    request = "rechargeHistory";
                    new AttempOrder().execute();
                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    page = 0;
                    city_array.clear();
                    request = "orderList";
                    new AttempOrder().execute();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {

                int threshold = 1;
                int count = listView.getCount();


                if (scrollState == SCROLL_STATE_IDLE) {
                    if (listView.getLastVisiblePosition() >= count
                            - threshold) {


                        if (city_array.size() == 20) {
                            page++;
                            new AttempOrder().execute();
                        }

                    }
                }

            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (request.equals("rechargeHistory")) {
                    Intent intent = new Intent(OrderActivity.this, RechargeHistoryDetail.class);
                    intent.putExtra("map", city_array.get(position));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(OrderActivity.this, OrderDetail.class);
                    intent.putExtra("map", city_array.get(position));
                    startActivity(intent);
                }
            }
        });

    }

    private class AttempOrder extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(OrderActivity.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {


            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


            nameValuePairs.add(new BasicNameValuePair("rquest", request));
            nameValuePairs.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(OrderActivity.this)));
            nameValuePairs.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(OrderActivity.this)));
            nameValuePairs.add(new BasicNameValuePair("api_token", Apis.Api_Token));
            nameValuePairs.add(new BasicNameValuePair("limit", "20"));
            nameValuePairs.add(new BasicNameValuePair("page", "" + page));

            Log.e("Log_tag", "" + nameValuePairs);


            try {

                // Create an array
                Parser perser = new Parser();
                json = perser.getJSONFromUrl(url, nameValuePairs);

                udata = json.getString("uData");

                Log.e("json", "" + json);

                if (request.equals("rechargeHistory")) {
                    if (udata.equals("1")) {
                        JSONArray jarray = json.getJSONArray("detail");
                        HashMap<String, String> map;
                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject object = jarray.getJSONObject(i);
                            map = new HashMap<String, String>();


                            map.put("order_date", object.getString("re_date"));
                            map.put("order_cost", object.getString("re_amount"));
                            map.put("order_status", object.getString("error_code").replace("\r", ""));
                            map.put("orderid", object.getString("re_orderid"));
                            map.put("image", getImage(object.getString("re_operator")));
                            map.put("name", "Recharge of " + getOperator(object.getString("re_operator")) + " Mobile " + object.getString("re_mobile"));


                            city_array.add(map);
                        }
                    }
                } else {
                    if (udata.equals("1")) {
                        JSONArray jarray = json.getJSONArray("order");
                        HashMap<String, String> map;
                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject object = jarray.getJSONObject(i);
                            map = new HashMap<String, String>();


                            map.put("order_date", object.getString("order_date"));
                            map.put("order_cost", object.getString("order_cost"));
                            map.put("order_status", object.getString("order_status"));
                            map.put("orderid", object.getString("orderid"));
                            map.put("address", object.getString("address"));

                            if (object.getString("product_image").contains(",")) {
                                String img[] = object.getString("product_image").split(",");
                                map.put("image", img[0]);

                            } else {
                                map.put("image", object.getString("product_image"));
                            }
                            map.put("name", object.getJSONArray("product_detail").getJSONObject(0).getString("name"));
                            map.put("des", object.getJSONArray("product_detail").getJSONObject(0).getString("description"));
                            map.put("ship_price", object.getJSONArray("product_detail").getJSONObject(0).getString("shipping_charge"));


                            city_array.add(map);
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

            if (request.equalsIgnoreCase("rechargeHistory")) {
                OrderAdapter estoreAdapter = new OrderAdapter(OrderActivity.this, city_array);
                listView.setAdapter(estoreAdapter);
            } else {
                OrderListAdapter estoreAdapter = new OrderListAdapter(OrderActivity.this, city_array);
                listView.setAdapter(estoreAdapter);
            }


        }
    }


    public static String getOperator(String opera) {

        String operator_name = "";

        try {
            JSONObject object = new JSONObject(Apis.Oprator);
            JSONArray json = object.getJSONArray("Operator");


            for (int i = 0; i < json.length(); i++) {

                if (json.getJSONObject(i).getString("Code").equals(opera)) {
                    operator_name = json.getJSONObject(i).getString("Operator");
                    break;
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("exp", e.toString());
        }


        return operator_name;

    }

    public static String getImage(String opera) {

        String operator_name = "";

        try {
            JSONObject object = new JSONObject(Apis.Oprator);
            JSONArray json = object.getJSONArray("Operator");


            for (int i = 0; i < json.length(); i++) {

                if (json.getJSONObject(i).getString("Code").equals(opera)) {
                    operator_name = json.getJSONObject(i).getString("image");
                    break;
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("exp", e.toString());
        }


        return operator_name;

    }


}
