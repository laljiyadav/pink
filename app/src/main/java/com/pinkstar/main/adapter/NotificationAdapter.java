package com.pinkstar.main.adapter;

import android.app.Activity;
import android.content.Context;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Rskaushik on 07-06-2016.
 */
public class NotificationAdapter extends BaseAdapter {
    ArrayList<HashMap<String, String>> citynearArrayList;
    Context context;
    GPSTracker gpsTracker;

    public NotificationAdapter(Context context, ArrayList<HashMap<String, String>> citynearArrayList) {
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
        convertView = inflater.inflate(R.layout.noti_list, parent, false);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        ImageView img = (ImageView) convertView.findViewById(R.id.image);

        title.setText(citynearArrayList.get(position).get("name"));


        return convertView;
    }


    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-dd-MM HH:mm:ss";
        String outputPattern = "EEE dd MMM yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


}
