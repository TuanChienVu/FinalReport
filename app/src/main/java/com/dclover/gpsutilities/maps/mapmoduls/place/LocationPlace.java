package com.dclover.gpsutilities.maps.mapmoduls.place;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kinghero on 7/4/2016.
 */
public class LocationPlace {
    @SerializedName("lat")
    @Expose
    private Float lat;
    @SerializedName("lng")
    @Expose
    private Float lng;

    /**
     *
     * @return
     * The lat
     */
    public Float getLat() {
        return lat;
    }

    /**
     *
     * @param lat
     * The lat
     */
    public void setLat(Float lat) {
        this.lat = lat;
    }

    /**
     *
     * @return
     * The lng
     */
    public Float getLng() {
        return lng;
    }

    /**
     *
     * @param lng
     * The lng
     */
    public void setLng(Float lng) {
        this.lng = lng;
    }
}
