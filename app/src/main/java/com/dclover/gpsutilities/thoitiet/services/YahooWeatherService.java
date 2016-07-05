package com.dclover.gpsutilities.thoitiet.services;

import android.net.Uri;
import android.os.AsyncTask;

import com.dclover.gpsutilities.thoitiet.datas.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class YahooWeatherService {
    private WeatherServiceCallback callback;
    private Exception error;
    private String location;

    /* renamed from: com.sundroid.myapplication.services.YahooWeatherService.1 */
    class C02091 extends AsyncTask<String, Void, String> {
        C02091() {
        }

        protected String doInBackground(String... strings) {
            String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")", new Object[]{strings[0]});
            String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", new Object[]{Uri.encode(YQL)});
           // Log.d(NotificationCompatApi21.CATEGORY_MESSAGE, "EndPoint:--" + endpoint);
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(endpoint).openConnection().getInputStream()));
                StringBuilder result = new StringBuilder();
                while (true) {
                    String line = reader.readLine();
                    if (line != null) {
                        result.append(line);
                    } else {
                       // Log.d(NotificationCompatApi21.CATEGORY_MESSAGE, "Result:-" + result.toString());
                        return result.toString();
                    }
                }
            } catch (Exception e) {
                YahooWeatherService.this.error = e;
                return null;
            }
        }

        protected void onPostExecute(String s) {
            if (s != null || YahooWeatherService.this.error == null) {
                try {
                    JSONObject data = new JSONObject(s);
                   // Log.d(NotificationCompatApi21.CATEGORY_MESSAGE, "JSONOBJ:---" + s);
                    JSONObject queryResults = data.optJSONObject("query");
                    if (queryResults.optInt("count") == 0) {
                        YahooWeatherService.this.callback.serviceFailure(new LocationWeatherException("Khồn tìm thấy thông tin thời tiết ở " + YahooWeatherService.this.location));
                        return;
                    }
                    Channel channel = new Channel();
                    channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));
                    YahooWeatherService.this.callback.serviceSuccess(channel);
                    return;
                } catch (JSONException e) {
                    YahooWeatherService.this.callback.serviceFailure(e);
                    return;
                }
            }
            YahooWeatherService.this.callback.serviceFailure(YahooWeatherService.this.error);
        }
    }

    public class LocationWeatherException extends Exception {
        public LocationWeatherException(String detailMessage) {
            super(detailMessage);
        }
    }

    public YahooWeatherService(WeatherServiceCallback callback) {
        this.callback = callback;
    }

    public String getLocation() {
        return this.location;
    }

    public void refreshWeather(String loc) {
        this.location = loc;
        //Log.d(NotificationCompatApi21.CATEGORY_MESSAGE, loc);
        new C02091().execute(new String[]{this.location});
    }
}
