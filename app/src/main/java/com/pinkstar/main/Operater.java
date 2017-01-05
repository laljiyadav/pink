package com.pinkstar.main;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.pinkstar.main.adapter.ExpandableListAdapter;
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

public class Operater extends Activity {

    ExpandableListView listView;
    String url = Apis.Opt_pre;
    JSONObject json;
    ExpandableListAdapter listAdapter;
    ArrayList<HashMap<String, String>> oprators_list = new ArrayList<HashMap<String, String>>();
    HashMap<String, ArrayList<String>> details = new HashMap<String, ArrayList<String>>();
    HashMap<String, ArrayList<String>> details_code = new HashMap<String, ArrayList<String>>();
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> code = new ArrayList<String>();
    public static String operator_code = "";
    public static String circle_code = "";
    public static String operator_name = "";
    public static String operator_name_code = "";
    public static String operator_code1 = "";
    public static String circle_code1 = "";
    public static String operator_name1 = "";
    public static String operator_name_code1 = "";
    public static String operator_code2 = "";
    public static String circle_code2 = "";
    public static String operator_name2 = "";
    public static String operator_name_code2 = "";
    public static String type = "";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operater);
        listView = (ExpandableListView) findViewById(R.id.expend);


        type = getIntent().getExtras().getString("type");

        if (type.equals("prepaid")) {
            url = Apis.Opt_pre;
        } else if (type.equals("postpaid")) {
            url = Apis.Opt_post;
        } else if (type.equals("datacard")) {
            url = Apis.Opt_data;
        } else if (type.equals("dth")) {
            url = Apis.Opt_dth;
        }


        new AttempOperator().execute();


        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        oprators_list.get(groupPosition).get("operator_name_code")
                                + " -> "
                                + details_code.get(
                                oprators_list.get(groupPosition).get("operator_name")).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();

                if (type.equals("postpaid") || type.equals("prepaid")) {
                    operator_name = oprators_list.get(groupPosition).get("operator_name");
                    operator_code = oprators_list.get(groupPosition).get("operator_code");
                    operator_name_code=oprators_list.get(groupPosition).get("operator_name_code");
                    circle_code = details_code.get(oprators_list.get(groupPosition).get("operator_name")).get(childPosition);
                } else if (type.equals("datacard")) {
                    operator_name1 = oprators_list.get(groupPosition).get("operator_name");
                    operator_code1 = oprators_list.get(groupPosition).get("operator_code");
                    operator_name_code1=oprators_list.get(groupPosition).get("operator_name_code");
                    circle_code1 = details_code.get(oprators_list.get(groupPosition).get("operator_name")).get(childPosition);
                } else {
                    operator_name2 = oprators_list.get(groupPosition).get("operator_name");
                    operator_code2 = oprators_list.get(groupPosition).get("operator_code");
                    operator_name_code2=oprators_list.get(groupPosition).get("operator_name_code");
                    circle_code2 = details_code.get(oprators_list.get(groupPosition).get("operator_name")).get(childPosition);
                }


                finish();
                return false;
            }
        });

    }

    private class AttempOperator extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(Operater.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {


            // Create an array
            try {
                Parser perser = new Parser();

                ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
                strBuilder.add(new BasicNameValuePair("type", type));
                strBuilder.add(new BasicNameValuePair("rquest", "operatorList"));
                strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(Operater.this)));
                strBuilder.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(Operater.this)));
                strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));

                Log.e("Log_tag", "" + strBuilder.toString());
                json = perser.getJSONFromUrl(url,strBuilder);

                JSONArray j_operator = json.getJSONArray("operator");
                JSONArray j_area = json.getJSONArray("circle");

                HashMap<String, String> map;
                for (int i = 0; i < j_operator.length(); i++) {
                    map = new HashMap<String, String>();
                    JSONObject js = j_operator.getJSONObject(i);
                        map.put("operator_name", js.getString("operator_name"));
                        map.put("operator_code", js.getString("id"));
                        map.put("operator_name_code", js.getString("operator_text_code"));


                    oprators_list.add(map);

                }


                for (int i = 0; i < j_area.length(); i++) {

                    JSONObject js = j_area.getJSONObject(i);
                    name.add(js.getString("circle_name"));
                    code.add(js.getString("circle_code"));


                }


                for (int i = 0; i < j_operator.length(); i++) {

                    details.put(oprators_list.get(i).get("operator_name"), name);
                    details_code.put(oprators_list.get(i).get("operator_name"), code);
                    title.add(oprators_list.get(i).get("operator_name"));

                }

            } catch (Exception e) {

            }


            return null;

        }

        @Override
        protected void onPostExecute(String args) {
            // Locate the listview in listview_main.xml
            Dialogs.dismissDialog();
            Log.e("detais", "" + details);

            listAdapter = new ExpandableListAdapter(Operater.this, title, details);
            listView.setAdapter(listAdapter);

        }
    }
}
