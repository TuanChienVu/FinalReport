package com.dclover.gpsutilities.taxi.Utils;

import com.dclover.gpsutilities.taxi.model.Driver;
import com.dclover.gpsutilities.taxi.model.LoginRegisterResult;
import com.dclover.gpsutilities.taxi.model.PhoneCenterModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Kinghero on 6/6/2016.
 */
public interface APIs {

    @GET("/TaxiOperation/rest/MobileWS/login")
    void getLogin( @Query("phonenumber") String phone,@Query("pass") String pass,Callback<LoginRegisterResult> repon);
    @GET("/TaxiOperation/rest/MobileWS/register")
    void getRegister( @Query("phonenumber") String phone,@Query("pass") String pass,Callback<LoginRegisterResult> repon);
    @GET("/TaxiOperation/rest/MobileWS/resetPassword")
    void getResetPass( @Query("phonenumber") String phone,Callback<LoginRegisterResult> repon);
    @GET("/TaxiOperation/rest/MobileWS/get_driver_info")
    void getDriverInfor( @Query("driver_id") String driver_id,Callback<Driver> repon);
    @GET("/TaxiOperation/rest/MobileWS/get_callcenter")
    void GetCenterCall(Callback<List<PhoneCenterModel>> repon);
}
