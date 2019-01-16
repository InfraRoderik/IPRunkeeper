package com.infraroderik.iprunkeeper;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.infraroderik.iprunkeeper.DataModel.Segment;
import com.infraroderik.iprunkeeper.DataModel.Traject;
import com.infraroderik.iprunkeeper.Service.DataStorage;
import com.infraroderik.iprunkeeper.Service.LocationCallbackHandler;
import com.infraroderik.iprunkeeper.Service.LocationCallbackListener;
import com.infraroderik.iprunkeeper.Service.NotificationService;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static android.support.v4.app.ServiceCompat.stopForeground;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationCallbackListener {

    private GoogleMap mMap;
    private ImageButton startStop;
    private boolean started = false;
    private Date startTime;
    private Date endTime;
    private long lastTime;
    private LatLng lastLatLng = null;
    private ArrayList<Segment> segments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        LocationCallbackHandler.getInstance().addListener(this);

        startStop = findViewById(R.id.imageButton);
        startStop.setImageResource(R.drawable.play);
        startStop.setOnClickListener(v ->{
            if(!started) { //run has started
                routeStart();
            }
            else{ //run has stopped
                stopRoute();
            }
        });
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
        UiSettings settings = googleMap.getUiSettings();
        settings.setTiltGesturesEnabled(true);
        settings.setZoomControlsEnabled(true);
        settings.setMapToolbarEnabled(false);
        settings.setCompassEnabled(true);
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;
        mMap.setMyLocationEnabled(true);
        settings.setMyLocationButtonEnabled(true);

    }

    public void routeStart(){
        started = true;
        startStop.setImageResource(R.drawable.stop);
        Toast.makeText(this, "Route tracking started", Toast.LENGTH_LONG).show();
        startForegroundService(new Intent(this, NotificationService.class).setAction("START"));
        startTime = Calendar.getInstance().getTime();
        segments = new ArrayList<>();
    }

    public void stopRoute(){
        started = false;
        Intent stopIntent = new Intent(this, NotificationService.class);
        stopIntent.setAction("STOP");
        startService(stopIntent);
        startStop.setImageResource(R.drawable.play);
        if(segments.size() > 5) {
            Traject traject = new Traject.Builder()
                    .segmentList(segments)
                    .type(0)
                    .startDateTime(startTime.getTime())
                    .endDateTime(Calendar.getInstance().getTimeInMillis())
                    .build();
            DataStorage storage = new DataStorage(this);
            storage.addTraject(traject);
            endTime = Calendar.getInstance().getTime();
            Intent intent = new Intent(this, Main3Activity.class);
            intent.putExtra("TRAJECT", traject);
            startActivity(intent);
        }
    }

    @Override
    public void onLocationAvailable(Location location) {
        if(lastLatLng != null) {
            segments.add(new Segment.Builder()
                    .time((int)(Calendar.getInstance().getTimeInMillis() - lastTime))
                    .startPointLat(lastLatLng.latitude)
                    .startPointLong(lastLatLng.longitude)
                    .endPointLat(location.getLatitude())
                    .endPointLong(location.getLongitude())
                    .build());
        }

        lastLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        lastTime = Calendar.getInstance().getTimeInMillis();
    }
}

