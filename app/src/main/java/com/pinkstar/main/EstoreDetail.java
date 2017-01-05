package com.pinkstar.main;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pinkstar.main.adapter.OfferAdapter;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import me.relex.circleindicator.CircleIndicator;

public class EstoreDetail extends Activity {

    EditText ed_add, ed_add1, ed_city, ed_pin, ed_state, ed_country, ed_quantity, ed_name;
    TextView txt_name, txt_description;
    Button btn_book;
    String name, description, image, price, add1, add2, city, pin, state, country, quantity, fulladdress;
    String url = Apis.Base, id, udata;
    ArrayList<HashMap<String, String>> offerList = new ArrayList<HashMap<String, String>>();
    JSONObject json;
    ViewPager img_pager;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estore_detail);
        inIt();

    }

    public void inIt() {
        ed_add = (EditText) findViewById(R.id.add1);
        ed_name = (EditText) findViewById(R.id.name);
        ed_add1 = (EditText) findViewById(R.id.add2);
        ed_city = (EditText) findViewById(R.id.city);
        ed_pin = (EditText) findViewById(R.id.pin);
        ed_state = (EditText) findViewById(R.id.state);
        ed_country = (EditText) findViewById(R.id.country);
        ed_quantity = (EditText) findViewById(R.id.quatity);
        txt_name = (TextView) findViewById(R.id.title);
        txt_description = (TextView) findViewById(R.id.description);
        btn_book = (Button) findViewById(R.id.btn_book);
        layout = (LinearLayout) findViewById(R.id.hide);
        img_pager = (ViewPager) findViewById(R.id.image_pager);


        id = getIntent().getExtras().getString("id");


        ed_country.setText(SaveSharedPreference.getMobile(EstoreDetail.this).replace("+91-", ""));
        ed_name.setText(SaveSharedPreference.getUserName(EstoreDetail.this) + " " + SaveSharedPreference.getLastName(EstoreDetail.this));

        new AttempEstoreDetail().execute();

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int check = layout.getVisibility();
                Log.e("check", "" + check);
                if (check == 8) {
                    layout.setVisibility(View.VISIBLE);
                } else {

                    if (valid()) {
                        new AttemEstore().execute();

                    }
                }
            }
        });

    }


    private class AttempEstoreDetail extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(EstoreDetail.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {


            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


            nameValuePairs.add(new BasicNameValuePair("product_id", id));
            nameValuePairs.add(new BasicNameValuePair("rquest", "estoreProductDetail"));
            nameValuePairs.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(EstoreDetail.this)));
            nameValuePairs.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(EstoreDetail.this)));
            nameValuePairs.add(new BasicNameValuePair("api_token", Apis.Api_Token));

            Log.e("Log_tag", "" + nameValuePairs);

            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl(url, nameValuePairs);
            try {

                udata = json.getString("uData");

                if (udata.equals("1")) {
                    String img[] = json.getString("image").split(",");

                    HashMap<String, String> map;
                    for (int i = 0; i < img.length; i++) {
                        map = new HashMap<String, String>();
                        map.put("image", img[i]);

                        offerList.add(map);


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
            try {
                if (udata.equals("1")) {
                    OfferAdapter offerAdapter = new OfferAdapter(EstoreDetail.this, offerList);
                    img_pager.setAdapter(offerAdapter);
                    CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
                    indicator.setViewPager(img_pager);
                    txt_name.setText(json.getJSONObject("result").getString("name"));
                    txt_description.setText(json.getJSONObject("result").getString("description"));
                    btn_book.setText("Book Now      " + getResources().getString(R.string.rs) + " " + json.getJSONObject("result").getString("discount_price"));
                    price = json.getJSONObject("result").getString("discount_price");
                } else if (udata.equals("22")) {
                    Dialogs.showCenterToast(EstoreDetail.this, json.getJSONObject("result").getString("message"));
                } else {
                    Dialogs.showCenterToast(EstoreDetail.this, "Server Error");
                }
            } catch (Exception e) {

            }


        }
    }

    public boolean valid() {
        add1 = ed_add.getText().toString();
        add2 = ed_add1.getText().toString();
        city = ed_city.getText().toString();
        pin = ed_pin.getText().toString();
        state = ed_state.getText().toString();
        country = ed_country.getText().toString();

        if (add1.equals("")) {
            ed_add.setError("Enter Address");
            return false;
        } else if (city.equals("")) {
            ed_city.setError("Enter City");
            return false;
        } else if (pin.equals("")) {
            ed_pin.setError("Enter Pin Code");
            return false;
        } else if (state.equals("")) {
            ed_state.setError("Enter State");
            return false;
        } else if (country.equals("")) {
            ed_country.setError("Enter Contact Number");
            return false;
        } else if (country.length() < 10) {
            ed_country.setError("Enter Valid Number");
            return false;
        } else {
            return true;
        }


    }

    private class AttemEstore extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(EstoreDetail.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {


            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


            nameValuePairs.add(new BasicNameValuePair("product_id", id));
            nameValuePairs.add(new BasicNameValuePair("rquest", "productPurchase"));
            nameValuePairs.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(EstoreDetail.this)));
            nameValuePairs.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(EstoreDetail.this)));
            nameValuePairs.add(new BasicNameValuePair("api_token", Apis.Api_Token));
            nameValuePairs.add(new BasicNameValuePair("product_cost", price));
            nameValuePairs.add(new BasicNameValuePair("product_quantity", "1"));
            nameValuePairs.add(new BasicNameValuePair("address", add1 + " " + add2 + " " + city + " " + pin + " " + state));
            nameValuePairs.add(new BasicNameValuePair("contact_number", country));

            Log.e("Log_tag", "" + nameValuePairs);

            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl(url, nameValuePairs);

            Log.e("log",""+json);
            try {

                udata = json.getString("uData");


            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            Dialogs.dismissDialog();
            try {
                if (udata.equals("1")) {
                    Dialogs.showCenterToast(EstoreDetail.this, json.getJSONObject("result").getString("message"));
                    SaveSharedPreference.setBalStar(EstoreDetail.this, json.getJSONObject("star").getString("redeemable_star"));
                    SaveSharedPreference.setTotal(EstoreDetail.this, json.getJSONObject("star").getString("balance_star"));
                } else if (udata.equals("21")) {
                    Dialogs.showCenterToast(EstoreDetail.this, json.getJSONObject("result").getString("message"));
                } else if (udata.equals("22")) {
                    Dialogs.showCenterToast(EstoreDetail.this, json.getJSONObject("result").getString("message"));
                } else {
                    Dialogs.showCenterToast(EstoreDetail.this, "Server Error");
                }
            } catch (Exception e) {

            }


        }
    }
}
