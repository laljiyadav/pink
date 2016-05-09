package com.pinkstar.main.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pinkstar.main.R;
import com.pinkstar.main.other.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

public class OfferAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<HashMap<String,String>> data;
    ImageLoader imageLoader;

    public OfferAdapter(Context context, ArrayList<HashMap<String,String>> data) {
        mContext = context;
        this.data=data;
        imageLoader = new ImageLoader(context);

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
    public Object instantiateItem(ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.pager_list, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.page_img);
       // Log.e("url",data.get(position).image_url);
        imageLoader.DisplayImage(data.get(position).get("image"), imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}