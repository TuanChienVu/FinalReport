package com.dclover.gpsutilities.maps;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.khoihanh.moduls.DirectionFinder;
import com.dclover.gpsutilities.khoihanh.moduls.DirectionFinderListener;
import com.dclover.gpsutilities.khoihanh.moduls.Route;
import com.dclover.gpsutilities.maps.adapter.Adapter_ListView_Navagation;
import com.dclover.gpsutilities.maps.adapter.AutocompleteAdapter;
import com.dclover.gpsutilities.maps.adapter.ItemDiaDiemAdapter;
import com.dclover.gpsutilities.maps.mapmoduls.GetDirection;
import com.dclover.gpsutilities.maps.mapmoduls.PicassoMarker;
import com.dclover.gpsutilities.maps.mapmoduls.PlaceJSONParser;
import com.dclover.gpsutilities.maps.mapmoduls.autocomplete.ResualAutocomplete;
import com.dclover.gpsutilities.maps.mapmoduls.item;
import com.dclover.gpsutilities.maps.mapmoduls.place.ResualPlace;
import com.dclover.gpsutilities.taxi.Utils.Env;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MapActivity extends AppCompatActivity implements ItemDiaDiemAdapter.OnItemDiaDiemClick, OnMapReadyCallback, GetDirection.OnCompleteGetDirection, DirectionFinderListener {
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    Location myLocation;
    EditText searchLocationB, searchLocationA;
    FloatingActionsMenu fabMenu;
    InputMethodManager imm;
    boolean landau = true;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    LocationManager locationManager;
    LatLng DiemA, DiemB;
    boolean thucthi = false;
    TextView txtTime, txtMoney, txtKhoangCach;
    CardView display;
    LatLng vitri;
    RecyclerView rv;
    FloatingActionButton btnGps;
    boolean voice=true,chiduong=false;


    AutoCompleteTextView editTimkiem;
    ImageView imgDiadiemVoice,imgOpenNavigation;
    private PlacePicker.IntentBuilder builder;
    private PlacesAutoCompleteAdapter mPlacesAdapter;
    //    private AutoCompleteTextView Location;
    private static final int PLACE_PICKER_FLAG = 1;

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));
    protected GoogleApiClient mGoogleApiClient;

    ////////////near me

    Spinner mSprPlaceType;
    URL url = null;
    Bitmap bmp = null;
    PicassoMarker marker;

    String[] mPlaceType = null;
    String[] mPlaceTypeName = null;

    double mLatitude = 0;
    double mLongitude = 0;

    CardView cvTimkiem;
    List<com.dclover.gpsutilities.maps.mapmoduls.autocomplete.Prediction> data=new ArrayList<>();


    ///navi
    List<item> dulieuNavi =new ArrayList<item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .build();
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        builder = new PlacePicker.IntentBuilder();
//        Location = (AutoCompleteTextView) findViewById(R.id.myLocation);
//        mPlacesAdapter = new PlacesAutoCompleteAdapter(this, android.R.layout.simple_list_item_1,
//                mGoogleApiClient, BOUNDS_GREATER_SYDNEY, null);
//        Location.setOnItemClickListener(mAutocompleteClickListener);
//        Location.setAdapter(mPlacesAdapter);

        display = (CardView) findViewById(R.id.cvDisplay);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtMoney = (TextView) findViewById(R.id.txtMoney);
        txtKhoangCach = (TextView) findViewById(R.id.txtKhoangCach);
        btnGps = (FloatingActionButton) findViewById(R.id.btn_fab_gps);
        cvTimkiem=(CardView) findViewById(R.id.cv_search);
        rv = (RecyclerView) findViewById(R.id.rvDiadiem);
        editTimkiem = (AutoCompleteTextView) findViewById(R.id.edt_diadiem_timkiem);
        editTimkiem.setDropDownVerticalOffset(8);
        imgOpenNavigation=(ImageView) findViewById(R.id.img_open_navigation);



        ////nav
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.open_string, R.string.close_string);
        drawer.setDrawerListener(toggle);
        imgOpenNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        final ListView listViewDraw=(ListView) findViewById(R.id.lv_map_nav_content);
        addDulieu();

        Adapter_ListView_Navagation adapter=new Adapter_ListView_Navagation(this, 0, dulieuNavi);
        adapter.po=0;
        listViewDraw.setAdapter(adapter);
        listViewDraw.setItemChecked(0, true);
        listViewDraw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {

                if(position==3)
                {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }else if(position==4)
                {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
                else if(position==5)
                {
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }
                drawer.closeDrawers();
        }
    });

        editTimkiem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv= (TextView) view.findViewById(R.id.edt_item_diadiem_autocomple);
                editTimkiem.setText(tv.getText());
                String[] lat_long = new String[2];
                lat_long = convertAddress(tv.getText().toString());

                DiemA = new LatLng(Double.valueOf(lat_long[0]), Double.valueOf(lat_long[1]));
                MarkerOptions option = new MarkerOptions();
                option.position(DiemA);
                option.title(searchLocationA.getText().toString()).snippet("Nơi Xuất Phát");
                option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                option.alpha(0.8f);
                //option.rotation(90);
                Marker maker = mMap.addMarker(option);
                maker.showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DiemA, 15));
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }
            }
        });

        editTimkiem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /////
                    String[] lat_long = new String[2];
                    lat_long = convertAddress(editTimkiem.getText().toString());

                    DiemA = new LatLng(Double.valueOf(lat_long[0]), Double.valueOf(lat_long[1]));
                    MarkerOptions option = new MarkerOptions();
                    option.position(DiemA);
                    option.title(searchLocationA.getText().toString()).snippet("Nơi Xuất Phát");
                    option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    option.alpha(0.8f);
                    //option.rotation(90);
                    Marker maker = mMap.addMarker(option);
                    maker.showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DiemA, 15));
                    try  {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {

                    }
                    editTimkiem.dismissDropDown();
                    return true;
                }
                return false;
            }
        });
        imgDiadiemVoice = (ImageView) findViewById(R.id.img_diadiem_voice);
        imgDiadiemVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(voice==false)
                {
                    editTimkiem.setText("");
                }
                else
                {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN");
                    startActivityForResult(intent, 0);
                }
            }
        });
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://maps.googleapis.com").build();
        final MapAPI api = restAdapter.create(MapAPI.class);
        editTimkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (editTimkiem.getText().toString().equals("")) {
//                    imgDiadiemVoice.setImageResource(R.drawable);
//                }else{
                api.getDiadiemGoiY(s.toString(), "AIzaSyAhHiXNMuQXiofd35C3FDxXHP0Vcp_vsR0", new Callback<ResualAutocomplete>() {
                    @Override
                    public void success(ResualAutocomplete resualAutocomplete, Response response) {
                        AutocompleteAdapter arrayAdapter = new AutocompleteAdapter(MapActivity.this, R.id.edt_item_diadiem_autocomple, resualAutocomplete.getPredictions());
                        editTimkiem.setAdapter(arrayAdapter);
                        editTimkiem.showDropDown();


                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("dd", "error:" + error.getMessage());
                    }
                });


            }
//            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editTimkiem.getText().toString().equals("")) {
                    imgDiadiemVoice.setImageResource(R.drawable.ic_voice);
                    voice=true;
                } else {
                    imgDiadiemVoice.setImageResource(R.drawable.ic_remove);
                    voice=false;
                }
            }
        });
        ItemDiaDiemAdapter adapter1 = new ItemDiaDiemAdapter();
        adapter1.setClick(this);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter1);

        searchLocationA = (EditText) findViewById(R.id.searchLocationA);

        fabMenu = (FloatingActionsMenu) findViewById(R.id.fab_menu);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        searchLocationB = (EditText) findViewById(R.id.searchLocation);
        searchLocationA = (EditText) findViewById(R.id.searchLocationA);

        btnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myLocation != null) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));

                    mMap.moveCamera(center);

                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
                    mMap.animateCamera(zoom);
                    chay = true;
                }
            }
        });
        searchLocationA.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String[] lat_long = new String[2];
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

                if (thucthi == true) {
                    sendRequestAB();
                }

                return false;
            }

        });
    }

    public void addDulieu()
    {
        dulieuNavi.add(new item("Địa điểm của tôi", R.drawable.icon_myposition, false));
        dulieuNavi.add(new item("Tìm đường", R.drawable.ic_timduong, false));
        dulieuNavi.add(new item("", 0, true));
        dulieuNavi.add(new item("Bản đồ thường", R.drawable.ic_mapthuong, false));
        dulieuNavi.add(new item("Vệ tinh", R.drawable.ic_vetinh, false));
        dulieuNavi.add(new item("Địa hình", R.drawable.ic_diahinh, false));
        dulieuNavi.add(new item("", 0, true));
        dulieuNavi.add(new item("Cài đặt", R.drawable.ic_setting, false));



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {

           List<String> matches_text = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
          if(matches_text.size()>0)
              editTimkiem.setText(matches_text.get(0));


        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlacesAutoCompleteAdapter.PlaceAutocomplete item = mPlacesAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e("place", "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
        }
    };

    public void searchway(View view) {
        fabMenu.collapse();
        thucthi = true;
        searchLocationA.setVisibility(View.VISIBLE);
        searchLocationA.requestFocus();
        searchLocationB.setText("");
        imm.showSoftInput(searchLocationB, InputMethodManager.SHOW_IMPLICIT);
    }

    public void nearbyme(View view) {
        fabMenu.collapse();
        Intent intent = new Intent(MapActivity.this, NearbyMeActivity.class);
        startActivity(intent);
        /*try {
            builder = new PlacePicker.IntentBuilder();
            Intent intent = builder.build(MapActivity.this);
            // Start the Intent by requesting a result, identified by a request code.
            startActivityForResult(intent, PLACE_PICKER_FLAG);

        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil
                    .getErrorDialog(e.getConnectionStatusCode(), MapActivity.this, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(MapActivity.this, "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        }*/
    }

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


        if (route.size() > 1) {
            if(chiduong) {
                for (int i = 1; i < route.size(); i++) {
                    PolylineOptions polylineOptions = new PolylineOptions().
                            geodesic(true).
                            color(Color.GRAY).
                            width(9);
                    for (int j = 0; j < route.get(i).points.size(); j++)
                        polylineOptions.add(route.get(i).points.get(j));

                    polylinePaths.add(mMap.addPolyline(polylineOptions));
                }
                PolylineOptions polylineOptions = new PolylineOptions().
                        geodesic(true).
                        color(Color.BLUE).
                        width(9);
                for (int j = 0; j < route.get(0).points.size(); j++)
                    polylineOptions.add(route.get(0).points.get(j));

                polylinePaths.add(mMap.addPolyline(polylineOptions));
            }

            tvThogianMarker.setText("Thời gian: "+route.get(0).duration.text);
            if(tvKhoangcachMarker!=null)
            tvKhoangcachMarker.setText("Khoảng cách: " + route.get(0).distance.text);

            float khoangcach = (float) route.get(0).distance.value / 1000;
            float tieuthu = (float) (khoangcach * 14) / 100;
            Locale locale = new Locale("vi", "VN");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            String kq = fmt.format(tieuthu * 17000).toString().replace(".", ",");


            tvChiphiMarker.setText("Chi phí dự kiến: " + kq+" (17,000 đ/lit)");

        } else {
            if(chiduong) {
                PolylineOptions polylineOptions = new PolylineOptions().
                        geodesic(true).
                        color(Color.BLUE).
                        width(9);


                for (int i = 0; i < route.get(0).points.size(); i++)
                    polylineOptions.add(route.get(0).points.get(i));

                polylinePaths.add(mMap.addPolyline(polylineOptions));
            }
            tvThogianMarker.setText("Thời gian: "+route.get(0).duration.text);
            if(tvKhoangcachMarker!=null)
            tvKhoangcachMarker.setText("Khoảng cách: " + route.get(0).distance.text);
            float khoangcach = (float) route.get(0).distance.value / 1000;
            float tieuthu = (float) (khoangcach * 14) / 100;
            Locale locale = new Locale("vi", "VN");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            String kq = fmt.format(tieuthu * 17000).toString().replace(".", ",");


            tvChiphiMarker.setText("Chi phí dự kiến: " + kq+" (17,000 đ/lit)");

        }

    }

    @Override
    public void onCompleteGetDirection(boolean isOK, Route direction, String... msg) {

        if (isOK ) {
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


    boolean chay = false;

    TextView tvKhoangcachMarker,tvThogianMarker,tvChiphiMarker,tvChiduongMarker;
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        final LatLng lastPositon = Env.getPositonALL(MapActivity.this, "map");
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(lastPositon.latitude, lastPositon.longitude));
        googleMap.moveCamera(center);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        googleMap.animateCamera(zoom);
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                myLocation = location;
                if (landau && lastPositon.longitude == 0 && lastPositon.latitude == 0) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                    googleMap.moveCamera(center);


                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
                    googleMap.animateCamera(zoom);
                    landau = false;
                }

            }
        });

        this.mMap = googleMap;
        setUpMap();
        this.mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                final Dialog dialogNN=new Dialog(MapActivity.this);
                dialogNN.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogNN.setContentView(R.layout.maker_click_dialog);
                dialogNN.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, tinhPixel(300));
                dialogNN.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                final TextView tvName=(TextView) dialogNN.findViewById(R.id.tv_markerclick_name);
                final TextView tvDiachi=(TextView) dialogNN.findViewById(R.id.tv_markerclick_diachi);
                final TextView tvGoidien=(TextView) dialogNN.findViewById(R.id.tv_markerclick_goidien);
                final ImageView img=(ImageView) dialogNN.findViewById(R.id.img_markerclick_hinh);
                tvKhoangcachMarker=(TextView) dialogNN.findViewById(R.id.tv_markerclick_khoangcach);
                tvThogianMarker=(TextView) dialogNN.findViewById(R.id.tv_markerclick_thogian);
                tvChiphiMarker=(TextView) dialogNN.findViewById(R.id.tv_markerclick_chiphi);
                tvChiduongMarker=(TextView) dialogNN.findViewById(R.id.tv_markerclick_chiduong);
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://maps.googleapis.com").build();
                final MapAPI api = restAdapter.create(MapAPI.class);
                api.getPlace(marker.getTitle(), "AIzaSyAhHiXNMuQXiofd35C3FDxXHP0Vcp_vsR0", new Callback<ResualPlace>() {
                    @Override
                    public void success(final ResualPlace resualAutocomplete, Response response) {
                      tvName.setText(resualAutocomplete.getResult().getName());
                        tvDiachi.setText(resualAutocomplete.getResult().getFormattedAddress());
                        Picasso.with(getBaseContext()).load(resualAutocomplete.getResult().getIcon()).into(img);
                        final LatLng lng=new LatLng(resualAutocomplete.getResult().getGeometry().getLocation().getLat(),resualAutocomplete.getResult().getGeometry().getLocation().getLng());
                      chiduong=false;
                        sendRequest(lng);
                        tvGoidien.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:"+resualAutocomplete.getResult().getInternationalPhoneNumber()));
                                startActivity(intent);
                            }
                        });
                        tvChiduongMarker.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                chiduong=true;
                                sendRequest(lng);
                                dialogNN.dismiss();
                            }
                        });

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

                dialogNN.show();

                marker.hideInfoWindow();
                return true;
            }
        });

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                googleMap.clear();
                dau = true;

                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Tới đây")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                //sendRequest(latLng);
            }
        });
        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                vitri = cameraPosition.target;
            }
        });

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
    int tinhPixel(int dp)
    {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());

        return (int)pixels;
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

    @Override
    protected void onPause() {
        super.onPause();

        Env.savePositionALL(MapActivity.this, vitri, "map");


    }

    boolean dau = true;

    @Override
    public void onClickDiadiem(String type) {
        Log.d("dd", type);
        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + vitri.latitude + "," + vitri.longitude);
        sb.append("&radius=2000");
        sb.append("&types=" + type);
        sb.append("&sensor=true");

        sb.append("&key=AIzaSyAhHiXNMuQXiofd35C3FDxXHP0Vcp_vsR0");
        // Creating a new non-ui thread task to download Google place json data
        PlacesTask placesTask = new PlacesTask();
        // Invokes the "doInBackground()" method of the class PlaceTask
        placesTask.execute(sb.toString());
    }

    private class PlacesTask extends AsyncTask<String, Integer, String> {
        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result) {
            ParserTask parserTask = new ParserTask();

            // Start parsing the Google places in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {
            List<HashMap<String, String>> places = null;
            PlaceJSONParser placeJsonParser = new PlaceJSONParser();
            try {
                jObject = new JSONObject(jsonData[0]);
                /** Getting the parsed data as a List construct */
                places = placeJsonParser.parse(jObject);
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {
            // Clears all the existing markers
            mMap.clear();
            for (int i = 0; i < list.size(); i++) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();
                // Getting a place from the places list
                HashMap<String, String> hmPlace = list.get(i);
                // Getting latitude of the place
                double lat = Double.parseDouble(hmPlace.get("lat"));
                // Getting longitude of the place
                double lng = Double.parseDouble(hmPlace.get("lng"));
                // Getting name
                String name = hmPlace.get("place_name");
                String key=hmPlace.get("place_id");
                // Getting vicinity
                String vicinity = hmPlace.get("vicinity");
                String icon = hmPlace.get("icon");
                LatLng latLng = new LatLng(lat, lng);

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = (View) inflater.inflate(R.layout.icon_marker, null);
                ImageView img = (ImageView) view.findViewById(R.id.img_icon_marker);


                try {
                    url = new URL(icon);
                    bmp = Ion.with(MapActivity.this)
                            .load(String.valueOf(url)).asBitmap().get();
                    Drawable b = new BitmapDrawable(getResources(), bmp);
                    img.setImageDrawable(b);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .snippet(vicinity)
                        .title(key)
                        .icon(BitmapDescriptorFactory.fromBitmap(getResizedBitmap(getBitmapFromView(img), 50, 50))));
            }
        }
    }

    public static Bitmap getBitmapFromView(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.draw(canvas);
        return bitmap;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


}

