package com.dclover.gpsutilities.khoihanh.moduls;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;



/**
 * Created by Kinghero on 5/4/2016.
 */
public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}
