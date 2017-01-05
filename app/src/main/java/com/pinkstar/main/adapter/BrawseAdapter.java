package com.pinkstar.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pinkstar.main.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rskaushik on 07-06-2016.
 */
public class BrawseAdapter extends BaseAdapter {
    ArrayList<HashMap<String, String>> citynearArrayList;
    Context context;

    public BrawseAdapter(Context context, ArrayList<HashMap<String, String>> citynearArrayList) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.browse_list, parent, false);
        final TextView talk = (TextView) convertView.findViewById(R.id.bro_talktime);
        final TextView validity = (TextView) convertView.findViewById(R.id.bro_validity);
        final TextView plan = (TextView) convertView.findViewById(R.id.br_plan);
        final TextView descri = (TextView) convertView.findViewById(R.id.bro_descr);

        talk.setText(citynearArrayList.get(position).get("amount"));
        validity.setText(citynearArrayList.get(position).get("validity"));
        plan.setText(context.getResources().getString(R.string.rs)+" "+citynearArrayList.get(position).get("amount"));
        descri.setText(citynearArrayList.get(position).get("detail"));


        return convertView;
    }


}
