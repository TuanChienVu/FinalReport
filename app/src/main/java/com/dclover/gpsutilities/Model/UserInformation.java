package com.dclover.gpsutilities.Model;

/**
 * Created by MY PC on 04/06/2016.
 */
public class UserInformation {
    String UserName;
    String UserEmail;
    String UserPassword;
    String UserID;

    public UserInformation() {
    }

    public UserInformation(String userName, String userEmail, String userPassword, String userID) {
        UserName = userName;
        UserEmail = userEmail;
        UserPassword = userPassword;
        UserID = userID;
    }
}
