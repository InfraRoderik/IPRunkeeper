package com.infraroderik.iprunkeeper.DataModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Traject implements Serializable
{
    private int type;
    private int startDateTime;
    private int endDateTime;
    private ArrayList<Segment> segmentList;

    public Traject(Builder builder)
    {
        this.type = builder.type;
        this.startDateTime = builder.startDateTime;
        this.endDateTime = builder.endDateTime;
        this.segmentList = builder.segmentList;
    }

    public static class Builder
    {
        private int type;
        private int startDateTime;
        private int endDateTime;
        private ArrayList<Segment> segmentList;

        public Builder type(int type)
        {
            this.type = type;
            return this;
        }

        public Builder startDateTime(int startDateTime)
        {
            this.startDateTime = startDateTime;
            return this;
        }

        public Builder endDateTime(int endDateTime)
        {
            this.endDateTime = endDateTime;
            return this;
        }

        public Builder segmentList(ArrayList<Segment> segmentList)
        {
            this.segmentList = segmentList;
            return this;
        }

        public Traject build()
        {
            return new Traject(this);
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(int startDateTime) {
        this.startDateTime = startDateTime;
    }

    public int getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(int endDateTime) {
        this.endDateTime = endDateTime;
    }

    public ArrayList<Segment> getSegmentList() {
        return segmentList;
    }

    public void setSegmentList(ArrayList<Segment> segmentList) {
        this.segmentList = segmentList;
    }

    @Override
    public String toString()
    {
        return "Traject{" +
                "type='" + type + '\'' +
                ", startDateTime='" + startDateTime + '\'' +
                ", endDateTime=" + endDateTime +
                ", amount of segments=" + segmentList.size() +
                '}';
    }
}