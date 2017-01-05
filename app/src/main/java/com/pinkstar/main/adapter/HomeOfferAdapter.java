package com.pinkstar.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.pinkstar.main.EstoreDetail;
import com.pinkstar.main.OfferDetail;
import com.pinkstar.main.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeOfferAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<HashMap<String, String>> data;

    public HomeOfferAdapter(Context context, ArrayList<HashMap<String, String>> data) {
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
    public Object instantiateItem(ViewGroup container, final int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.pager_list, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.page_img);
       final ProgressBar progress=(ProgressBar)itemView.findViewById(R.id.progressBar);



        Picasso.with(mContext)
                .load(data.get(position).get("image"))
                .into(imageView,  new com.squareup.picasso.Callback() {
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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).get("type").equalsIgnoreCase("1")) {
                    Intent in = new Intent(mContext, OfferDetail.class);
                    in.putExtra("arraylist",data.get(position));
                    mContext.startActivity(in);

                }
                if (data.get(position).get("type").equalsIgnoreCase("2")) {
                    Intent in = new Intent(mContext, EstoreDetail.class);
                    in.putExtra("id",data.get(position).get("product_id"));
                    mContext.startActivity(in);

                }
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