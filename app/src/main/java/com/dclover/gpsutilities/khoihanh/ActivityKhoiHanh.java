package com.dclover.gpsutilities.khoihanh;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.khoihanh.moduls.DirectionFinder;
import com.dclover.gpsutilities.khoihanh.moduls.DirectionFinderListener;
import com.dclover.gpsutilities.khoihanh.moduls.Route;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityKhoiHanh extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap googleMap;
    Location myLocation;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    LocationManager locationManager;
    CardView display;
    LatLng den;



    TextView txtTime,txtMoney,txtKhoangCach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoi_hanh);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        display=(CardView) findViewById(R.id.cvDisplay);
        txtTime=(TextView) findViewById(R.id.txtTime);
        txtMoney=(TextView) findViewById(R.id.txtMoney);
        txtKhoangCach=(TextView) findViewById(R.id.txtKhoangCach);
    }

    boolean chay=false;
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if(chay==false) {
                    CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));

                    googleMap.moveCamera(center);

                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
                    googleMap.animateCamera(zoom);
                    chay=true;
                }

                myLocation=location;

            }
        });


        this.googleMap = googleMap;
        setUpMap();
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {
                display.setVisibility(View.GONE);
                googleMap.clear();
                dau=true;

                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Tới đây")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                sendRequest(latLng);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("dd","chay");
                        googleMap.clear();
                        sendRequest(latLng);
                        handler.postDelayed(this,1000);

                    }
                }, 1000);
            }
        });


    }

    private void sendRequest(LatLng latLng) {


        try {
            new DirectionFinder(this, myLocation.getLatitude() + "," + myLocation.getLongitude(), latLng.latitude + "," + latLng.longitude).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void setUpMap() {
        // Enable MyLocation Layer of Google Map
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);


        // Get LocationManager object from System Service LOCATION_SERVICE
//        LocationManager locationManager = (LocationManager)
//                getSystemService(Context.LOCATION_SERVICE);
//
//        // Create a criteria object to retrieve provider
//        Criteria criteria = new Criteria();
//
//        // Get the name of the best provider
//        String provider = locationManager.getBestProvider(criteria, true);
//
//        // Get Current Location
//        myLocation = locationManager.getLastKnownLocation(provider);
//
//        // Get latitude of the current location
//        double latitude = myLocation.getLatitude();
//
//        // Get longitude of the current location
//        double longitude = myLocation.getLongitude();
//
//        // Create a LatLng object for the current location
//        LatLng latLng = new LatLng(latitude, longitude);
//
//        // Show the current location in Google Map
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//
//        // Zoom in the Google Map
//        googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
//
//        Log.d("ddd", "" + latitude + "_" + longitude);

    }
    boolean dau=true;
    @Override
    public void onDirectionFinderStart() {
        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> route) {
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();


        if(route.size()>1)
        {
            for(int i=1;i<route.size();i++)
            {
                PolylineOptions polylineOptions = new PolylineOptions().
                        geodesic(true).
                        color(Color.GRAY).
                        width(9);
                for (int j = 0; j < route.get(i).points.size(); j++)
                    polylineOptions.add(route.get(i).points.get(j));

                polylinePaths.add(googleMap.addPolyline(polylineOptions));
            }
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(9);
            for (int j = 0; j < route.get(0).points.size(); j++)
                polylineOptions.add(route.get(0).points.get(j));

            polylinePaths.add(googleMap.addPolyline(polylineOptions));
            //Toast.makeText(getBaseContext(),route.get(0).distance.text+" - "+route.get(0).duration.text,Toast.LENGTH_SHORT).show();
            txtTime.setText(route.get(0).duration.text);
            txtKhoangCach.setText("Khoảng cách: "+route.get(0).distance.text);

            float khoangcach=(float)route.get(0).distance.value/1000;
            float tieuthu=(float)(khoangcach*14)/100;
            Locale locale = new Locale("vi", "VN");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            String kq=fmt.format(tieuthu*17000).toString().replace(".",",");


            txtMoney.setText("Tiền: "+kq);
            display.setVisibility(View.VISIBLE);
        }
        else
        {





            PolylineOptions  polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(9);


            for (int i = 0; i < route.get(0).points.size(); i++)
                polylineOptions.add(route.get(0).points.get(i));

            polylinePaths.add(googleMap.addPolyline(polylineOptions));
            //  Toast.makeText(getBaseContext(),route.get(0).distance.text+" - "+route.get(0).duration.text,Toast.LENGTH_SHORT).show();
            txtTime.setText(route.get(0).duration.text);
            txtKhoangCach.setText("Khoảng cách: "+route.get(0).distance.text);

            float khoangcach=(float)route.get(0).distance.value/1000;
            float tieuthu=(float)(khoangcach*14)/100;
            Locale locale = new Locale("vi", "VN");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            String kq=fmt.format(tieuthu*17000).toString().replace(".",",");


            txtMoney.setText("Tiền: "+kq);
            display.setVisibility(View.VISIBLE);

        }


    }

}
