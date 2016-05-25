package com.dclover.gpsutilities.maps;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.khoihanh.moduls.DirectionFinder;
import com.dclover.gpsutilities.khoihanh.moduls.DirectionFinderListener;
import com.dclover.gpsutilities.khoihanh.moduls.Route;
import com.dclover.gpsutilities.maps.mapmoduls.GetDirection;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
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

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GetDirection.OnCompleteGetDirection, DirectionFinderListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    Location myLocation;
    EditText searchLocationB, searchLocationA;
    FloatingActionsMenu fabMenu;
    InputMethodManager imm;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    LocationManager locationManager;
    LatLng DiemA, DiemB;
    boolean thucthi = false;
    TextView txtTime,txtMoney,txtKhoangCach;
    CardView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        display=(CardView) findViewById(R.id.cvDisplay);
        txtTime=(TextView) findViewById(R.id.txtTime);
        txtMoney=(TextView) findViewById(R.id.txtMoney);
        txtKhoangCach=(TextView) findViewById(R.id.txtKhoangCach);

        searchLocationA = (EditText) findViewById(R.id.searchLocationA);

        fabMenu = (FloatingActionsMenu) findViewById(R.id.fab_menu);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        searchLocationB = (EditText) findViewById(R.id.searchLocation);
        searchLocationA = (EditText) findViewById(R.id.searchLocationA);


        searchLocationA.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String[] lat_long = new String[2];
/*
                ReadWriteFile readWriteFile = new ReadWriteFile();
                String type_zoom = readWriteFile.read("config.txt");
                String[] parts = new String[20];
                parts = type_zoom.split(";");
                parts[0] = parts[0].replace("\r", "");
                parts[1] = parts[1].replace("\n", "");
                int type = Integer.parseInt(parts[0]);
                int zoom = Integer.parseInt(parts[1]);*/

                lat_long = convertAddress(searchLocationA.getText().toString());

                DiemA = new LatLng(Double.valueOf(lat_long[0]), Double.valueOf(lat_long[1]));
                MarkerOptions option = new MarkerOptions();
                option.position(DiemA);
                option.title(searchLocationA.getText().toString()).snippet("Nơi Xuất Phát");
                option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                option.alpha(0.8f);
                //option.rotation(90);
                Marker maker = mMap.addMarker(option);
                maker.showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DiemA, 14));

//                mMap.setMapType(type);

                return false;
            }
        });


        searchLocationB.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String[] lat_long = new String[2];
/*

                ReadWriteFile readWriteFile = new ReadWriteFile();
                String type_zoom = readWriteFile.read("config.txt");
                String[] parts = new String[20];
                parts = type_zoom.split(";");
                parts[0] = parts[0].replace("\r", "");
                parts[1] = parts[1].replace("\n", "");
                int type = Integer.parseInt(parts[0]);
                int zoom = Integer.parseInt(parts[1]);
*/

                lat_long = convertAddress(searchLocationB.getText().toString());

                DiemB = new LatLng(Double.valueOf(lat_long[0]), Double.valueOf(lat_long[1]));
                MarkerOptions option = new MarkerOptions();
                option.position(DiemB);
                option.title(searchLocationB.getText().toString()).snippet("Điểm Đến");
                option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                option.alpha(0.8f);
                //option.rotation(90);
                Marker maker = mMap.addMarker(option);
                maker.showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DiemB, 14));
//                mMap.setMapType(type);

                if(thucthi==true) {
                    sendRequestAB();
                }

                return false;
            }

        });
    }


    boolean chay = false;

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (chay == false) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));

                    googleMap.moveCamera(center);

                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
                    googleMap.animateCamera(zoom);
                    chay = true;
                }

                myLocation = location;

            }
        });

        this.mMap = googleMap;
        setUpMap();
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                googleMap.clear();
                dau = true;

                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Tới đây")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                sendRequest(latLng);
            }
        });

    }

    private void sendRequest(LatLng latLng) {


        try {
            new DirectionFinder((DirectionFinderListener) this, myLocation.getLatitude() + "," + myLocation.getLongitude(), latLng.latitude + "," + latLng.longitude).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void sendRequestAB() {


        try {
            new DirectionFinder((DirectionFinderListener) this, DiemA.latitude + "," + DiemA.longitude, DiemB.latitude + "," + DiemB.longitude).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private void setUpMap() {
        // Enable MyLocation Layer of Google Map
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);


    }

    boolean dau = true;

    @Override
    public void onCompleteGetDirection(boolean isOK, Route direction, String... msg) {
        if (isOK) {
            //di vao activity tiep theo
            //Toast.makeText(this, "GetDirection1111111111 = " + msg[0], Toast.LENGTH_SHORT).show();

            for (int i = 0; i < (direction.getSteps().size()); i++) {
                //Toast.makeText(this, "direction = " + direction.getSteps().get(i).getPoints(), Toast.LENGTH_SHORT).show();
                ArrayList<LatLng> directionPoint = decodePoly(direction.getSteps().get(i).getPoints());
                for (int j = 0; j < directionPoint.size(); j++) {
                    //rectLine.add(directionPoint.get(j));
                    //Toast.makeText(this, "ponit_end = " + directionPoint.get(j), Toast.LENGTH_SHORT).show();

                    PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED); // Màu và độ rộng
                    for (int k = 0; k < directionPoint.size(); k++) {
                        rectLine.add(directionPoint.get(k));
                    }
                    mMap.addPolyline(rectLine);


                }
            }

        } else {
            Toast.makeText(this, "GetDirection = " + msg[0], Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<LatLng> decodePoly(String encoded) {
        ArrayList<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng position = new LatLng((double) lat / 1E5, (double) lng / 1E5);
            poly.add(position);
        }
        return poly;
    }

    public String[] convertAddress(String address) {

        double lat = 0;
        double lon = 0;

        String[] lat_log = new String[2];

        Geocoder geoCoder = new Geocoder(this);

        if (address != null && !address.isEmpty()) {
            try {
                List<Address> addressList = geoCoder.getFromLocationName(address, 1);
                if (addressList != null && addressList.size() > 0) {
                    lat = addressList.get(0).getLatitude();
                    lon = addressList.get(0).getLongitude();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } // end catch
        } // end if

        lat_log[0] = String.valueOf(lat);
        lat_log[1] = String.valueOf(lon);

        return lat_log;

    } // end convertAddress

    public void enterLocation(View view) {
        fabMenu.collapse();
        thucthi=true;
        searchLocationA.setVisibility(View.VISIBLE);
        searchLocationA.requestFocus();
        searchLocationB.setText("");
        imm.showSoftInput(searchLocationB, InputMethodManager.SHOW_IMPLICIT);
    }

    public void selectStyle(View view) {
        fabMenu.collapse();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setItems(new CharSequence[]{"NORMAL", "HYBRID", "TERRAIN", "SATELLITE", "NONE"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        } else if (i == 1) {
                            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        } else if (i == 2) {
                            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        } else if (i == 3) {
                            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        } else {
                            mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                        }
                    }
                });
        dialogBuilder.create().show();
    }

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


    public void onDirectionFinderSuccess(List<Route> directions) {
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();


        if (directions.size() > 1) {
            for (int i = 1; i < directions.size(); i++) {
                PolylineOptions polylineOptions = new PolylineOptions().
                        geodesic(true).
                        color(Color.GRAY).
                        width(9);
                for (int j = 0; j < directions.get(i).points.size(); j++)
                    polylineOptions.add(directions.get(i).points.get(j));

                polylinePaths.add(mMap.addPolyline(polylineOptions));
            }
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(9);
            for (int j = 0; j < directions.get(0).points.size(); j++)
                polylineOptions.add(directions.get(0).points.get(j));

            polylinePaths.add(mMap.addPolyline(polylineOptions));

            txtTime.setText(directions.get(0).duration.text);
            txtKhoangCach.setText("Khoảng cách: "+directions.get(0).distance.text);

            float khoangcach=(float)directions.get(0).distance.value/1000;
            float tieuthu=(float)(khoangcach*14)/100;
            Locale locale = new Locale("vi", "VN");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            String kq=fmt.format(tieuthu*17000).toString().replace(".",",");


            txtMoney.setText("Tiền: "+kq);
            display.setVisibility(View.VISIBLE); } else {
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(9);


            for (int i = 0; i < directions.get(0).points.size(); i++)
                polylineOptions.add(directions.get(0).points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));

            txtTime.setText(directions.get(0).duration.text);
            txtKhoangCach.setText("Khoảng cách: "+directions.get(0).distance.text);
            float khoangcach=(float)directions.get(0).distance.value/1000;
            float tieuthu=(float)(khoangcach*14)/100;
            Locale locale = new Locale("vi", "VN");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            String kq=fmt.format(tieuthu*17000).toString().replace(".",",");


            txtMoney.setText("Tiền: "+kq);
            display.setVisibility(View.VISIBLE);
        }

    }
}
