package com.pinkstar.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.pinkstar.main.adapter.CityAdapter;
import com.pinkstar.main.adapter.CityNearByAdapter;
import com.pinkstar.main.adapter.HomeAdapter;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CityNearBY extends AppCompatActivity {
    String city;
    JSONObject jsonObject;
    String url = Apis.Base, udata;
    ArrayList<HashMap<String, String>> city_array = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> venderList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> venderList1 = new ArrayList<HashMap<String, String>>();
    ListView citynearby_listView;
    CityNearByAdapter cityNearByAdapter;
    ArrayList<String> tabs = new ArrayList<String>();
    JSONArray jsonArray;
    int j = 0;
TextView tool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        city = getIntent().getExtras().getString("search_selected_city");
        setContentView(R.layout.activity_city_near_by);
        citynearby_listView = (ListView) findViewById(R.id.citynearby_listView);
        tool=(TextView)findViewById(R.id.tool);
        tool.setText(city);
        citynearby_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(CityNearBY.this, HomeDetail.class);
                in.putExtra("company", venderList.get(position).get("company_name"));
                in.putExtra("img_array", venderList.get(position).get("img_array"));
                in.putExtra("unique_id", venderList.get(position).get("unique_id"));

                startActivity(in);
            }
        });
        new AttempVender().execute();
        jsonArray = new JSONArray();
    }

    private class AttempVender extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(CityNearBY.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            StringBuilder strBuilder = new StringBuilder(url);
            strBuilder.append("mobile=" + SaveSharedPreference.getMobile(CityNearBY.this));
            strBuilder.append("&rquest=search_city");
            strBuilder.append("&token_id=" + SaveSharedPreference.getUserID(CityNearBY.this));
            strBuilder.append("&cityname=" + city);
            strBuilder.append("&defaultext" + "");
            strBuilder.append("&index=0");
            strBuilder.append("&lastindex=10");

            // Create an array
            Parser perser = new Parser();
            jsonObject = perser.getJSONFromUrl1(strBuilder.toString());

            try {

                udata = jsonObject.getString("udata");
                JSONArray js = jsonObject.getJSONArray("result");


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
                    map1.put("company_name", info.getString("company_display_name"));
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

                    venderList.add(map1);


                }
            } catch (Exception e) {

                Log.e("Log_Exception", e.toString());
            }


            citynearby_listView.setAdapter(new CityNearByAdapter(CityNearBY.this, venderList));


        }
    }
}
