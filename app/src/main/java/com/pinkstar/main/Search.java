package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.pinkstar.main.adapter.CityAdapter;
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

public class Search extends Activity {

    String url = Apis.Base, udata;
    JSONObject json;
    ArrayList<HashMap<String, String>> city_array = new ArrayList<HashMap<String, String>>();
    ListView city_list;
    CityAdapter adapter;
    GPSTracker gpsTracker;
    Location location;
    ImageView star_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        inIt();
    }

    public void inIt() {
        gpsTracker = new GPSTracker(Search.this);
        location = gpsTracker.getLocation();


        city_list = (ListView) findViewById(R.id.city_list);
        star_img = (ImageView) findViewById(R.id.star_img);


        star_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.star_dialog(Search.this);
            }
        });


        Dialogs.Touch(Search.this, star_img);

        city_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (location == null) {
                    Dialogs.alertDialog(Search.this, "Allow &#34;Pink Star&#34; to access your location while you use this app?", "Location is required", "Don't Allow", "Allow");


                } else {
                    Intent nearbycityIntent = new Intent(Search.this, CityNearBY.class);
                    nearbycityIntent.putExtra("search_selected_city", city_array.get(position).get("city"));
                    startActivity(nearbycityIntent);
                }
            }
        });
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

            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
            strBuilder.add(new BasicNameValuePair("rquest", "searchCity"));
            strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(Search.this)));
            strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));

            Log.e("Log_tag", "" + strBuilder.toString());

            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl(url, strBuilder);

            try {

                udata = json.getString("uData");

                if (udata.equals("1")) {
                    JSONArray jarray = json.getJSONArray("result");
                    HashMap<String, String> map;
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        map = new HashMap<String, String>();

                        map.put("city", object.getString("city"));


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
  /*
*/
}
