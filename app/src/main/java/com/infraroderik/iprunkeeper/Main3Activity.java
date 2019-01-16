package com.infraroderik.iprunkeeper;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.infraroderik.iprunkeeper.DataModel.Segment;
import com.infraroderik.iprunkeeper.DataModel.Traject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main3Activity extends AppCompatActivity {
    private Traject traject;
    private TextView distanceText;
    private TextView distanceResult;
    private TextView startText;
    private TextView startResult;
    private TextView endText;
    private TextView endResult;
    private TextView timeWastedText;
    private TextView timeWastedResult;
    private TextView speedText;
    private TextView speedResult;
    private TextView results;
    private List<LatLng> legs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        traject = (Traject)getIntent().getExtras().get("TRAJECT");
        Toast.makeText(this, traject.toString(), Toast.LENGTH_LONG).show();

        float distance = calculateDistance(traject.getSegmentList());

        distanceText = findViewById(R.id.distance_Text);
        distanceResult = findViewById(R.id.distance_Result);
        startText = findViewById(R.id.start_text);
        startResult = findViewById(R.id.start_result);
        endText = findViewById(R.id.end_text);
        endResult = findViewById(R.id.end_result);
        timeWastedText = findViewById(R.id.time_text);
        timeWastedResult = findViewById(R.id.time_result);
        speedText = findViewById(R.id.speed_text);
        speedResult = findViewById(R.id.speed_result);
        results = findViewById(R.id.score);

        distanceText.setText(R.string.Distance_text);
        startText.setText(R.string.StartTime_Text);
        endText.setText(R.string.EndTime_text);
        timeWastedText.setText(R.string.Time_text);
        speedText.setText(R.string.Speed_text);
        results.setText(R.string.result_text);

        int minutes = (int)(new Date(traject.getStartDateTime()).getMinutes());
        if(minutes < 10){
            startResult.setText(new Date(traject.getStartDateTime()).getHours()+":0"+minutes);
        }
        else {
            startResult.setText(new Date(traject.getStartDateTime()).getHours() + ":" + minutes);
        }
        int minute2 = (new Date(traject.getEndDateTime()).getMinutes());
        if(minutes < 10){
            endResult.setText(new Date(traject.getEndDateTime()).getHours()+":0"+minute2);
        }
        else {
            endResult.setText(new Date(traject.getEndDateTime()).getHours() + ":" + minute2);
        }
        distanceResult.setText(Math.round(distance/10.00)/100.000+" km");
        //startResult.setText(new Date(traject.getStartDateTime()).getHours()+":"+new Date(traject.getStartDateTime()).getMinutes());
        //endResult.setText(new Date(traject.getEndDateTime()).getHours()+":"+new Date(traject.getEndDateTime()).getMinutes());
        timeWastedResult.setText((traject.getEndDateTime()- traject.getStartDateTime())/60000+" min.");

        float i = distance/1000;
        float p = (float) ((float)(traject.getEndDateTime()- traject.getStartDateTime()) /3600000.0);
        speedResult.setText((float)Math.round((i)/(p)*10)/10.0f + " km/h");
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
