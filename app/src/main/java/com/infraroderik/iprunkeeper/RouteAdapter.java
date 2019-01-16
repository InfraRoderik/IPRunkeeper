package com.infraroderik.iprunkeeper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.infraroderik.iprunkeeper.DataModel.Traject;

import java.util.ArrayList;
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

        TextView startTime = (TextView) convertView.findViewById(R.id.StartTime);
        TextView duration = (TextView) convertView.findViewById(R.id.Duration);
        TextView speed = (TextView) convertView.findViewById(R.id.AvarageSpeed);
        TextView length = (TextView) convertView.findViewById(R.id.Length);

        startTime.setText(new Date(traject.getStartDateTime()).toString());
        duration.setText((new Date(traject.getEndDateTime()).getTime())- (new Date(traject.getStartDateTime()).getTime())/60000+" min.");
        speed.setText("");
        length.setText("");


        return convertView;
    }
}
