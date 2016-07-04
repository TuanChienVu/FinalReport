package com.dclover.gpsutilities.maps.mapmoduls.autocomplete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kinghero on 7/4/2016.
 */
public class ResualAutocomplete {
    @SerializedName("predictions")
    @Expose
    private List<com.dclover.gpsutilities.maps.mapmoduls.autocomplete.Prediction> predictions = new ArrayList<com.dclover.gpsutilities.maps.mapmoduls.autocomplete.Prediction>();
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * @return The predictions
     */
    public List<com.dclover.gpsutilities.maps.mapmoduls.autocomplete.Prediction> getPredictions() {
        return predictions;
    }

    /**
     * @param predictions The predictions
     */
    public void setPredictions(List<com.dclover.gpsutilities.maps.mapmoduls.autocomplete.Prediction> predictions) {
        this.predictions = predictions;
    }
}