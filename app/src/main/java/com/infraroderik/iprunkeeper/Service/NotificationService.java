package com.infraroderik.iprunkeeper.Service;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.infraroderik.iprunkeeper.MapsActivity;
import com.infraroderik.iprunkeeper.R;

import static com.infraroderik.iprunkeeper.Service.App.CHANNEL_ID;

public class NotificationService extends Service {
    LocationRequest mLocationRequest;
    FusedLocationProviderClient mFusedLocationProviderClient;
    LocationCallbackHandler locationCallbackHandler;
    Looper looper;
    HandlerThread handlerThread;

    @Override
    public void onCreate() { super.onCreate(); }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals("STOP")) {
            Log.i("NOTIFICATIONSERVICE", "Received Stop Foreground Intent");
            //your end servce code
            stopForeground(true);
            stopSelf();
            return START_STICKY;
        }
        Intent notificationIntent = new Intent(this, MapsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Run keeper run!")
                .setContentText("GPS service")
                .setSmallIcon(R.drawable.main)
                .build();

        startForeground(1, notification);

        handlerThread = new HandlerThread("MyHandlerThread");
        looper = handlerThread.getLooper();
        handlerThread.start();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setInterval(3000);
        mLocationRequest.setMaxWaitTime(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(1);

        locationCallbackHandler = LocationCallbackHandler.getInstance();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED)
                mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, locationCallbackHandler, looper);
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mFusedLocationProviderClient.removeLocationUpdates(locationCallbackHandler);
        stopSelf();
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
