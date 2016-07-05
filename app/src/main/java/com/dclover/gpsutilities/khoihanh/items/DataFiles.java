package com.dclover.gpsutilities.khoihanh.items;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class DataFiles {
    private double avgSpeed;
    private String dateTime;
    private float distanceTravelled;
    private String endPlace;
    private String fileName;
    private int id;
    private float maxSpeed;
    private String startPlace;
    private float timeDuration;

    public void setId(int id) {
        this.id = id;
    }

    public DataFiles(String fileName, String startPlace, String endPlace, float maxSpeed, double avgSpeed, float timeDuration, float distanceTravelled, String dateTime) {
        this.fileName = fileName;
        this.endPlace = endPlace;
        this.startPlace = startPlace;
        this.maxSpeed = maxSpeed;
        this.avgSpeed = avgSpeed;
        this.timeDuration = timeDuration;
        this.distanceTravelled = distanceTravelled;
        this.dateTime = dateTime;
    }

    public void setfileName(String fileName) {
        this.fileName = fileName;
    }

    public int getId() {
        return this.id;
    }

    public String getfileName() {
        return this.fileName;
    }

    public void setstartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public void setendPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public void setdateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setmaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setavgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public void settimeDuration(float timeDuration) {
        this.timeDuration = timeDuration;
    }

    public void setdistanceTravelled(float distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public String getstartPlace() {
        return this.startPlace;
    }

    public String getendPlace() {
        return this.endPlace;
    }

    public String getdateTime() {
        return this.dateTime;
    }

    public float getmaxSpeed() {
        return this.maxSpeed;
    }

    public double getavgSpeed() {
        return this.avgSpeed;
    }

    public float gettimeDuration() {
        return this.timeDuration;
    }

    public float getdistanceTravelled() {
        return this.distanceTravelled;
    }
}
