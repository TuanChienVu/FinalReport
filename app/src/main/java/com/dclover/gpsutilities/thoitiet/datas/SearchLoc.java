package com.dclover.gpsutilities.thoitiet.datas;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class SearchLoc {
    private String country;
    private String name;

    public SearchLoc(String name, String country) {
        this.country = country;
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return this.country;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name + "," + this.country;
    }
}
