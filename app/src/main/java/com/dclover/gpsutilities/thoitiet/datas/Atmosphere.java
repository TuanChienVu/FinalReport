package com.dclover.gpsutilities.thoitiet.datas;

import org.json.JSONObject;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class Atmosphere implements JSONPopulator {
    private String humidity;
    private String pressure;
    private String rising;
    private String visibility;

    public String getVisibility() {
        return this.visibility;
    }

    public String getPressure() {
        return this.pressure;
    }

    public String getHumidity() {
        return this.humidity;
    }

    public String getRising() {
        return this.rising;
    }

    public void populate(JSONObject data) {
        this.humidity = data.optString("humidity");
        this.pressure = data.optString("pressure");
        this.rising = data.optString("rising");
        this.visibility = data.optString("visibility");
    }
}

