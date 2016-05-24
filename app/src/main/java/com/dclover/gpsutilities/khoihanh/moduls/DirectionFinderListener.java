package com.dclover.gpsutilities.khoihanh.moduls;

import java.util.List;

/**
 * Created by Kinghero on 5/4/2016.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
