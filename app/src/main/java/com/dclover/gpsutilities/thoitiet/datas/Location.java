package com.dclover.gpsutilities.thoitiet.datas;

import org.json.JSONObject;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class Location implements JSONPopulator {
    private String city;
    private String country;
    private String region;

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public String getRegion() {
        return this.region;
    }

    public void populate(JSONObject data) {
        this.city = data.optString("city");
        this.city = data.optString("country");
        this.city = data.optString("region");
    }
}
