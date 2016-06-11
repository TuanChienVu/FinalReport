package com.dclover.gpsutilities.taxi.model;

/**
 * Created by Kinghero on 6/7/2016.
 */
public class User {
    private String avatar;
    private String deviceUUID;
    private String email;
    private String name;
    private String newPass;
    private String password;
    private String phoneNo;
    private String regId;

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDeviceUUID() {
        return this.deviceUUID;
    }

    public void setDeviceUUID(String deviceUUID) {
        this.deviceUUID = deviceUUID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPass() {
        return this.newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getRegId() {
        return this.regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
}
