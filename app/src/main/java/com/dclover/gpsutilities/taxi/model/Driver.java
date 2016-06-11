package com.dclover.gpsutilities.taxi.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kinghero on 6/7/2016.
 */
public class Driver {
    private double angle;
    private String avatar;
    private int carType;
    private String driverId;
    private String driverName;
    private double lat;
    private String licensePlate;
    @SerializedName("long")
    private double lng;
    private String phoneNumber;
    private double rate;
    private String taxiNumber;

    public String getDriverId() {
        return this.driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return this.driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getTaxiNumber() {
        return this.taxiNumber;
    }

    public void setTaxiNumber(String taxiNumber) {
        this.taxiNumber = taxiNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLicensePlate() {
        return this.licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getCarType() {
        return this.carType;
    }

    public void setCarType(int carType) {
        this.carType = carType;
    }

    public double getLng() {
        return this.lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getRate() {
        return this.rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
