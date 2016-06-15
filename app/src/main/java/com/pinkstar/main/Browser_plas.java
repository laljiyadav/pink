package com.pinkstar.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.pinkstar.main.fragment.FullTalk;
import com.pinkstar.main.fragment.LocalPack;
import com.pinkstar.main.fragment.OneFragment;
import com.pinkstar.main.fragment.Roaming;
import com.pinkstar.main.fragment.SmsPack;
import com.pinkstar.main.fragment.ThreeFragment;
import com.pinkstar.main.fragment.ThreeG;
import com.pinkstar.main.fragment.TopUp;
import com.pinkstar.main.fragment.TwoFragment;
import com.pinkstar.main.fragment.TwoG;

import java.util.ArrayList;
import java.util.List;

public class Browser_plas extends ActionBarActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_plas);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);
        Log.e("item",""+viewPager.getCurrentItem());
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        TopUp m1stFragment;
        TwoG m2ndFragment;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(final int position) {
            Log.e("Log.e","pos"+position);
            switch (position) {
                case 0:
                    Log.e("Log.e","0");
                    return new TopUp();
                case 1:
                    Log.e("Log.e","1");
                    return new TwoG();
                case 2:
                    Log.e("Log.e","2");
                    return new ThreeG();
                case 3:
                    Log.e("Log.e","3");
                    return new FullTalk();
                case 4:
                    Log.e("Log.e","4");
                    return new SmsPack();
                case 5:
                    Log.e("Log.e","5");
                    return new LocalPack();
                case 6:
                    Log.e("Log.e","6");
                    return new Roaming();

                default:
                    break;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            // save the appropriate reference depending on position
            Log.e("Log.e","possfdsf"+position);
            switch (position) {
                case 0:
                    m1stFragment = (TopUp) createdFragment;
                    break;
                case 1:
                    m2ndFragment = (TwoG) createdFragment;
                    break;
            }
            return createdFragment;
        }


        @Override
        public CharSequence getPageTitle(final int position) {
            Log.e("Log.e","positi"+position);
            switch (position) {
                case 0:
                    return "Top Up";
                case 1:
                    return "2G Data";
                case 2:
                    return "3G/4G Data";
                case 3:
                    return "Full Talktime";
                case 4:
                    return "SMS Packs";
                case 5:
                    return "Local/ISD Calls";
                case 6:
                    return "Roaming";
                default:
                    break;
            }
            return null;
        }
    }
}
