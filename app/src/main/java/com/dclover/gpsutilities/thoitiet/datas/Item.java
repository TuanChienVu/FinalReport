package com.dclover.gpsutilities.thoitiet.datas;

import org.json.JSONObject;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class Item implements JSONPopulator {
    private Condition1 condition;
    private ForeCast foreCast;
    private String title;

    public Condition1 getCondition() {
        return this.condition;
    }

    public ForeCast getForeCast() {
        return this.foreCast;
    }

    public String getTitle() {
        return this.title;
    }

    public void populate(JSONObject data) {
        this.condition = new Condition1();
        this.condition.populate(data.optJSONObject("condition"));
        this.foreCast = new ForeCast();
        this.foreCast.populateArray(data.optJSONArray("forecast"));
        this.title = data.optString("title");
    }
}

