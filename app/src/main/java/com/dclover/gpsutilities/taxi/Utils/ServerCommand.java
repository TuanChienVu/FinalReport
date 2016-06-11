package com.dclover.gpsutilities.taxi.Utils;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by Kinghero on 6/7/2016.
 */
public class ServerCommand {
    public static void processCommand(String command) {
        if (command != null) {
            String commandContent = XmlPullParser.NO_NAMESPACE;
            Intent intent;
            if (command.startsWith(Constants.CMD_SERVER_LOGIN_SUCCESS)) {
                commandContent = command.replace(Constants.CMD_SERVER_LOGIN_SUCCESS, XmlPullParser.NO_NAMESPACE).replace(Constants.CMD_END, XmlPullParser.NO_NAMESPACE);
                intent = new Intent(Constants.CMD_SERVER_LOGIN_SUCCESS);
                intent.putExtra(Constants.CMD_SERVER_LOGIN_SUCCESS, commandContent);
                Log.i("1.LOGIN_SUCCESS", commandContent);
                LocalBroadcastManager.getInstance(Env.getApplication()).sendBroadcast(intent);
            } else if (command.startsWith(Constants.CMD_SERVER_UPDATE_DRIVER_INFO)) {
                commandContent = command.replace(Constants.CMD_SERVER_UPDATE_DRIVER_INFO, XmlPullParser.NO_NAMESPACE).replace(Constants.CMD_END, XmlPullParser.NO_NAMESPACE);
                intent = new Intent(Constants.CMD_SERVER_UPDATE_DRIVER_INFO);
                intent.putExtra(Constants.CMD_SERVER_UPDATE_DRIVER_INFO, commandContent);
                LocalBroadcastManager.getInstance(Env.getApplication()).sendBroadcast(intent);
                Log.i("2.UPDATE_DRIVER_INFO", commandContent);
            } else if (command.startsWith(Constants.CMD_SERVER_REQUEST_TRIP_RETURN)) {
                commandContent = command.replace(Constants.CMD_SERVER_REQUEST_TRIP_RETURN, XmlPullParser.NO_NAMESPACE).replace(Constants.CMD_END, XmlPullParser.NO_NAMESPACE);
                intent = new Intent(Constants.CMD_SERVER_REQUEST_TRIP_RETURN);
                intent.putExtra(Constants.CMD_SERVER_REQUEST_TRIP_RETURN, commandContent);
                LocalBroadcastManager.getInstance(Env.getApplication()).sendBroadcast(intent);
                Log.i("3.REQUEST_TRIP_RETURN", commandContent);
            } else if (command.startsWith(Constants.CMD_SERVER_REQUEST_TRIP_FAIL)) {
                commandContent = command.replace(Constants.CMD_SERVER_REQUEST_TRIP_FAIL, XmlPullParser.NO_NAMESPACE).replace(Constants.CMD_END, XmlPullParser.NO_NAMESPACE);
                intent = new Intent(Constants.CMD_SERVER_REQUEST_TRIP_FAIL);
                intent.putExtra(Constants.CMD_SERVER_REQUEST_TRIP_FAIL, commandContent);
                LocalBroadcastManager.getInstance(Env.getApplication()).sendBroadcast(intent);
                Log.i("4.REQUEST_TRIP_FAIL", commandContent);
            } else if (command.startsWith(Constants.CMD_SERVER_UPDATE_TRIP_SUMMARY)) {
                commandContent = command.replace(Constants.CMD_SERVER_UPDATE_TRIP_SUMMARY, XmlPullParser.NO_NAMESPACE).replace(Constants.CMD_END, XmlPullParser.NO_NAMESPACE);
                intent = new Intent(Constants.CMD_SERVER_UPDATE_TRIP_SUMMARY);
                intent.putExtra(Constants.CMD_SERVER_UPDATE_TRIP_SUMMARY, commandContent);
                LocalBroadcastManager.getInstance(Env.getApplication()).sendBroadcast(intent);
                Log.i("5.UPDATE_TRIP_SUMMARY", commandContent);
            } else if (command.startsWith(Constants.CMD_SERVER_UPDATE_TRIP)) {
                commandContent = command.replace(Constants.CMD_SERVER_UPDATE_TRIP, XmlPullParser.NO_NAMESPACE).replace(Constants.CMD_END, XmlPullParser.NO_NAMESPACE);
                intent = new Intent(Constants.CMD_SERVER_UPDATE_TRIP);
                intent.putExtra(Constants.CMD_SERVER_UPDATE_TRIP, commandContent);
                LocalBroadcastManager.getInstance(Env.getApplication()).sendBroadcast(intent);
                Log.i("6.SERVER_UPDATE_TRIP", commandContent);
            } else if (command.startsWith(Constants.CMD_SERVER_CANCEL_RETURN)) {
                commandContent = command.replace(Constants.CMD_SERVER_CANCEL_RETURN, XmlPullParser.NO_NAMESPACE).replace(Constants.CMD_END, XmlPullParser.NO_NAMESPACE);
                intent = new Intent(Constants.CMD_SERVER_CANCEL_RETURN);
                intent.putExtra(Constants.CMD_SERVER_CANCEL_RETURN, commandContent);
                LocalBroadcastManager.getInstance(Env.getApplication()).sendBroadcast(intent);
                Log.i("7.SERVER_CANCEL_RETURN", commandContent);
            } else if (command.startsWith(Constants.CMD_SERVER_NOTIFY_ARRIVED)) {
                LocalBroadcastManager.getInstance(Env.getApplication()).sendBroadcast(new Intent(Constants.CMD_SERVER_NOTIFY_ARRIVED));
                Log.i("8.SERVER_NOTIFY_ARRIVED", commandContent);
            }
        }
    }
    }
