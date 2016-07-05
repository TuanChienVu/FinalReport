package com.dclover.gpsutilities.thoitiet.datas;

import org.json.JSONObject;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class Astronomy implements JSONPopulator {
    private String sunrise;
    private String sunset;

    public String getSunrise() {
        return this.sunrise;
    }

    public String getSunset() {
        return this.sunset;
    }

    public void populate(JSONObject data) {
        this.sunrise = data.optString("sunrise");
        this.sunset = data.optString("sunset");
    }
}
