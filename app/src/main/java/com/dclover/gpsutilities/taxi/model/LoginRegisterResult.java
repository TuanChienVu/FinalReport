package com.dclover.gpsutilities.taxi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kinghero on 6/6/2016.
 */
public class LoginRegisterResult {

    @SerializedName("errorString")
    @Expose
    private String errorString;
    @SerializedName("userEmail")
    @Expose
    private String userEmail;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("regId")
    @Expose
    private String regId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("result")
    @Expose
    private Integer result;

    public String getAll()
    {
        return "errorString:"+errorString+",userEmail:"+userEmail+",avatar:"+avatar+",regId:"+regId+",userName:"+userName+",result:"+result;
    }

    /**
     * @return The errorString
     */
    public String getErrorString() {
        return errorString;
    }

    /**
     * @param errorString The errorString
     */
    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    /**
     * @return The userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * @param userEmail The userEmail
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * @return The avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar The avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return The regId
     */
    public String getRegId() {
        return regId;
    }

    /**
     * @param regId The regId
     */
    public void setRegId(String regId) {
        this.regId = regId;
    }

    /**
     * @return The userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName The userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return The result
     */
    public Integer getResult() {
        return result;
    }

    /**
     * @param result The result
     */
    public void setResult(Integer result) {
        this.result = result;
    }
}
