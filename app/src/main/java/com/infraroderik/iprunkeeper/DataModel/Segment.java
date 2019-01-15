package com.infraroderik.iprunkeeper.DataModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Segment implements Serializable
{
    private int time;
    private int startPointLat;
    private int startPointLong;
    private int endPointLat;
    private int endPointLong;

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
        private int time;
        private int startPointLat;
        private int startPointLong;
        private int endPointLat;
        private int endPointLong;

        public Builder time(int time)
        {
            this.time = time;
            return this;
        }

        public Builder startPointLat(int startPointLat)
        {
            this.startPointLat = startPointLat;
            return this;
        }

        public Builder startPointLong(int startPointLong)
        {
            this.startPointLong = startPointLong;
            return this;
        }

        public Builder endPointLat(int endPointLat)
        {
            this.endPointLat = endPointLat;
            return this;
        }

        public Builder endPointLong(int endPointLong)
        {
            this.endPointLong = endPointLong;
            return this;
        }

        public Segment build()
        {
            return new Segment(this);
        }
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getStartPointLat() {
        return startPointLat;
    }

    public void setStartPointLat(int startPointLat) {
        this.startPointLat = startPointLat;
    }

    public int getStartPointLong() {
        return startPointLong;
    }

    public void setStartPointLong(int startPointLong) {
        this.startPointLong = startPointLong;
    }

    public int getEndPointLat() {
        return endPointLat;
    }

    public void setEndPointLat(int endPointLat) {
        this.endPointLat = endPointLat;
    }

    public int getEndPointLong() {
        return endPointLong;
    }

    public void setEndPointLong(int endPointLong) {
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