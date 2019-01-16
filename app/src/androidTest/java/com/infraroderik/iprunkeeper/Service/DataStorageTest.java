package com.infraroderik.iprunkeeper.Service;

import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.infraroderik.iprunkeeper.DataModel.Segment;
import com.infraroderik.iprunkeeper.DataModel.Traject;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class DataStorageTest {
    private DataStorage storage = new DataStorage(InstrumentationRegistry.getTargetContext());

    private Segment segment = new Segment.Builder()
            .startPointLat(123123)
            .startPointLong(123123)
            .endPointLat(123124)
            .endPointLong(123124)
            .time(1024)
            .build();

    private Traject traject = new Traject.Builder()
            .type(1)
            .startDateTime(new Date(2019,1,1, 4, 20).getTime())
            .endDateTime(new Date(2019,1,1, 7, 15).getTime())
            .build();

    private ArrayList<Segment> segments = new ArrayList<>();

    public void initalise() {
        segments.add(segment);
        traject.setSegmentList(segments);
    }

    @Test
    public void insertTrajects() {
        initalise();
        storage.addTraject(traject);
    }



    @Test
    public void retrieveSegmentsOfTrajectByID() {
        int id = this.storage.getTPrimaryKey(traject);

        for (Segment s : this.storage.retrieveSegmentsOfTraject(id)) {
            Log.i("S", s.toString());
            assertEquals(segment.getTime(), s.getTime());
        }
    }

    @Test
    public void retrieveTrajects() {
        for (Traject t : this.storage.retrieveAllTrajects()) {
            Log.i("T", t.toString());
        }
        assertEquals(1, storage.retrieveAllTrajects().size());
    }
}