package com.dclover.gpsutilities.thoitiet.datas;

import org.json.JSONObject;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class Wind implements JSONPopulator {
    private String chill;
    private String direction;
    private String speed;

    public String getSpeed() {
        return this.speed;
    }

    public String getChill() {
        return this.chill;
    }

    public String getDirection() {
        return this.direction;
    }

    public void populate(JSONObject data) {
        this.chill = data.optString("chill");
        this.direction = data.optString("direction");
        this.speed = data.optString("speed");
    }
}

