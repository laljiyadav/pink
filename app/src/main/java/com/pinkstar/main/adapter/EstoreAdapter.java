package com.pinkstar.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pinkstar.main.EstoreDetail;
import com.pinkstar.main.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


public class EstoreAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> map;

    public EstoreAdapter(Context context, ArrayList<HashMap<String, String>> map1) {
        this.context = context;
        this.map = map1;

    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public float getPageWidth(int position) {
        return 0.5f;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        // Declare Variables
        TextView txtfirst;
        TextView txtsecont;
        TextView txtname;
        ImageView imgflag;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.estore_list, container,
                false);

        // Locate the TextViews in viewpager_item.xml
        txtfirst = (TextView) itemView.findViewById(R.id.grid_first);
        txtsecont = (TextView) itemView.findViewById(R.id.grid_second);
        txtname = (TextView) itemView.findViewById(R.id.listname);
        imgflag = (ImageView) itemView.findViewById(R.id.imagelist);

        String re = context.getResources().getString(R.string.rs);

        // Capture position and set to the TextViews
        txtfirst.setText(re + map.get(position).get("price"));
        txtfirst.setPaintFlags(txtfirst.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        txtsecont.setText(re + map.get(position).get("discount_price"));
        txtname.setText(map.get(position).get("name"));
        final ProgressBar progress = (ProgressBar) itemView.findViewById(R.id.progressBar);

        if (!map.get(position).get("product_image").equals("")) {
            Picasso.with(context)
                    .load(map.get(position).get("product_image"))
                    .into(imgflag, new com.squareup.picasso.Callback() {
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
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, EstoreDetail.class);
                in.putExtra("name", map.get(position).get("name"));
                in.putExtra("discount_price", map.get(position).get("discount_price"));
                in.putExtra("description", map.get(position).get("description"));
                in.putExtra("product_image", map.get(position).get("product_image"));
                in.putExtra("id", map.get(position).get("id"));
                context.startActivity(in);
            }
        });

        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);

    }
}