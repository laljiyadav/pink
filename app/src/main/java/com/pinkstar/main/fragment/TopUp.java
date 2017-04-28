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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pinkstar.main.Operater;
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


public class TopUp extends Fragment {

    ListView list;
    public static String amount = "", amount1 = "";
    private int page;
    BrawseAdapter adapter;
    ArrayList<HashMap<String, String>> title;

    public TopUp() {
        // Required empty public constructor
    }

    public static Fragment newInstance(int page, ArrayList<HashMap<String, String>> hashMaps, Fragment fragment) {
        //TopUp fragmentFirst = new TopUp();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putSerializable("someTitle", hashMaps);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        page = getArguments().getInt("someInt", 0);
        title = (ArrayList<HashMap<String, String>>) getArguments().getSerializable("someTitle");


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_top_up, container, false);
        list = (ListView) view.findViewById(R.id.list);

        Log.e("some", "" + page);
        Log.e("title", "" + title);
        adapter = new BrawseAdapter(getActivity(), title);
        list.setAdapter(adapter);




        return view;


    }


}
