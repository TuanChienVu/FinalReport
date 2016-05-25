package com.dclover.gpsutilities.khoihanh;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.khoihanh.moduls.DirectionFinder;
import com.dclover.gpsutilities.khoihanh.moduls.DirectionFinderListener;
import com.dclover.gpsutilities.khoihanh.moduls.Route;
import com.dclover.gpsutilities.khoihanh.speed.CLocation;
import com.dclover.gpsutilities.khoihanh.speed.IBaseGpsListener;
import com.getbase.floatingactionbutton.FloatingActionButton;
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

public class ActivityKhoiHanh extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener, IBaseGpsListener {

    private GoogleMap googleMap;
    Location myLocation;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    List<Route> route = new ArrayList<>();
    LocationManager locationManager;
    CardView display;
    LatLng den;
    Dialog dialog;


    SharedPreferences preferences;

    TextView txtTime, txtMoney, txtKhoangCach, txtSpeed;
    FloatingActionButton btnthaydiemden;
    FloatingActionsMenu actionsMenu;

    Button btngps, btnchon, btnthoat, btnkhoihanh,btnok;
    RelativeLayout rlChondiemden, rlBatDau;
    EditText editdiemden;
    ImageView imgNext, imgBack, imgGps;
    int duonght = 0, duongtimthay = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("long", "" + myLocation.getLongitude());
        editor.putString("lati", "" + myLocation.getLatitude());
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoi_hanh);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        display = (CardView) findViewById(R.id.cvDisplay);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtMoney = (TextView) findViewById(R.id.txtMoney);
        txtKhoangCach = (TextView) findViewById(R.id.txtKhoangCach);
        btnthaydiemden = (FloatingActionButton) findViewById(R.id.btnthaydiemden);
        actionsMenu = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        rlChondiemden = (RelativeLayout) findViewById(R.id.rl_chondiemden);
        rlBatDau = (RelativeLayout) findViewById(R.id.rlBatdau);
        btngps = (Button) findViewById(R.id.btngps);
        btnthoat = (Button) findViewById(R.id.btnthoat);
        btnchon = (Button) findViewById(R.id.btnchon);
        btnkhoihanh = (Button) findViewById(R.id.btnKhoiHanh);
        imgNext = (ImageView) findViewById(R.id.imgNext);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgGps = (ImageView) findViewById(R.id.imgGps);
        txtSpeed = (TextView) findViewById(R.id.txtSpeed);
        btnok=(Button) findViewById(R.id.btnOk);


        setupDialog();


        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlBatDau.setVisibility(View.VISIBLE);
                display.setVisibility(View.GONE);
            }
        });
        btnchon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                den = googleMap.getCameraPosition().target;
                rlChondiemden.setVisibility(View.GONE);
                dialog.show();
                editdiemden.setText("[Điểm Tọa Độ]");


            }
        });
        btngps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));
                googleMap.moveCamera(center);
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
                googleMap.animateCamera(zoom);
            }
        });
        btnthaydiemden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                actionsMenu.collapse();

            }
        });
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlChondiemden.setVisibility(View.GONE);
                dialog.show();
            }
        });

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                duonght++;
                if (duonght > (duongtimthay - 1))
                    duonght = 0;


                doiduong();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duonght--;
                if (duonght < 0)
                    duonght = (duongtimthay - 1);

                doiduong();
            }
        });
        imgGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myLocation != null) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));

                    googleMap.moveCamera(center);

                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
                    googleMap.animateCamera(zoom);
                    chay = true;
                }
            }
        });


        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        this.updateSpeed(null);
    }
    public void finish()
    {
        super.finish();
        System.exit(0);
    }

    private void updateSpeed(CLocation location) {
        // TODO Auto-generated method stub
        float nCurrentSpeed = 0;

        if(location != null)
        {
            location.setUseMetricunits(this.useMetricUnits());
            nCurrentSpeed = location.getSpeed();
        }




        txtSpeed.setText((int)nCurrentSpeed+"");

    }
    private boolean useMetricUnits() {
        // TODO Auto-generated method stub
//        CheckBox chkUseMetricUnits = (CheckBox) this.findViewById(R.id.chkMetricUnits);
//        return chkUseMetricUnits.isChecked();
        return false;
    }
    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        if(location != null)
        {
            CLocation myLocation = new CLocation(location, this.useMetricUnits());
            this.updateSpeed(myLocation);
        }
    }
    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGpsStatusChanged(int event) {
        // TODO Auto-generated method stub

    }


    private void setupDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_nhapdiemden);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, tinhPixel(380));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button btnMap=(Button) dialog.findViewById(R.id.btnMap);
        editdiemden=(EditText) dialog.findViewById(R.id.edtdiemden);
        btnkhoihanh=(Button) dialog.findViewById(R.id.btnKhoiHanh);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                rlChondiemden.setVisibility(View.VISIBLE);
            }
        });

        btnkhoihanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(den==null)
                {
                    customToast("Chưa có điểm đến");
                }
                else
                {

                    dialog.dismiss();
                    display.setVisibility(View.GONE);
                    googleMap.clear();
                    dau=true;

                    googleMap.addMarker(new MarkerOptions()
                            .position(den)
                            .title("Tới đây")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    sendRequest(den);
                }
            }
        });
        dialog.show();
    }

    private void customToast(String msg){
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = layoutInflater.inflate(R.layout.fb_comments_list_item, null,false);
        TextView tvTitle = (TextView) inflatedView.findViewById(R.id.tv_text_popup);
        tvTitle.setText(msg);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setView(inflatedView);
        toast.show();
    }
    boolean chay=false;

    int tinhPixel(int dp)
    {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());

        return (int)pixels;
    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {


        if(preferences.getString("lati","0")!="0" && preferences.getString("long","0")!="0") {
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(Double.valueOf(preferences.getString("lati", "0")), Double.valueOf(preferences.getString("long", "0"))));
            googleMap.moveCamera(center);


            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
            googleMap.animateCamera(zoom);
        }
        //  googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(preferences.getLong("lati",0),preferences.getLong("long",0)) , 14) );
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {


                myLocation=location;



            }
        });


        this.googleMap = googleMap;
        setUpMap();
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {


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

    public void doiduong()
    {
        googleMap.clear();
        dau=true;

        googleMap.addMarker(new MarkerOptions()
                .position(den)
                .title("Tới đây")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        if(route.size()>1)
    {
        for(int i=0;i<route.size();i++)
        {
            if(i!=duonght) {
                PolylineOptions polylineOptions = new PolylineOptions().
                        geodesic(true).
                        color(Color.GRAY).
                        width(9);
                for (int j = 0; j < route.get(i).points.size(); j++)
                    polylineOptions.add(route.get(i).points.get(j));

                polylinePaths.add(googleMap.addPolyline(polylineOptions));
            }
        }
        PolylineOptions polylineOptions = new PolylineOptions().
                geodesic(true).
                color(Color.BLUE).
                width(9);
        for (int j = 0; j < route.get(duonght).points.size(); j++)
            polylineOptions.add(route.get(duonght).points.get(j));

        polylinePaths.add(googleMap.addPolyline(polylineOptions));
        //Toast.makeText(getBaseContext(),route.get(0).distance.text+" - "+route.get(0).duration.text,Toast.LENGTH_SHORT).show();
        txtTime.setText(route.get(duonght).duration.text);
        txtKhoangCach.setText("Khoảng cách: "+route.get(duonght).distance.text);

        float khoangcach=(float)route.get(duonght).distance.value/1000;
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

    @Override
    public void onDirectionFinderSuccess(List<Route> route) {


        duongtimthay=route.size();
        this.route=route;
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();


        if(route.size()>1)
        {
            for(int i=0;i<route.size();i++)
            {
                if(i!=duonght) {
                    PolylineOptions polylineOptions = new PolylineOptions().
                            geodesic(true).
                            color(Color.GRAY).
                            width(9);
                    for (int j = 0; j < route.get(i).points.size(); j++)
                        polylineOptions.add(route.get(i).points.get(j));

                    polylinePaths.add(googleMap.addPolyline(polylineOptions));
                }
            }
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(9);
            for (int j = 0; j < route.get(duonght).points.size(); j++)
                polylineOptions.add(route.get(duonght).points.get(j));

            polylinePaths.add(googleMap.addPolyline(polylineOptions));
            //Toast.makeText(getBaseContext(),route.get(0).distance.text+" - "+route.get(0).duration.text,Toast.LENGTH_SHORT).show();
            txtTime.setText(route.get(duonght).duration.text);
            txtKhoangCach.setText("Khoảng cách: "+route.get(duonght).distance.text);

            float khoangcach=(float)route.get(duonght).distance.value/1000;
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
