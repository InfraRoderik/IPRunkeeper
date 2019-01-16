package com.infraroderik.iprunkeeper.DataModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Segment implements Serializable
{
    private long time;
    private double startPointLat;
    private double startPointLong;
    private double endPointLat;
    private double endPointLong;

    public Segment(Builder builder)
    {
        this.time = builder.time;
        this.startPointLat = builder.startPointLat;
        this.startPointLong = builder.startPointLong;
        this.endPointLat = builder.endPointLat;
        this.endPointLong = builder.endPointLong;
    }

    public static class Builder
    {
        private long time;
        private double startPointLat;
        private double startPointLong;
        private double endPointLat;
        private double endPointLong;

        public Builder time(long time)
        {
            this.time = time;
            return this;
        }

        public Builder startPointLat(double startPointLat)
        {
            this.startPointLat = startPointLat;
            return this;
        }

        public Builder startPointLong(double startPointLong)
        {
            this.startPointLong = startPointLong;
            return this;
        }

        public Builder endPointLat(double endPointLat)
        {
            this.endPointLat = endPointLat;
            return this;
        }

        public Builder endPointLong(double endPointLong)
        {
            this.endPointLong = endPointLong;
            return this;
        }

        public Segment build()
        {
            return new Segment(this);
        }
    }

    public long getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getStartPointLat() {
        return startPointLat;
    }

    public void setStartPointLat(double startPointLat) {
        this.startPointLat = startPointLat;
    }

    public double getStartPointLong() {
        return startPointLong;
    }

    public void setStartPointLong(double startPointLong) {
        this.startPointLong = startPointLong;
    }

    public double getEndPointLat() {
        return endPointLat;
    }

    public void setEndPointLat(double endPointLat) {
        this.endPointLat = endPointLat;
    }

    public double getEndPointLong() {
        return endPointLong;
    }

    public void setEndPointLong(double endPointLong) {
        this.endPointLong = endPointLong;
    }

    @Override
    public String toString()
    {
        return "Segment{" +
                "time='" + time + '\'' +
                ", startPointLat='" + startPointLat + '\'' +
                ", startPointLong=" + startPointLong +
                ", endPointLat=" + endPointLat +
                ", endPointLong=" + endPointLong +
                '}';
    }
}