package com.dclover.gpsutilities.thoitiet.datas;

import org.json.JSONObject;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class Channel implements JSONPopulator {
    private Astronomy astronomy;
    private Atmosphere atmosphere;
    private Item item;
    private Location location;
    private Units units;
    private Wind wind;

    public Item getItem() {
        return this.item;
    }

    public Units getUnits() {
        return this.units;
    }

    public Astronomy getAstronomy() {
        return this.astronomy;
    }

    public Atmosphere getAtmosphere() {
        return this.atmosphere;
    }

    public Location getLocation() {
        return this.location;
    }

    public Wind getWind() {
        return this.wind;
    }

    public void populate(JSONObject data) {
        this.units = new Units();
        this.units.populate(data.optJSONObject("units"));
        this.item = new Item();
        this.item.populate(data.optJSONObject("item"));
        this.location = new Location();
        this.location.populate(data.optJSONObject("location"));
        this.wind = new Wind();
        this.wind.populate(data.optJSONObject("wind"));
        this.atmosphere = new Atmosphere();
        this.atmosphere.populate(data.optJSONObject("atmosphere"));
        this.astronomy = new Astronomy();
        this.astronomy.populate(data.optJSONObject("astronomy"));
    }
}
