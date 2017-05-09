package com.pinkstar.main;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.pinkstar.main.adapter.NotificationAdapter;
import com.pinkstar.main.adapter.OrderAdapter;
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

public class Notifition extends Activity {

    JSONObject json;
    String udata, url = Apis.Base;
    ArrayList<HashMap<String, String>> city_array = new ArrayList<HashMap<String, String>>();
    ListView listView;
    ImageView star_img;
    String title[] = {"Stars Offer", "Estore Offer", "Order Confirmed", "Recharge Successfully"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);

        inIt();

    }

    public void inIt() {
        listView = (ListView) findViewById(R.id.order_list);
        star_img = (ImageView) findViewById(R.id.star_img);

        star_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.star_dialog(Notifition.this);
            }
        });
        Dialogs.Touch(Notifition.this, star_img);

        HashMap<String, String> map;
        for (int i = 0; i < title.length; i++) {

            map = new HashMap<String, String>();


            map.put("name", title[i]);


            city_array.add(map);
        }

        listView.setAdapter(new NotificationAdapter(Notifition.this, city_array));

    }


    private class AttempOrder extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(Notifition.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {


            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


            nameValuePairs.add(new BasicNameValuePair("rquest", "orderList"));
            nameValuePairs.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(Notifition.this)));
            nameValuePairs.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(Notifition.this)));
            nameValuePairs.add(new BasicNameValuePair("api_token", Apis.Api_Token));

            Log.e("Log_tag", "" + nameValuePairs);


            try {

                // Create an array
                Parser perser = new Parser();
                json = perser.getJSONFromUrl(url, nameValuePairs);

                udata = json.getString("uData");

                Log.e("json", "" + json);


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
                        map.put("image", object.getString("product_image"));
                        map.put("name", object.getJSONArray("product_detail").getJSONObject(0).getString("name"));


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

            OrderAdapter estoreAdapter = new OrderAdapter(Notifition.this, city_array);
            listView.setAdapter(estoreAdapter);


        }
    }
}
