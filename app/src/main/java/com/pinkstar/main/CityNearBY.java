package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pinkstar.main.adapter.CityAdapter;
import com.pinkstar.main.adapter.CityNearByAdapter;
import com.pinkstar.main.adapter.HomeAdapter;
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

public class CityNearBY extends AppCompatActivity {
    String city;
    JSONObject jsonObject;
    String url = Apis.Base, udata;
    ArrayList<HashMap<String, String>> venderList = new ArrayList<HashMap<String, String>>();
    ListView citynearby_listView;
    int page = 0;
    TextView tx_found;
    GPSTracker gpsTracker;
    Location location;
    ImageView star_img;
    String search_name;
    boolean check = false;
    boolean isCheck = false;
    JSONArray js;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        city = getIntent().getExtras().getString("search_selected_city");
        setTitle(city);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_near_by);

        inIt();


    }

    public void inIt() {
        citynearby_listView = (ListView) findViewById(R.id.citynearby_listView);
        star_img = (ImageView) findViewById(R.id.star_img);
        tx_found = (TextView) findViewById(R.id.no_data);
        gpsTracker = new GPSTracker(CityNearBY.this);
        location = gpsTracker.getLocation();
        citynearby_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(CityNearBY.this, HomeDetail.class);
                in.putExtra("company", venderList.get(position).get("company_display_name"));
                in.putExtra("img_array", venderList.get(position).get("img_array"));
                in.putExtra("unique_id", venderList.get(position).get("unique_id"));

                startActivity(in);
            }
        });

        citynearby_listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {

                int threshold = 1;
                int count = citynearby_listView.getCount();

                if (scrollState == SCROLL_STATE_IDLE) {
                    if (citynearby_listView.getLastVisiblePosition() >= count
                            - threshold) {
                        if (!check) {
                            if (js.length() == 20) {
                                page++;
                                new AttempVender().execute();
                            }
                        } else {
                            if (js.length() == 20) {
                                page++;
                                new AttempVender().execute();
                            }
                        }
                    }
                }

            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {


            }
        });
        new AttempVender().execute();

        star_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.star_dialog(CityNearBY.this);
            }
        });
        Dialogs.Touch(CityNearBY.this, star_img);
    }

    private class AttempVender extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(CityNearBY.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
            strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(CityNearBY.this)));
            strBuilder.add(new BasicNameValuePair("rquest", "searchCityVendor"));
            strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));
            strBuilder.add(new BasicNameValuePair("city", city));
            strBuilder.add(new BasicNameValuePair("page", "" + page));
            strBuilder.add(new BasicNameValuePair("lat", "" + location.getLatitude()));
            strBuilder.add(new BasicNameValuePair("long", "" + location.getLongitude()));

            // Create an array
            Parser perser = new Parser();
            jsonObject = perser.getJSONFromUrl(url, strBuilder);
            Log.e("Log_tag", "" + strBuilder.toString());

            try {

                udata = jsonObject.getString("uData");


                if (udata.equals("1")) {
                    js = jsonObject.getJSONArray("result");
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


                        venderList.add(map1);


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
            if (udata.equals("1")) {

                citynearby_listView.setAdapter(new CityNearByAdapter(CityNearBY.this, venderList));
            } else {
                if (page == 0) {
                    tx_found.setVisibility(View.VISIBLE);
                }
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem naviItem = menu.findItem(R.id.action_navi);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        naviItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent in = new Intent(CityNearBY.this, MarkerActivity.class);
                in.putExtra("arraylist", venderList);
                startActivity(in);
                return false;

            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(getApplication(), "submit    " + query, Toast.LENGTH_SHORT).show();
                search_name = query;
                if (query.length() <= 3) {

                } else {

                    new AttempSearchVender().execute();
                }
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {


                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                Toast.makeText(getApplication(), "collapse", Toast.LENGTH_SHORT).show();
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });
        return true;
    }


    private class AttempSearchVender extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(CityNearBY.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
            strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(CityNearBY.this)));
            strBuilder.add(new BasicNameValuePair("rquest", "searchCityVendor"));
            strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));
            strBuilder.add(new BasicNameValuePair("city", city));
            strBuilder.add(new BasicNameValuePair("page", "" + page));
            strBuilder.add(new BasicNameValuePair("lat", "" + location.getLatitude()));
            strBuilder.add(new BasicNameValuePair("long", "" + location.getLongitude()));
            strBuilder.add(new BasicNameValuePair("name", search_name));

            // Create an array
            Parser perser = new Parser();
            jsonObject = perser.getJSONFromUrl(url, strBuilder);
            Log.e("Log_tag", "" + strBuilder.toString());

            try {

                udata = jsonObject.getString("uData");


                if (udata.equals("1")) {
                    if (!isCheck) {
                        venderList.clear();
                        page = 0;
                        check = true;
                        isCheck = true;
                    }
                    js = jsonObject.getJSONArray("result");
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


                        venderList.add(map1);


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

            if (udata.equals("1")) {
                citynearby_listView.setAdapter(new CityNearByAdapter(CityNearBY.this, venderList));
            }


        }
    }


}
