package com.pinkstar.main.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pinkstar.main.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Cate_Adapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<HashMap<String, String>> data;

    public Cate_Adapter(Context context, ArrayList<HashMap<String, String>> data) {
        mContext = context;
        this.data = data;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public float getPageWidth(int position) {
        return 0.3f;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.cate_list, container, false);

        TextView imageView = (TextView) itemView.findViewById(R.id.cate_name);
        imageView.setText(data.get(position).get("name"));

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}