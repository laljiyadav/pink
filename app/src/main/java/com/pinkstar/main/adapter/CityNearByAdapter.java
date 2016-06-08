package com.pinkstar.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinkstar.main.R;
import com.pinkstar.main.data.GPSTracker;
import com.pinkstar.main.other.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rskaushik on 07-06-2016.
 */
public class CityNearByAdapter extends BaseAdapter {
    ArrayList<HashMap<String, String>> citynearArrayList;
    Context context;
    ImageLoader imageLoader;
    GPSTracker gpsTracker;
    double lat, lang, lang1, lat1;

    public CityNearByAdapter(Context context, ArrayList<HashMap<String, String>> citynearArrayList) {
        this.context = context;
        this.citynearArrayList = citynearArrayList;
        imageLoader = new ImageLoader(context);
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
        convertView = inflater.inflate(R.layout.near_list, parent, false);
        TextView per = (TextView) convertView.findViewById(R.id.persent);
        TextView kms = (TextView) convertView.findViewById(R.id.kilo);
        TextView name = (TextView) convertView.findViewById(R.id.listname);
        ImageView img = (ImageView) convertView.findViewById(R.id.imagelist);

        name.setText(citynearArrayList.get(position).get("company_name"));
        per.setText(citynearArrayList.get(position).get("ps_discount") + "%");
        imageLoader.DisplayImage(citynearArrayList.get(position).get("image_url"), img);
        Location location = gpsTracker.getLocation();
        lat = location.getLatitude();
        lang = location.getLongitude();
        lat1 = Double.parseDouble(citynearArrayList.get(position).get("lat"));
        lang1 = Double.parseDouble(citynearArrayList.get(position).get("long"));

        kms.setText("" + (int) distance(lat, lang, lat1, lang1) + "Km");


        return convertView;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
