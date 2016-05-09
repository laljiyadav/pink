package com.pinkstar.main;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.pinkstar.main.adapter.CityAdapter;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Search extends Activity {

    String url = Apis.Base, udata;
    JSONObject json;
    ArrayList<HashMap<String, String>> city_array = new ArrayList<HashMap<String, String>>();
    ListView city_list;
    CityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        city_list = (ListView) findViewById(R.id.city_list);

        new AttempSearch().execute();


    }


    private class AttempSearch extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(Search.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            // http://pinkstarapp.com/api/restservices.php?email=%@&password=%@&rquest=email_signup
            StringBuilder strBuilder = new StringBuilder(url);
            strBuilder.append("rquest=city_list");

            Log.e("Log_tag", "" + strBuilder.toString());

            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl1(strBuilder.toString());
            Log.e("Log_tag", "" + json);
            try {

                udata = json.getString("udata");
                JSONArray jarray = json.getJSONArray("result");
                if (udata.equals("1")) {

                    HashMap<String, String> map;
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        map = new HashMap<String, String>();

                        map.put("city", object.getString("CityName"));
                        map.put("citycount", object.getString("CityCount"));
                        map.put("cityloc", object.getString("CityLoc"));

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

            adapter = new CityAdapter(Search.this, city_array);
            city_list.setAdapter(adapter);


        }
    }
}
