package com.pinkstar.main;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pinkstar.main.adapter.CityAdapter;
import com.pinkstar.main.adapter.ExpandableListAdapter;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;
import com.pinkstar.main.fragment.OneFragment;
import com.pinkstar.main.fragment.ThreeFragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Operater extends Activity {

    GridView listView;
    ListView list;
    ArrayList<HashMap<String, String>> oprators_list = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> circle_list = new ArrayList<HashMap<String, String>>();
    String opt_code, circle_code;
    TextView txt_head;
    String type;
    int images[] = {R.drawable.vodafone, R.drawable.aircel, R.drawable.bsnl, R.drawable.airtel, R.drawable.docomo,
            R.drawable.tata, R.drawable.rel_cdma, R.drawable.rele_cdma, R.drawable.mts, R.drawable.uninor,
            R.drawable.videocon, R.drawable.mtnl, R.drawable.mtnl, R.drawable.idea, R.drawable.jio};


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operater);
        listView = (GridView) findViewById(R.id.expend);
        list = (ListView) findViewById(R.id.list);
        txt_head = (TextView) findViewById(R.id.head);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = getIntent().getExtras().getString("type");
        }

        inIt();

    }


    public void inIt() {


        try {
            JSONObject object = new JSONObject(Apis.Oprator);
            JSONArray jsonArray = object.getJSONArray("Operator");


            for (int i = 0; i < jsonArray.length(); i++) {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", jsonArray.getJSONObject(i).getString("Operator"));
                map.put("code", jsonArray.getJSONObject(i).getString("Code"));
                map.put("image", jsonArray.getJSONObject(i).getString("image"));

                oprators_list.add(map);
            }

            listView.setAdapter(new ExpandableListAdapter(Operater.this, oprators_list));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    list.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    txt_head.setText("Select Area");
                    opt_code = oprators_list.get(position).get("code");
                    Log.e("value", "" + list.getVisibility());

                    listdata();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("exp", e.toString());
        }


    }

    public void listdata() {

        try {
            JSONObject object = new JSONObject(Apis.Oprator);

            JSONArray jsonArray = object.getJSONArray("Circle");


            for (int i = 0; i < jsonArray.length(); i++) {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("city", jsonArray.getJSONObject(i).getString("Operator"));
                map.put("code", jsonArray.getJSONObject(i).getString("Code"));

                circle_list.add(map);
            }

            list.setAdapter(new CityAdapter(Operater.this, circle_list));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    circle_code = circle_list.get(position).get("code");

                    if (type.equals("postpaid"))
                        OneFragment.setText(opt_code, circle_code);
                    else if (type.equals("datacard"))
                        ThreeFragment.setText(opt_code, circle_code);
                    finish();


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("exp", e.toString());
        }
    }

    @Override
    public void onBackPressed() {


        if (list.getVisibility() == View.VISIBLE) {
            listView.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
            txt_head.setText("Select Operator");
        } else {
            finish();
        }

    }
}
