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

import com.pinkstar.main.MyWallet;
import com.pinkstar.main.R;
import com.pinkstar.main.data.GPSTracker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rskaushik on 07-06-2016.
 */
public class StarOfferAdapter extends BaseAdapter {
    ArrayList<HashMap<String, String>> citynearArrayList;
    Context context;


    public StarOfferAdapter(Context context, ArrayList<HashMap<String, String>> citynearArrayList) {
        this.context = context;
        this.citynearArrayList = citynearArrayList;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.offer_list, parent, false);
        TextView amount = (TextView) convertView.findViewById(R.id.txt_amount);
        TextView details = (TextView) convertView.findViewById(R.id.txt_detail);
        TextView stars = (TextView) convertView.findViewById(R.id.txt_star);
        String re = context.getResources().getString(R.string.rs);

        amount.setText(re + "" + citynearArrayList.get(position).get("amount"));
        stars.setText(citynearArrayList.get(position).get("star") + " star");
        details.setText(citynearArrayList.get(position).get("offer_details"));

        amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyWallet.setText(citynearArrayList.get(position).get("amount"));
            }
        });

        return convertView;
    }
}



