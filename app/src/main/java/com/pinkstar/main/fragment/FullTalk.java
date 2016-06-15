package com.pinkstar.main.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.pinkstar.main.R;
import com.pinkstar.main.adapter.BrawseAdapter;
import com.pinkstar.main.adapter.ExpandableListAdapter;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class FullTalk extends Fragment {

    ListView list;
    JSONObject json;
    String url = Apis.Base;
    ArrayList<HashMap<String, String>> array_two = new ArrayList<HashMap<String, String>>();
    BrawseAdapter adapter;

    public FullTalk() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_full_talk, container, false);
        list = (ListView) view.findViewById(R.id.list);

        new AttempFull().execute();
        return view;


    }

    private class AttempFull extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(getActivity(), "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            // http://pinkstarapp.com/api/restservices.php?rquest=plan_list&operator=28&circle=17&plancode=2G
            StringBuilder builder = new StringBuilder(url);
            builder.append("rquest=plan_list");
            builder.append("&operator=28");
            builder.append("&circle=17");

            try {
                Parser perser = new Parser();
                json = perser.getJSONFromUrl1(builder.toString());
                Log.e("Json_list", "" + json);
                JSONArray result = json.getJSONArray("result");


                HashMap<String, String> map;
                for (int i = 0; i < result.length(); i++) {
                    map = new HashMap<String, String>();
                    JSONObject js = result.getJSONObject(i);

                    if (js.getString("Detail").contains("Full Talktime")) {

                        map.put("amount", js.getString("Amount"));
                        map.put("validity", js.getString("Validity"));
                        map.put("detail", js.getString("Detail"));

                        array_two.add(map);
                    }


                }


            } catch (Exception e) {

            }


            return null;

        }

        @Override
        protected void onPostExecute(String args) {
            // Locate the listview in listview_main.xml
            Dialogs.dismissDialog();

            adapter = new BrawseAdapter(getActivity(), array_two);
            list.setAdapter(adapter);

        }
    }
}
