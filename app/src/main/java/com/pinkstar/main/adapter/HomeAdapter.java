package com.pinkstar.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pinkstar.main.R;
import com.pinkstar.main.data.GPSTracker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


public class HomeAdapter extends BaseAdapter {

    Context c;
    ArrayList<HashMap<String, String>> map;
    GPSTracker gpsTracker;
    double lat, lang, lat1, lang1;


    public HomeAdapter(Context context, ArrayList<HashMap<String, String>> map) {

        super();
        this.c = context;
        this.map = map;
        gpsTracker = new GPSTracker(context);


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
        final ProgressBar progress=(ProgressBar)row.findViewById(R.id.progressBar);

        name.setText(map.get(position).get("company_display_name"));
        per.setText(map.get(position).get("discount_amount") + "%");

        Picasso.with(c)
                .load(map.get(position).get("image_url"))
                .into(img,  new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (progress != null) {
                            progress .setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {


                    }
                });
        Location location = gpsTracker.getLocation();
        lat = location.getLatitude();
        lang = location.getLongitude();
        lat1 = Double.parseDouble(map.get(position).get("lat"));
        lang1 = Double.parseDouble(map.get(position).get("long"));

        kms.setText("" + (int) distance(lat, lang, lat1, lang1) + "Km");


        return row;
       /* return null;*/
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