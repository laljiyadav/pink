package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pinkstar.main.adapter.StarOfferAdapter;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyWallet extends Activity implements View.OnClickListener {

    TextView txt_total, txt_read, txt_name;
    ImageView star_img, show_img;
    ProgressBar progress;
    Button btn_add;
    static EditText ed_stae;
    String get_star;
    LinearLayout lin_add;
    boolean flag = true;
    JSONObject jsonObject;
    String udata, url = Apis.Base;
    ArrayList<HashMap<String, String>> offerList = new ArrayList<HashMap<String, String>>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        inIt();
    }

    public void inIt() {
        star_img = (ImageView) findViewById(R.id.star_img);
        show_img = (ImageView) findViewById(R.id.img);
        txt_total = (TextView) findViewById(R.id.txt_total);
        txt_read = (TextView) findViewById(R.id.txt_read);
        txt_name = (TextView) findViewById(R.id.name);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        btn_add = (Button) findViewById(R.id.btn_add);
        ed_stae = (EditText) findViewById(R.id.ed_star);
        lin_add = (LinearLayout) findViewById(R.id.lin_add);
        listView = (ListView) findViewById(R.id.list);

        txt_total.setText("" + (Integer.parseInt(SaveSharedPreference.getTotal(MyWallet.this))));
        txt_read.setText(SaveSharedPreference.getBalStar(MyWallet.this));

        txt_name.setText(SaveSharedPreference.getUserName(MyWallet.this) + " " + SaveSharedPreference.getLastName(MyWallet.this));

        btn_add.setOnClickListener(this);

        star_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.star_dialog(MyWallet.this);
            }
        });

        Dialogs.Touch(MyWallet.this, star_img);

        if (SaveSharedPreference.getUserIMAGE(MyWallet.this).equals("")) {
            progress.setVisibility(View.GONE);
        } else {
            Picasso.with(MyWallet.this)
                    .load(SaveSharedPreference.getUserIMAGE(MyWallet.this))
                    .into(show_img, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (progress != null) {
                                progress.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {


                        }
                    });
        }

        new AttempOfer().execute();
    }

    public static void setText(String value) {

        ed_stae.setText(value);
    }


    private class AttempOfer extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(MyWallet.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
            strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(MyWallet.this)));
            strBuilder.add(new BasicNameValuePair("rquest", "starOffer"));
            strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));
            strBuilder.add(new BasicNameValuePair("token_id", "" + SaveSharedPreference.getUSERAuth(MyWallet.this)));


            try {

                Parser perser = new Parser();
                jsonObject = perser.getJSONFromUrl(url, strBuilder);
                Log.e("Log_tag", "" + strBuilder.toString());
               // Log.e("Log_tag", "" + jsonObject);

                udata = jsonObject.getString("uData");


                if (udata.equals("1")) {

                    JSONArray js = jsonObject.getJSONArray("result");
                    HashMap<String, String> map1;
                    for (int i = 0; i < js.length(); i++) {
                        map1 = new HashMap<String, String>();
                        JSONObject object = js.getJSONObject(i);
                        if (object.has("star")) {
                            map1.put("star", object.getString("star"));
                            map1.put("amount", object.getString("amount"));
                            map1.put("offer_details", object.getString("offer_details"));
                            map1.put("start_date", object.getString("start_date"));
                            map1.put("end_date", object.getString("end_date"));


                            offerList.add(map1);

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

            if (udata.equals("1")) {

                listView.setAdapter(new StarOfferAdapter(MyWallet.this, offerList));
            }


        }
    }

    @Override
    public void onClick(View v) {

        if (v == btn_add) {
            if (flag) {
                lin_add.setVisibility(View.VISIBLE);
                flag = false;
            } else {

                if (valid()) {

                    Intent in = new Intent(MyWallet.this, WebView_Activity.class);
                    in.putExtra("star", get_star);
                    startActivity(in);

                }

            }
        }

    }

    public boolean valid() {

        get_star = ed_stae.getText().toString();
        if (get_star.equals("")) {
            ed_stae.setError("Enter Star");
            return false;
        } else {
            return true;

        }
    }
}
