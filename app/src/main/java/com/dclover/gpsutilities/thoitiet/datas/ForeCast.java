package com.dclover.gpsutilities.thoitiet.datas;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class ForeCast implements JSONARRAYPopulator {
    ArrayList<ForeCastData> foreCastDatas;

    public ArrayList<ForeCastData> getForeCastDatas() {
        return this.foreCastDatas;
    }

    public void populateArray(JSONArray data) {
        this.foreCastDatas = new ArrayList();
        for (int i = 0; i < data.length(); i++) {
            try {
                ForeCastData foreCastData = new ForeCastData();
                foreCastData.date = data.getJSONObject(i).optString("date");
                foreCastData.day = data.getJSONObject(i).optString("day");
                foreCastData.high = data.getJSONObject(i).optString("high");
                foreCastData.low = data.getJSONObject(i).optString("low");
                foreCastData.text = data.getJSONObject(i).optString("text");
                foreCastData.code = data.getJSONObject(i).optString("code");
                this.foreCastDatas.add(foreCastData);
            } catch (Exception e) {
                //Log.d(NotificationCompatApi21.CATEGORY_MESSAGE, "error forecast:-" + e.toString());
            }
        }
    }
}
