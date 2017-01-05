package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MarkerActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    ArrayList<HashMap<String, String>> venderlist;
    Marker allMarkers;
    private HashMap<Marker, Integer> mHashMap = new HashMap<Marker, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        Init();

    }

    public void Init() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MarkerActivity.this);
        venderlist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("arraylist");

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        marker();


        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                //Toast.makeText(getApplicationContext(),""+mHashMap.get(marker),Toast.LENGTH_SHORT).show();
                Intent in = new Intent(MarkerActivity.this, HomeDetail.class);

                in.putExtra("unique_id", ""+mHashMap.get(marker));

                startActivity(in);
            }
        });



    }

    public void marker() {


        for (int i = 0; i < venderlist.size(); i++) {


            allMarkers = map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(venderlist.get(i).get("lat")), Double.parseDouble(venderlist.get(i).get("long"))))
                    .title(venderlist.get(i).get("company_display_name"))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


            mHashMap.put(allMarkers,Integer.parseInt(venderlist.get(i).get("unique_id")));


            if (i == (venderlist.size() - 1)) {
                LatLng current = new LatLng(Double.parseDouble(venderlist.get(i).get("lat")), Double.parseDouble(venderlist.get(i).get("long")));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(current)      // Sets the center of the map to Mountain View
                        .zoom(13)                   // Sets the zoom
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }


    }
}
