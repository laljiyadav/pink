package com.pinkstar.main.adapter;

/**
 * Created by sudhir on 5/5/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinkstar.main.R;

import java.util.ArrayList;
import java.util.HashMap;


public class ExpandableListAdapter extends BaseAdapter {

    Context c;
    ArrayList<HashMap<String, String>> map;

    public ExpandableListAdapter(Context context, ArrayList<HashMap<String, String>> map) {

        super();
        this.c = context;
        this.map = map;


    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Object getItem(int position) {
        return map.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View row = inflater.inflate(R.layout.child_raw, parent, false);
        TextView city = (TextView) row.findViewById(R.id.txt_optname);
        ImageView icon = (ImageView) row.findViewById(R.id.opt_image);

        city.setText(map.get(position).get("name"));
        //count.setText(map.get(position).get("citycount"));
        icon.setImageResource(Integer.parseInt(map.get(position).get("image")));


        return row;

    }
}