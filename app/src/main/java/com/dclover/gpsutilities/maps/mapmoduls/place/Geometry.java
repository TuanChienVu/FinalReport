package com.dclover.gpsutilities.maps.mapmoduls.place;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kinghero on 7/4/2016.
 */
public class Geometry {
    @SerializedName("location")
    @Expose
    private LocationPlace location;

    /**
     *
     * @return
     * The location
     */
    public LocationPlace getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(LocationPlace location) {
        this.location = location;
    }
}
