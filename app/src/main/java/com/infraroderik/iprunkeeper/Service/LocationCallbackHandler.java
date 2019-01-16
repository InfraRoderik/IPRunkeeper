package com.infraroderik.iprunkeeper.Service;

import android.app.Activity;
import android.app.Application;
import android.location.Location;
import android.location.LocationListener;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LocationCallbackHandler extends LocationCallback {
    private static LocationCallbackHandler instance;
    private List<Location> locationList;
    private ArrayList<LocationCallbackListener> listeners;

    public LocationCallbackHandler()
    {
        instance = this;
        listeners = new ArrayList<LocationCallbackListener>();
    }

    public static LocationCallbackHandler getInstance() {
        if(instance == null) instance = new LocationCallbackHandler();
        return instance;
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        Log.d("AIDSCODED", locationResult.toString());
        locationList = locationResult.getLocations();

        if(locationList.size() > 0)
        {
            Location location = locationList.get(locationList.size() - 1);
            Log.i("AIDSCODE", locationResult.getLastLocation().getLatitude() + " " + locationResult.getLastLocation().getLongitude());

            for (LocationCallbackListener e : listeners) {
                e.onLocationAvailable(location);
            }
        }
    }

    public void addListener(LocationCallbackListener listener)
    {
        listeners.add(listener);
    }
}

