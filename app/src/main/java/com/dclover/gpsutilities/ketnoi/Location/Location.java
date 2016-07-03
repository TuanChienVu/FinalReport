package com.dclover.gpsutilities.ketnoi.Location;

/**
 * Created by MY PC on 07/06/2016.
 */
public class Location {
    Double lat;
    Double log;
    String userID;
    String userName;

    public Location() {
    }

    public Location(Double lat, String userName, String userID, Double log) {
        this.lat = lat;
        this.userName = userName;
        this.userID = userID;
        this.log = log;
    }

    public Location(Double lat, Double log, String userID) {
        this.lat = lat;
        this.log = log;
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLog() {
        return log;
    }

    public void setLog(Double log) {
        this.log = log;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
