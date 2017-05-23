package com.pinkstar.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pinkstar.main.R;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.GPSTracker;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Rskaushik on 07-06-2016.
 */
public class OrderListAdapter extends BaseAdapter {
    ArrayList<HashMap<String, String>> citynearArrayList;
    Context context;
    GPSTracker gpsTracker;

    public OrderListAdapter(Context context, ArrayList<HashMap<String, String>> citynearArrayList) {
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
        convertView = inflater.inflate(R.layout.order_list, parent, false);
        TextView order_number = (TextView) convertView.findViewById(R.id.order_number);
        TextView time = (TextView) convertView.findViewById(R.id.time_date);
        TextView rupee = (TextView) convertView.findViewById(R.id.repee);
        TextView descri = (TextView) convertView.findViewById(R.id.description);
        TextView payment = (TextView) convertView.findViewById(R.id.payment_type);
        TextView repeeat = (TextView) convertView.findViewById(R.id.repeat);
        ImageView img = (ImageView) convertView.findViewById(R.id.image);
        final ProgressBar progress = (ProgressBar) convertView.findViewById(R.id.progressBar);
        time.setText(parseDateToddMMyyyy(citynearArrayList.get(position).get("order_date")));
        rupee.setText(context.getResources().getString(R.string.rs) + " " + citynearArrayList.get(position).get("order_cost"));

        order_number.setText("Order No-" + citynearArrayList.get(position).get("orderid"));
        descri.setText(citynearArrayList.get(position).get("name"));
        payment.setText(citynearArrayList.get(position).get("order_status"));


        Picasso.with(context)
                .load(citynearArrayList.get(position).get("image"))
                .into(img, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (progress != null) {
                            progress.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {


                    }
                });


        return convertView;
    }


    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
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
