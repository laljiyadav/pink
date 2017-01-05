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
import com.pinkstar.main.data.GPSTracker;

import java.util.ArrayList;

/**
 * Created by Rskaushik on 07-06-2016.
 */
public class SubcatAdapter extends BaseAdapter {
    ArrayList<String> citynearArrayList;
    Context context;
    GPSTracker gpsTracker;

    public SubcatAdapter(Context context, ArrayList<String> citynearArrayList) {
        this.context = context;
        this.citynearArrayList = citynearArrayList;
        gpsTracker = new GPSTracker(context);

    }

    @Override
    public int getCount() {
        return citynearArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.spi_list, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.text);
        ImageView img = (ImageView) convertView.findViewById(R.id.image);


        name.setText(citynearArrayList.get(position));
        if (position == 0) {
            img.setVisibility(View.VISIBLE);
        } else {
            img.setVisibility(View.VISIBLE);
        }


        return convertView;
    }


}
