package com.example.jonas.examproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Maps extends FragmentActivity
        implements OnMapReadyCallback {
    GoogleMap map;

    public String cordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        Intent i = getIntent();
        cordinates = i.getStringExtra("cordinates");
        Log.d("Cordinates in maps:", cordinates);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("DEBUG", "onMapReady");
        Log.d("Cordinates in maps:", cordinates);
        String parts[];
        int lat = 0;
        int lng = 0;
        if (cordinates.contains(" - ")) {
            parts = cordinates.split(" - ");
            lat = Integer.parseInt(parts[0]);
            lng = Integer.parseInt(parts[1]);
        }
        map.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title("Marker"));
    }
}
