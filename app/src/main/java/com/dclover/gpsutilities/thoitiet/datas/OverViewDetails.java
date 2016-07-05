package com.dclover.gpsutilities.thoitiet.datas;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class OverViewDetails {
    String Pressure;
    String humidity;
    String sunrise;
    String sunset;
    String visibility;
    String wind;

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setPressure(String pressure) {
        this.Pressure = pressure;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getSunrise() {
        return this.sunrise;
    }

    public String getSunset() {
        return this.sunset;
    }

    public String getHumidity() {
        return this.humidity;
    }

    public String getPressure() {
        return this.Pressure;
    }

    public String getVisibility() {
        return this.visibility;
    }

    public String getWind() {
        return this.wind;
    }
}
