package com.dclover.gpsutilities.thoitiet.datas;

import org.json.JSONObject;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class Condition1 implements JSONPopulator {
    private int code;
    private String date;
    private String description;
    private int temperature;

    public int getCode() {
        return this.code;
    }

    public int getTemperature() {
        return this.temperature;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDate() {
        return this.date;
    }

    public void populate(JSONObject data) {
        this.code = data.optInt("code");
        this.date = data.optString("date");
        this.temperature = data.optInt("temp");
        this.description = data.optString("text");
    }
}
