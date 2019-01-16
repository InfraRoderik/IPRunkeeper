package com.infraroderik.iprunkeeper;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.infraroderik.iprunkeeper.DataModel.Segment;
import com.infraroderik.iprunkeeper.DataModel.Traject;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PolylineOptions lineOptions2;
    private List<LatLng> legs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Intent intent = getIntent();
        Traject traject = (Traject) intent.getSerializableExtra("FURBY_OBJECT");

        legs = new ArrayList<LatLng>();
        legs.add(new LatLng(traject.getSegmentList().get(0).getStartPointLat(),traject.getSegmentList().get(0).getStartPointLong()));
        for (Segment s: traject.getSegmentList()) {
            legs.add(new LatLng(s.getEndPointLat(), s.getEndPointLong()));
        }

        PolylineOptions lineOptions = new PolylineOptions();
        lineOptions.addAll(legs);
        lineOptions.width(15);
        lineOptions.color(Color.RED);
        mMap.addPolyline(lineOptions);
        mMap.setMinZoomPreference(14);
        mMap.setMaxZoomPreference(24);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(legs.get(0),15));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
    }
}
