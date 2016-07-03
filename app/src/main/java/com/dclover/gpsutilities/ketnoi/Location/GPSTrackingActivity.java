package com.dclover.gpsutilities.ketnoi.Location;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.ketnoi.Message.SharedPrefsUtils;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GPSTrackingActivity extends AppCompatActivity {

    ImageButton btnShowLocation, btnRefresh;
    List<Location> mLocationsList;
    // GPSTracker class
    GPSTracker gps;
    // Google Map
    private GoogleMap googleMap;
    Firebase myFirebase;
    String userID;
    String userName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpstracking);
        myFirebase = new Firebase("https://detaikhoaluan.firebaseio.com/Location/");
        Firebase.setAndroidContext(this);
        userID = SharedPrefsUtils.getStringPreference(GPSTrackingActivity.this, "UserID");
        userName = SharedPrefsUtils.getStringPreference(GPSTrackingActivity.this, "UserName");
        mLocationsList = new ArrayList<Location>();
        btnShowLocation = (ImageButton) findViewById(R.id.btn_GetGPS);
        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

//        callAsynchronousTask();


        btnRefresh = (ImageButton) findViewById(R.id.btn_Refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mLocationsList.size(); i++) {
                    // create marker
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(mLocationsList.get(i).getLat(),
                            mLocationsList.get(i).getLog())).title(mLocationsList.get(i).getUserName());
                    googleMap.addMarker(marker);
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(
                            new LatLng(mLocationsList.get(i).getLat(), mLocationsList.get(i).getLog())).zoom(20).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    btnShowLocation.setVisibility(View.VISIBLE);
                }
            }
        });
        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(GPSTrackingActivity.this);
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                // check if GPS enabled
                if (gps.canGetLocation()) {
                    Location location = new Location();
                    location.setLat(latitude);
                    location.setLog(longitude);
                    location.setUserID(userID);
                    location.setUserName(userName);
                    myFirebase.push().setValue(location);
                    for (int i = 0; i < mLocationsList.size(); i++) {
                        // create marker
                        MarkerOptions marker = new MarkerOptions().position(new LatLng(mLocationsList.get(i).getLat(),
                                mLocationsList.get(i).getLog())).title(mLocationsList.get(i).getUserName());
                        googleMap.addMarker(marker);
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                                new LatLng(mLocationsList.get(i).getLat(), mLocationsList.get(i).getLog())).zoom(20).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                    btnShowLocation.setVisibility(View.INVISIBLE);
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
            }
        });

        myFirebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mLocationsList.add(dataSnapshot.getValue(Location.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            for (int i = 0; i < mLocationsList.size(); i++) {
                                if (mLocationsList.get(i).getLat() == mLocationsList.get(i).getLat()
                                        && mLocationsList.get(i).getLog() == mLocationsList.get(i).getLog()
                                        && mLocationsList.get(i).getUserID() == mLocationsList.get(i).getUserID()
                                        && mLocationsList.get(i).getUserName() == mLocationsList.get(i).getUserName()) {
                                    Toast.makeText(GPSTrackingActivity.this, mLocationsList.get(i).getUserName()
                                            + " hasn't moved anyway", Toast.LENGTH_SHORT).show();
                                } else if (mLocationsList == null) {
                                    double latitude = gps.getLatitude();
                                    double longitude = gps.getLongitude();
                                    Location location = new Location();
                                    location.setLat(latitude);
                                    location.setLog(longitude);
                                    location.setUserID(userID);
                                    location.setUserName(userName);

                                    myFirebase.push().setValue(location);
                                } else {
                                    double latitude = gps.getLatitude();
                                    double longitude = gps.getLongitude();
                                    Location location = new Location();
                                    location.setLat(latitude);
                                    location.setLog(longitude);
                                    location.setUserID(userID);
                                    location.setUserName(userName);

                                    myFirebase.push().setValue(location);
                                }
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000); //execute in every 50000 ms
    }

    /**
     * function to load map. If map is not created it will create it for you
     */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
}
