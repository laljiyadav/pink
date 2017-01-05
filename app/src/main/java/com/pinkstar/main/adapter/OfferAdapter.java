package com.pinkstar.main.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.pinkstar.main.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class OfferAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<HashMap<String, String>> data;

    public OfferAdapter(Context context, ArrayList<HashMap<String, String>> data) {
        mContext = context;
        this.data = data;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.pager_list, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.page_img);
        final ProgressBar progress = (ProgressBar) itemView.findViewById(R.id.progressBar);

        Picasso.with(mContext)
                .load(data.get(position).get("image"))
                .into(imageView, new com.squareup.picasso.Callback() {
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
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}