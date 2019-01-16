package com.infraroderik.iprunkeeper;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.infraroderik.iprunkeeper.DataModel.Segment;
import com.infraroderik.iprunkeeper.DataModel.Traject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RouteAdapter extends ArrayAdapter<Traject> {
    public RouteAdapter(Context context, ArrayList<Traject> trajects) {
        super(context, 0, trajects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Traject traject = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_main2, parent, false
            );
        }

        float distance = calculateDistance(traject.getSegmentList());

        TextView startTime = (TextView) convertView.findViewById(R.id.StartTime);
        TextView duration = (TextView) convertView.findViewById(R.id.Duration);
        TextView speed = (TextView) convertView.findViewById(R.id.AvarageSpeed);
        TextView length = (TextView) convertView.findViewById(R.id.Length);
        Date d = new Date(traject.getStartDateTime());

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
        String date = dateFormat.format(new Date());

        startTime.setText(date);
        duration.setText((traject.getEndDateTime() - traject.getStartDateTime())/60000+" min.");
        float i = distance/1000;
        float p = (float) ((float)(traject.getEndDateTime()- traject.getStartDateTime()) /3600000.0);
        speed.setText((float)Math.round((i)/(p)*10)/10.0f + " km/h");
        length.setText(Math.round(distance/10.00)/100.000+" km");


        return convertView;
    }

    public float calculateDistance(ArrayList<Segment> segments){
        float distance = 0;
        for (Segment s: segments) {
            Location location1 = new Location("");
            location1.setLongitude(s.getStartPointLong());
            location1.setLatitude(s.getStartPointLat());

            Location location2 = new Location("");
            location2.setLatitude(s.getEndPointLat());
            location2.setLongitude(s.getEndPointLong());

            distance += location1.distanceTo(location2);
        }
        return distance;
    }
}
