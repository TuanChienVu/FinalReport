package com.dclover.gpsutilities.taxi.Utils;

import android.util.Log;

/**
 * Created by Kinghero on 6/7/2016.
 */
public class ClientCommand {
    public static void doLogin() {
        if (Env.getUser() != null) {
            SocketUtils.getSingleton().sendMessage((Constants.CMD_CLIENT_LOGIN + "{\"phoneNumber\":\"" + Env.getUser().getPhoneNo() + "\",\"uuid\":" + "\"" + Env.getUser().getDeviceUUID() + "\"}") + Constants.CMD_END);
            Log.d("dd","login:"+(Constants.CMD_CLIENT_LOGIN + "{\"phoneNumber\":\"" + Env.getUser().getPhoneNo() + "\",\"uuid\":" + "\"" + Env.getUser().getDeviceUUID() + "\"}") + Constants.CMD_END);
        }
    }
    public static void updateRiderLocation(double longitude, double latitude) {
        SocketUtils.getSingleton().sendMessage((("$rider_update_loc={" + "\"long\":" + longitude + ",") + "\"lat\":" + latitude) + "}$end");

    }

}
