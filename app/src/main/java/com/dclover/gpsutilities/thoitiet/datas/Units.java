package com.dclover.gpsutilities.thoitiet.datas;

import org.json.JSONObject;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class Units implements JSONPopulator {
    private String distance;
    private String pressure;
    private String speed;
    private String temperature;

    public String getTemperature() {
        return this.temperature;
    }

    public String getDistance() {
        return this.distance;
    }

    public String getPressure() {
        return this.pressure;
    }

    public String getSpeed() {
        return this.speed;
    }

    public void populate(JSONObject data) {
        this.temperature = data.optString("temperature");
        this.distance = data.optString("distance");
        this.pressure = data.optString("pressure");
        this.speed = data.optString("speed");
    }
}
