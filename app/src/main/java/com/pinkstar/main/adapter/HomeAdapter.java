package com.pinkstar.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinkstar.main.R;
import com.pinkstar.main.other.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;


public class HomeAdapter extends BaseAdapter {

    Context c;
    ArrayList<HashMap<String,String>> map;
    ImageLoader imageLoader;


    public HomeAdapter(Context context, ArrayList<HashMap<String,String>> map ){

        super();
        this.c = context;
        this.map =map;
        imageLoader=new ImageLoader(context);


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
        View row = inflater.inflate(R.layout.homelist, parent, false);
        TextView per = (TextView) row.findViewById(R.id.persent);
        TextView kms = (TextView) row.findViewById(R.id.kilo);
        TextView name = (TextView) row.findViewById(R.id.listname);
        ImageView img = (ImageView) row.findViewById(R.id.imagelist);

        name.setText(map .get(position).get("company_name"));
        per.setText(map.get(position).get("ps_discount")+"%");
        imageLoader.DisplayImage(map.get(position).get("image_url"), img);


        return row;
       /* return null;*/
    }
}