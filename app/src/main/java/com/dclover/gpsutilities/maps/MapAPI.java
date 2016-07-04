package com.dclover.gpsutilities.maps;

import com.dclover.gpsutilities.maps.mapmoduls.autocomplete.ResualAutocomplete;
import com.dclover.gpsutilities.maps.mapmoduls.place.ResualPlace;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Kinghero on 7/4/2016.
 */
public interface MapAPI {
    @GET("/maps/api/place/autocomplete/json?region=vn")
    void getDiadiemGoiY(@Query("input") String input, @Query("key") String key, Callback<ResualAutocomplete> repon);
    @GET("/maps/api/place/details/json?region=vn")
    void getPlace(@Query("placeid") String id, @Query("key") String key, Callback<ResualPlace> repon);
}
