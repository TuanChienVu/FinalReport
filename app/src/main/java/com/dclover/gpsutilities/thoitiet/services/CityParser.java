package com.dclover.gpsutilities.thoitiet.services;

import android.net.Uri;
import android.util.Log;

import com.dclover.gpsutilities.thoitiet.datas.SearchLoc;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class CityParser {
    public List<SearchLoc> getParseJsonWCF(String sName) {
        List<SearchLoc> ListData = new ArrayList();
        String temp = sName;
        try {
            String YQL = String.format("select * from geo.places where text = \"%s*\"", new Object[]{sName});
            String str = "query";
            //edit
            JSONObject query = new JSONObject(new BufferedReader(new InputStreamReader(new URL(String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", new Object[]{Uri.encode(YQL)})).openConnection().getInputStream())).readLine()).optJSONObject(str);
            JSONObject results = query.optJSONObject("results");
            int count = query.optInt("count");
            JSONArray jsonArray = results.getJSONArray("place");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject r = jsonArray.getJSONObject(i);
                JSONObject country = r.getJSONObject("country");
                Log.i("THIS", country.getString("content"));
                ListData.add(new SearchLoc(r.getString("name"), country.getString("content")));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return ListData;
    }
}
