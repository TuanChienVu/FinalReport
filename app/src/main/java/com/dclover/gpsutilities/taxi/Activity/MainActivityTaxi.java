package com.dclover.gpsutilities.taxi.Activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dclover.gpsutilities.MainActivity;
import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.taxi.Utils.APIs;
import com.dclover.gpsutilities.taxi.Utils.ClientCommand;
import com.dclover.gpsutilities.taxi.Utils.CommonUtils;
import com.dclover.gpsutilities.taxi.Utils.Constants;
import com.dclover.gpsutilities.taxi.Utils.DrawerListAdapter;
import com.dclover.gpsutilities.taxi.Utils.Env;
import com.dclover.gpsutilities.taxi.Utils.ListDriverDialogFragment;
import com.dclover.gpsutilities.taxi.Utils.SocketUtils;
import com.dclover.gpsutilities.taxi.model.Driver;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivityTaxi extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap googleMap;
    boolean landau=true;
    BroadcastReceiver mBroadcastReceiver;
    private View currentCarType;
    private Map<String, Marker> mapMarker;
    private Map<String, Driver> mapDriver;
    private Bitmap bmCar4;
    private Bitmap bmCar7;
    private ActionBarDrawerToggle mDrawerToggle;
    LatLng vitri;
    @Bind(R.id.drawer)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.btnMainTaxiCallTaxis)
    Button btnDanhsachtaxi;
    @Bind(R.id.left_drawer)
    ListView lisviewdraw;
    @Bind(R.id.ll_taxiinfor)
    LinearLayout ll_taxiInfor;
    @Bind(R.id.imgTaxiImageTaxi)
    ImageView imgTaxiLaixe;
    @Bind(R.id.imgTaxiImageKindTaxi)
    ImageView imgTaxiLoai;
    @Bind(R.id.frmTaxiCallTaxi)
    FrameLayout btnCallLaixe;
    @Bind(R.id.txtTaxiNameTaxi)
    TextView txtNameTaxi;
    @Bind(R.id.txtTaxiBienSo)
    TextView txtBienSo;
    APIs api;


    @Override
    protected void onStart() {
        super.onStart();
        mBroadcastReceiver=new MyBroadcastReceiver();
        IntentFilter broadcastFilter = new IntentFilter();
        broadcastFilter.addAction(Constants.CMD_SERVER_LOGIN_SUCCESS);
        broadcastFilter.addAction(Constants.CMD_SERVER_UPDATE_DRIVER_INFO);
        broadcastFilter.addAction(Constants.CMD_SERVER_REQUEST_TRIP_RETURN);
        broadcastFilter.addAction(Constants.CMD_SERVER_REQUEST_TRIP_FAIL);
        broadcastFilter.addAction(Constants.CMD_SERVER_UPDATE_TRIP_SUMMARY);
        broadcastFilter.addAction(Constants.CMD_SERVER_UPDATE_TRIP);
        broadcastFilter.addAction(Constants.CMD_SERVER_CANCEL_RETURN);
        broadcastFilter.addAction(Constants.CMD_SERVER_NOTIFY_ARRIVED);
        LocalBroadcastManager.getInstance(Env.getApplication()).registerReceiver(this.mBroadcastReceiver, broadcastFilter);
        SocketUtils.startServerMessageThread();

    }

    @Override
    protected void onStop() {
        super.onStop();
        SocketUtils.stopServerMessageThread();
        LocalBroadcastManager.getInstance(Env.getApplication()).unregisterReceiver(this.mBroadcastReceiver);

    }

    @Override
    protected void onPause() {
        super.onPause();

        Env.savePosition(MainActivityTaxi.this,vitri);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_taxi);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Constants.endpoint).build();
        api = restAdapter.create(APIs.class);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
               R.string.open, R.string.close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        btnDanhsachtaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Driver[] drivers = new Driver[mapDriver.size()];
                mapDriver.values().toArray(drivers);
                ListDriverDialogFragment newFragment = new ListDriverDialogFragment();
                newFragment.setDrivers(drivers);
                newFragment.show(getFragmentManager(), "Drivers");

                Log.d("dd","câ");
            }
        });

        DrawerListAdapter adapter=new DrawerListAdapter(mDrawerLayout,MainActivityTaxi.this,1,new String[]{"LÀM MỚI","Tài khoản","Gọi tổng đài","Lịch sử","Trợ giúp","Thông tin","Phản hồi","Đăng Xuất"});
        lisviewdraw.setAdapter(adapter);

        /////
        this.mapMarker = new HashMap();
        this.mapDriver = new HashMap();
        this.bmCar4 = CommonUtils.resizeImage(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.taxi4_top_50), CommonUtils.dpToPx(BitmapDescriptorFactory.HUE_ORANGE, getResources()), CommonUtils.dpToPx(BitmapDescriptorFactory.HUE_ORANGE, getResources()));
        this.bmCar7 = CommonUtils.resizeImage(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.taxi7_top_50), CommonUtils.dpToPx(BitmapDescriptorFactory.HUE_ORANGE, getResources()), CommonUtils.dpToPx(BitmapDescriptorFactory.HUE_ORANGE, getResources()));

        if (Env.getApplication() == null) {
            Env.setApplication(getApplication());
        }




        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

        @Override
        public void onMapReady(final GoogleMap googleMap) {
            this.googleMap = googleMap;

            final LatLng lastPositon=Env.getPositon(MainActivityTaxi.this);
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(lastPositon.latitude,lastPositon.longitude));
            googleMap.moveCamera(center);
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
            googleMap.animateCamera(zoom);
            googleMap.setOnMarkerClickListener(
                    new GoogleMap.OnMarkerClickListener() {

                        public boolean onMarkerClick(Marker marker) {
                            marker.showInfoWindow();
                            showCallDriver(marker);
                            return true;
                        }
                    });
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    ll_taxiInfor.setVisibility(View.GONE);
                }
            });

            if (ActivityCompat.checkSelfPermission(MainActivityTaxi.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivityTaxi.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {


                    if(landau&& lastPositon.longitude==0 && lastPositon.latitude==0) {
                        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                        googleMap.moveCamera(center);


                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
                        googleMap.animateCamera(zoom);
                        landau=false;
                    }

                }
            });

            googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    LatLng center=cameraPosition.target;
                    vitri=cameraPosition.target;
                    ClientCommand.updateRiderLocation(center.longitude,center.latitude);
                    Log.d("dd","Thay doi vi tri");

                }
            });
        }

    public void updateFromServer(Intent intent) {

        if (Constants.CMD_SERVER_UPDATE_DRIVER_INFO.equals(intent.getAction())) {
            updateCarMarker(intent.getStringExtra(Constants.CMD_SERVER_UPDATE_DRIVER_INFO));
            Log.d("ddd", "kq:" + intent.getStringExtra(Constants.CMD_SERVER_UPDATE_DRIVER_INFO));
        }
    }

    public void updateCarMarker(String jsonContent)
    {

        try {
            String licensePlate;
            Object[] objArr = CommonUtils.convertJson2MapDriver(jsonContent);
            List<Driver> lstDriver = (List<Driver>) objArr[0];
            int estimateTime = ((Double) objArr[1]).intValue();
            if (estimateTime > 0) {
               // this.circleAnim.setCenterText(estimateTime + "'");
            } else {
                //this.circleAnim.setCenterText("?");
            }
            List<String> lstActualDraw = new ArrayList();
            for (Driver driver : lstDriver) {
                String driverName = driver.getDriverName();
                String phoneNumber = driver.getPhoneNumber();
                licensePlate = driver.getLicensePlate() == null ? XmlPullParser.NO_NAMESPACE : driver.getLicensePlate();
                int carType = driver.getCarType();
//                if (this.currentCarType != this.imvTaxiAny) {
//                    if (this.currentCarType == this.imvTaxi4Seat) {
//                        if (carType != 1) {
//                        }
//                    } else if (this.currentCarType == this.imvTaxi7Seat) {
//                        if (carType != 2) {
//                        }
//                    } else if (this.currentCarType == this.imvTaxiAirport && carType != 3) {
//                    }
//                }
                lstActualDraw.add(licensePlate);
                double longitude = driver.getLng();
                double latitude = driver.getLat();
                double angle = driver.getAngle();
                if (this.mapMarker.containsKey(licensePlate)) {
                    animateCarMarker((Marker) this.mapMarker.get(licensePlate), latitude, longitude, (float) angle);
                } else {
                    MarkerOptions mo = new MarkerOptions().position(new LatLng(latitude, longitude)).anchor(0.5f, 0.5f);
                    mo.rotation((float) angle);
                    mo.title(driverName);
                    mo.snippet(phoneNumber);


                    if (carType == 2) {
                        mo.icon(BitmapDescriptorFactory.fromBitmap(this.bmCar7));
                    } else {
                        mo.icon(BitmapDescriptorFactory.fromBitmap(this.bmCar4));
                    }

                    Marker marker = this.googleMap.addMarker(mo);
                    this.mapDriver.put(licensePlate, driver);
                    this.mapMarker.put(licensePlate, marker);
                }
            }
            List<String> lstRemove = new ArrayList();
            for (String licensePlate2 : this.mapMarker.keySet()) {
                boolean isOld = true;
                for (String actual : lstActualDraw) {
                    if (licensePlate2.equals(actual)) {
                        isOld = false;
                        break;
                    }
                }
                if (isOld) {
                    lstRemove.add(licensePlate2);
                }
            }
            for (String keyRemove : lstRemove) {
                this.mapDriver.remove(keyRemove);
                ((Marker) this.mapMarker.get(keyRemove)).remove();
                this.mapMarker.remove(keyRemove);
            }
        } catch (Exception e) {
        }

    }


    private void showCallDriver(Marker marker) {

        String licensePlate = null;
        for (String licensePlate2 : this.mapMarker.keySet()) {
            Marker marker1 = (Marker) this.mapMarker.get(licensePlate2);
            if (marker1.getTitle().equals(marker.getTitle()) && marker1.getSnippet().equals(marker.getSnippet())) {
                licensePlate=licensePlate2;
                break;
            }
        }
        if (licensePlate != null) {
            final Driver driver = (Driver) this.mapDriver.get(licensePlate);
            if (driver != null) {
                ll_taxiInfor.setVisibility(View.VISIBLE);
                txtNameTaxi.setText(driver.getDriverName());
                txtBienSo.setText(driver.getLicensePlate());
                imgTaxiLaixe.setImageResource(R.drawable.male_cus_64);
                btnCallLaixe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (driver.getPhoneNumber() != null && driver.getPhoneNumber().trim().length() > 0) {
                            startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + driver.getPhoneNumber().trim())));

                        }

                    }
                });
                api.getDriverInfor(driver.getDriverId(), new Callback<Driver>() {
                    @Override
                    public void success(Driver driver1, Response response) {
                        setDriverAvatar(driver1.getAvatar());
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

                Log.d("ten:","t:"+driver.getAvatar());
            }
        }
    }
    private void setDriverAvatar(String avatar) {
        if (CommonUtils.isEmpty(avatar)) {
            imgTaxiLaixe.setImageResource(R.drawable.male_cus_64);
            return;
        }
        Bitmap bitmap = CommonUtils.convertString2Bitmap(avatar);
        if (bitmap != null) {
            imgTaxiLaixe.setImageBitmap(bitmap);
        } else {
            imgTaxiLaixe.setImageResource(R.drawable.male_cus_64);
        }
    }


    private void animateCarMarker(Marker marker, double latitude, double longitude, float angle) {
        LatLng startLatLng = marker.getPosition();
        float startAngle = marker.getRotation();
        if (Math.abs(startLatLng.latitude - latitude) >= 0.001d || Math.abs(startLatLng.longitude - longitude) >= 0.001d || ((double) Math.abs(startAngle - angle)) >= 15.0d) {
            Interpolator interpolator = new LinearInterpolator();
            Handler handler = new Handler();
            handler.post(new AnimationChange(SystemClock.uptimeMillis(), interpolator, longitude, startLatLng, latitude, angle, startAngle, marker, handler));
        }
    }

    class AnimationChange implements Runnable {
        final /* synthetic */ float val$angle;
        final /* synthetic */ Handler val$handler;
        final /* synthetic */ Interpolator val$interpolator;
        final /* synthetic */ double val$latitude;
        final /* synthetic */ double val$longitude;
        final /* synthetic */ Marker val$marker;
        final /* synthetic */ long val$start;
        final /* synthetic */ float val$startAngle;
        final /* synthetic */ LatLng val$startLatLng;

        AnimationChange(long j, Interpolator interpolator, double d, LatLng latLng, double d2, float f, float f2, Marker marker, Handler handler) {
            this.val$start = j;
            this.val$interpolator = interpolator;
            this.val$longitude = d;
            this.val$startLatLng = latLng;
            this.val$latitude = d2;
            this.val$angle = f;
            this.val$startAngle = f2;
            this.val$marker = marker;
            this.val$handler = handler;
        }

        public void run() {
            float t = this.val$interpolator.getInterpolation(((float) (SystemClock.uptimeMillis() - this.val$start)) / 3000.0f);
            double lat = (((double) t) * this.val$latitude) + (((double) (1.0f - t)) * this.val$startLatLng.latitude);
            float agl = (this.val$angle * t) + ((1.0f - t) * this.val$startAngle);
            this.val$marker.setPosition(new LatLng(lat, (((double) t) * this.val$longitude) + (((double) (1.0f - t)) * this.val$startLatLng.longitude)));
            this.val$marker.setRotation(agl);
            if (((double) t) < 1.0d) {
                this.val$handler.postDelayed(this, 100);
            } else {
                this.val$marker.setVisible(true);
            }
        }
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            MainActivityTaxi.this.updateFromServer(intent);
            Log.d("dd","Nhan duoc");
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

}
