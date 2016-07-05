package com.dclover.gpsutilities.thoitiet.services;

/**
 * Created by Kinghero on 7/5/2016.
 */
public interface WeatherServiceCallback {
    void serviceFailure(Exception exception);

    void serviceSuccess(com.dclover.gpsutilities.thoitiet.datas.Channel channel);
}
