package com.dclover.gpsutilities.khoihanh;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dclover.gpsutilities.BuildConfig;
import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.khoihanh.adapter.CustomAdapter;
import com.dclover.gpsutilities.khoihanh.fragment.SpeedGraph;
import com.dclover.gpsutilities.khoihanh.items.DataFiles;
import com.dclover.gpsutilities.khoihanh.util.PreferenceConstants;
import com.dclover.gpsutilities.khoihanh.util.UtilPreferences;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.ExceptionReporter;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.jjoe64.graphview.series.DataPoint;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ActivityKhoiHanh extends ActionBarActivity implements OnMapReadyCallback, LocationListener, CustomAdapter.customButtonListener {

    private static final String LOG_TAG = "AdMob Interstitial ads";
    private static final String TAG = "GPS Trip Tracker";
    public static String appLanguageCode;
    public static ActivityKhoiHanh mapObject;
    public int AdsCount;
    private int AppFocusLimit;
    TextView AvgSpeedTextview;
    float CurrentHighestSpeed;
    public DbHandler DBhandler;
    TextView DistanceDisplayText;
    float DistanceTravelled;
    EditText EndPlaceInput;
    private boolean IsMapView;
    boolean IsRecording;
    boolean IsReview;
    int MODE_OF_TRIP;
    TextView MaxSpeedTextview;
    private Location PrevLoc;
    int REQUEST_ACCESS_MY_LOCATION;
    LinearLayout ReviewGridDisplay;
    int SPEED_UNIT;
    TextView SpeedDisplayText;
    private SpeedGraph SpeedGraphObj;
    EditText StartPlaceInput;
    Chronometer TimeDisplayText;

    MenuItem action_pause;
    MenuItem action_record;
    MenuItem action_review;
    CustomAdapter adapter;
    private float avgSpeed;
    ImageView carSelectImage;
    private GoogleApiClient client;
    private Location currentPosition;
    float currentSpeed;
    ImageView cycleSelectImage;
    Dialog dialog_adloading;
    LinearLayout duringRecordLayout;
    LinearLayout graphLayout;
    HashMap<Double, Integer> hashMap;
    TextView highestSpeedTextview;
    boolean isGPSEnabled;
    boolean isNetworkEnabled;
    boolean isPause;
    TextView kmphButton;
    private LocationManager locationManager;
    ACTIVITIES mActivities;
    private InterstitialAd mInterstitialAd;
    private GoogleMap mMap;
    private ArrayList<MapDetails> mMapDetails;
    public Tracker mTracker;
    LinearLayout mapContentLayout;
    FloatingActionButton mapToggleFab;
    private float maxSpeed;
    private String mfileName;
    int minDistance;
    int minTime;
    String modeOfTravel;
    Button mphButton;
    TextView newRecordButton;
    private DataPoint[] points;
    private Polyline polyline;
    private PolylineOptions polylineOptions;
    TextView rAvgSpeedText;
    TextView rDistanceText;
    TextView rEndPlaceText;
    TextView rMaxSpeedText;
    TextView rStartPlaceText;
    TextView rTimeDurationText;
    TextView realTimeDiaplay;
    Dialog saveDialog;
    FloatingActionButton shareFab;
    LinearLayout speedDisplayLayout;
    Marker speedMarker;
    LinearLayout speedUnitContainer;
    long start;
    LinearLayout startRecordLayout;
    long systemTime;
    long travelTime;
    String url_app_ads_count;
    ImageView walkSelectImage;

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.14 */
    class AnonymousClass14 implements DialogInterface.OnClickListener {
        final /* synthetic */ String val$filename;
        final /* synthetic */ int val$position;

        AnonymousClass14(String str, int i) {
            this.val$filename = str;
            this.val$position = i;
        }

        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
            ActivityKhoiHanh.this.DBhandler.deleteFile(String.valueOf(this.val$filename));
            ActivityKhoiHanh.this.adapter.remove(ActivityKhoiHanh.this.adapter.getItem(this.val$position));
            ActivityKhoiHanh.this.adapter.notifyDataSetChanged();
            if (ActivityKhoiHanh.this.fileExistance(this.val$filename + ".txt")) {
                ActivityKhoiHanh.this.getBaseContext().getFileStreamPath(this.val$filename + ".txt").delete();
                Toast.makeText(ActivityKhoiHanh.this, "File Deleted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.19 */
    class AnonymousClass19 extends Thread {
        final /* synthetic */ Handler val$handler;

        AnonymousClass19(Handler handler) {
            this.val$handler = handler;
        }

        public void run() {
            while (true) {
                try {
                    AnonymousClass19.sleep(1000);
                    this.val$handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.1 */
    class C05771 implements View.OnClickListener {
        C05771() {
        }

        public void onClick(View v) {
            ActivityKhoiHanh ActivityKhoiHanh = ActivityKhoiHanh.this;
            ActivityKhoiHanh.AdsCount++;
            if (ActivityKhoiHanh.this.SPEED_UNIT == 2) {
                ActivityKhoiHanh.this.kmphButtonClicked();
            } else {
                ActivityKhoiHanh.this.mphButtonClicked();
            }
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.20 */
    class AnonymousClass20 extends Dialog {
        AnonymousClass20(Context x0, int x1) {
            super(x0, x1);
        }

        public void onBackPressed() {
            ActivityKhoiHanh.this.action_record.setVisible(false);
            ActivityKhoiHanh.this.action_review.setVisible(true);

            ActivityKhoiHanh.this.InitialViewDisplay();
            super.onBackPressed();
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.21 */
    class AnonymousClass21 implements AdapterView.OnItemClickListener {
        final /* synthetic */ Dialog val$dialog;

        AnonymousClass21(Dialog dialog) {
            this.val$dialog = dialog;
        }

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ActivityKhoiHanh.this.IsReview = true;
            ActivityKhoiHanh.this.ReviewModeDisplay(position);
            ActivityKhoiHanh.this.DrawPreviousRoute(String.valueOf(parent.getItemAtPosition(position)));
            ActivityKhoiHanh.this.action_review.setVisible(false);

            ActivityKhoiHanh.this.mTracker.send(new HitBuilders.EventBuilder().setCategory("Trip List Activity").setAction("Trip Item Selected").setLabel("Trip Opened in Review Mode").build());
            this.val$dialog.dismiss();
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.24 */
    class AnonymousClass24 implements View.OnClickListener {
        final /* synthetic */ Dialog val$dialog;

        AnonymousClass24(Dialog dialog) {
            this.val$dialog = dialog;
        }

        public void onClick(View v) {
            ActivityKhoiHanh.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(ActivityKhoiHanh.this.getResources().getString(R.string.moreapp_play_text))));
            this.val$dialog.dismiss();
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.25 */
    class AnonymousClass25 implements View.OnClickListener {
        final /* synthetic */ Dialog val$dialog;

        AnonymousClass25(Dialog dialog) {
            this.val$dialog = dialog;
        }

        public void onClick(View v) {
            String shareLocationURL = ActivityKhoiHanh.this.getResources().getString(R.string.share_play_text) + "\n This app is easy to find the locations and saving the location details ";
            Intent sharingIntent = new Intent("android.intent.action.SEND");
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra("android.intent.extra.TEXT", shareLocationURL);
            sharingIntent.putExtra("android.intent.extra.SUBJECT", ActivityKhoiHanh.this.getResources().getString(R.string.app_name));
            ActivityKhoiHanh.this.startActivity(Intent.createChooser(sharingIntent, "Share Location using "));
            this.val$dialog.dismiss();
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.26 */
    class AnonymousClass26 implements View.OnClickListener {
        final /* synthetic */ Dialog val$dialog;

        AnonymousClass26(Dialog dialog) {
            this.val$dialog = dialog;
        }

        public void onClick(View v) {
            ActivityKhoiHanh.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(ActivityKhoiHanh.this.getResources().getString(R.string.app_play_text))));
            this.val$dialog.dismiss();
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.27 */
    class AnonymousClass27 implements View.OnClickListener {
        final /* synthetic */ Dialog val$dialog;

        AnonymousClass27(Dialog dialog) {
            this.val$dialog = dialog;
        }

        public void onClick(View v) {
            ActivityKhoiHanh.this.finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            this.val$dialog.dismiss();
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.29 */
    static /* synthetic */ class AnonymousClass29 {
        static final /* synthetic */ int[] f4xad5f64cd;

        static {
            f4xad5f64cd = new int[ACTIVITIES.values().length];
            try {
                f4xad5f64cd[ACTIVITIES.MAIN_PAGE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f4xad5f64cd[ACTIVITIES.RECORDING_PAGE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f4xad5f64cd[ACTIVITIES.SAVE_DIALOG.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f4xad5f64cd[ACTIVITIES.EXIT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f4xad5f64cd[ACTIVITIES.LISTVIEW_DIALOG.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.2 */
    class C05782 implements View.OnClickListener {
        C05782() {
        }

        public void onClick(View v) {
            ActivityKhoiHanh.this.RecordButton();
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.3 */
    class C05793 implements View.OnClickListener {
        C05793() {
        }

        public void onClick(View v) {
            ActivityKhoiHanh.this.MODE_OF_TRIP = 1;
            ActivityKhoiHanh.this.minTime = GamesStatusCodes.STATUS_REQUEST_UPDATE_PARTIAL_SUCCESS;
            ActivityKhoiHanh.this.minDistance = 2;
            ActivityKhoiHanh.this.selectMode();
            ActivityKhoiHanh.this.ResetLocationUpdates();
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.4 */
    class C05804 implements View.OnClickListener {
        C05804() {
        }

        public void onClick(View v) {
            ActivityKhoiHanh.this.MODE_OF_TRIP = 2;
            ActivityKhoiHanh.this.minTime = FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS;
            ActivityKhoiHanh.this.minDistance = 5;
            ActivityKhoiHanh.this.selectMode();
            ActivityKhoiHanh.this.ResetLocationUpdates();
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.5 */
    class C05815 implements View.OnClickListener {
        C05815() {
        }

        public void onClick(View v) {
            ActivityKhoiHanh.this.MODE_OF_TRIP = 3;
            ActivityKhoiHanh.this.minTime = 30000;
            ActivityKhoiHanh.this.minDistance = 100;
            ActivityKhoiHanh.this.selectMode();
            ActivityKhoiHanh.this.ResetLocationUpdates();
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.6 */
    class C05826 implements View.OnClickListener {
        C05826() {
        }

        public void onClick(View v) {
            ActivityKhoiHanh ActivityKhoiHanh = ActivityKhoiHanh.this;
            ActivityKhoiHanh.AdsCount++;
            ActivityKhoiHanh.this.IsMapView = !ActivityKhoiHanh.this.IsMapView;
            if (ActivityKhoiHanh.this.IsMapView) {
                ActivityKhoiHanh.this.mTracker.send(new HitBuilders.EventBuilder().setCategory("Recording Activity").setAction("Map Toggle Button Clicked").setLabel("Map View Opened").build());
                ActivityKhoiHanh.this.mapToggleFab.setImageResource(R.drawable.ic_speedometer);
                ActivityKhoiHanh.this.viewMap();
                return;
            }
            ActivityKhoiHanh.this.mTracker.send(new HitBuilders.EventBuilder().setCategory("Recording Activity").setAction("Map Toggle Button Clicked").setLabel("Speedometer View Opened").build());
            ActivityKhoiHanh.this.mapToggleFab.setImageResource(R.drawable.ic_map);
            ActivityKhoiHanh.this.duringRecordViewDisplay();
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.7 */
    class C05837 implements View.OnClickListener {
        C05837() {
        }

        public void onClick(View v) {
            ActivityKhoiHanh.this.shareFab.setVisibility(View.GONE);
            ActivityKhoiHanh.this.captureScreen();
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.8 */
    class C05848 extends Dialog {
        C05848(Context x0, int x1) {
            super(x0, x1);
        }

        public void onBackPressed() {
            ActivityKhoiHanh.this.SaveAlert();
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.9 */
    class C05859 implements View.OnClickListener {
        final /* synthetic */ EditText val$TripNameInput;

        C05859(EditText editText) {
            this.val$TripNameInput = editText;
        }

        public void onClick(View v) {
            ActivityKhoiHanh ActivityKhoiHanh = ActivityKhoiHanh.this;
            ActivityKhoiHanh.AdsCount++;
            if (this.val$TripNameInput.getText().toString().isEmpty() || ActivityKhoiHanh.this.StartPlaceInput.getText().toString().isEmpty() || ActivityKhoiHanh.this.EndPlaceInput.getText().toString().isEmpty()) {
                ActivityKhoiHanh.this.DisplayAlert();
                return;
            }
            String mfileName = this.val$TripNameInput.getText().toString();
            File from = ActivityKhoiHanh.this.getBaseContext().getFileStreamPath("temp.txt");
            if (from.exists()) {
                from.renameTo(new File(ActivityKhoiHanh.this.getApplicationInfo().dataDir + "/files", mfileName + ".txt"));
            }
            DateFormat dateFormatter = new SimpleDateFormat("hh:mm:ss \n dd/MM/yyyy");
            dateFormatter.setLenient(false);
            ActivityKhoiHanh.this.DBhandler.addNewFile(new DataFiles(mfileName, ActivityKhoiHanh.this.StartPlaceInput.getText().toString(), ActivityKhoiHanh.this.EndPlaceInput.getText().toString(), ActivityKhoiHanh.this.maxSpeed, (double) ActivityKhoiHanh.this.avgSpeed, (float) ActivityKhoiHanh.this.travelTime, ActivityKhoiHanh.this.DistanceTravelled, dateFormatter.format(new Date())));
            ActivityKhoiHanh.this.saveDialog.dismiss();
            ActivityKhoiHanh.this.mActivities = ACTIVITIES.MAIN_PAGE;
            ActivityKhoiHanh.this.displayAd();
            ActivityKhoiHanh.this.mTracker.send(new HitBuilders.EventBuilder().setCategory("Save Activity").setAction("Save Button Clicked").setLabel("Saved Successfully").build());
        }
    }

    public enum ACTIVITIES {
        MAIN_PAGE,
        RECORDING_PAGE,
        LISTVIEW_DIALOG,
        SAVE_DIALOG,
        EXIT,
        NONE
    }

    public class GetA_adsCount extends AsyncTask<String, Void, Void> {
        ProgressDialog mProgressDialog;

        protected void onPostExecute(Void result) {
        }

        protected void onPreExecute() {
        }

        protected Void doInBackground(String... params) {
            try {
                try {
                    ActivityKhoiHanh.this.AppFocusLimit = Integer.parseInt(ActivityKhoiHanh.this.convertStreamToString(new BufferedInputStream(new URL(ActivityKhoiHanh.this.url_app_ads_count).openConnection().getInputStream())));
                } catch (NumberFormatException e) {
                    ActivityKhoiHanh.this.AppFocusLimit = 5;
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                ActivityKhoiHanh.this.AppFocusLimit = 5;
            }
            return null;
        }
    }

    public class MapDetails {
        public LatLng mLocation;
        public float mSpeed;
        public float mTime;
    }

    private class assignAddressAsync extends AsyncTask<Void, Void, Void> {
        String Endaddress;
        String Startaddress;
        ProgressDialog pdLoading;

        private assignAddressAsync() {
            this.pdLoading = new ProgressDialog(ActivityKhoiHanh.this);
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pdLoading.setMessage("\t" + ActivityKhoiHanh.this.getResources().getString(R.string.loading) + "...");
            this.pdLoading.show();
        }

        protected Void doInBackground(Void... params) {
            this.Startaddress = ActivityKhoiHanh.this.GetAddress(((MapDetails) ActivityKhoiHanh.this.mMapDetails.get(0)).mLocation);
            this.Endaddress = ActivityKhoiHanh.this.GetAddress(((MapDetails) ActivityKhoiHanh.this.mMapDetails.get(ActivityKhoiHanh.this.mMapDetails.size() - 1)).mLocation);
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!(this.Startaddress == null || this.Startaddress.isEmpty())) {
                ActivityKhoiHanh.this.StartPlaceInput.setText(this.Startaddress);
            }
            if (!(this.Endaddress == null || this.Endaddress.isEmpty())) {
                ActivityKhoiHanh.this.EndPlaceInput.setText(this.Endaddress);
            }
            this.pdLoading.dismiss();
        }
    }

    public class ToastAdListener extends AdListener {
        private Context mContext;

        public ToastAdListener(Context context) {
            this.mContext = context;
        }

        public void onAdLoaded() {
        }

        public void onAdFailedToLoad(int errorCode) {
            String errorReason = BuildConfig.FLAVOR;
            switch (errorCode) {
                case Barcode.Phone.UNKNOWN /*0*/:
                    errorReason = "Internal error";
                    break;
                case CompletionEvent.STATUS_FAILURE /*1*/:
                    errorReason = "Invalid request";
                    break;
                case CompletionEvent.STATUS_CONFLICT /*2*/:
                    errorReason = "Network Error";
                    break;
                case CompletionEvent.STATUS_CANCELED /*3*/:
                    errorReason = "No fill";
                    break;
            }
            Log.d(ActivityKhoiHanh.LOG_TAG, "onAdFailedToLoad : " + errorReason);
        }

        public void onAdOpened() {
        }

        public void onAdClosed() {
        }

        public void onAdLeftApplication() {
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.23 */
    class AnonymousClass23 extends ToastAdListener {
        AnonymousClass23(Context context) {
            super(context);
        }

        public void onAdLoaded() {
            super.onAdLoaded();
        }

        public void onAdFailedToLoad(int errorCode) {
            super.onAdFailedToLoad(errorCode);
            Log.d(ActivityKhoiHanh.LOG_TAG, "onAdFailedToLoad : " + errorCode);
            ActivityKhoiHanh.this.show_ads(1);
        }

        public void onAdOpened() {
            ActivityKhoiHanh.this.LoadActivity();
        }

        public void onAdClosed() {
            if (ActivityKhoiHanh.this.dialog_adloading != null && ActivityKhoiHanh.this.dialog_adloading.isShowing()) {
                ActivityKhoiHanh.this.dialog_adloading.dismiss();
            }
            ActivityKhoiHanh.this.show_ads(1);
        }
    }

    public ActivityKhoiHanh() {
        this.REQUEST_ACCESS_MY_LOCATION = 0;
        this.minTime = GamesStatusCodes.STATUS_REQUEST_UPDATE_PARTIAL_SUCCESS;
        this.minDistance = 2;
        this.SPEED_UNIT = 1;
        this.MODE_OF_TRIP = 1;
        this.currentSpeed = 0.0f;
        this.IsRecording = false;
        this.isPause = false;
        this.IsReview = false;
        this.AppFocusLimit = 5;
        this.url_app_ads_count = "http://virtualmaze.co.in/iphone/adnetwork/adsratio.php?appname=gpstriptracker&platform=android";
        this.mActivities = ACTIVITIES.NONE;
    }

    public static ActivityKhoiHanh getInstance() {
        return mapObject;
    }

    protected void onCreate(Bundle savedInstanceState) {
        String deviceDefaultName = PreferenceConstants.PREF_DEFAULT_DEVICE_LANGUAGE_NAME;
        String deviceDefaultCode = PreferenceConstants.PREF_DEFAULT_DEVICE_LANGUAGE_CODE;
        try {

            Resources resources = getPackageManager().getResourcesForApplication("com.dclover.gpsutilities");
            deviceDefaultCode = resources.getConfiguration().locale.getLanguage();
            deviceDefaultName = resources.getConfiguration().locale.getDisplayLanguage(Locale.ENGLISH);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        getWindow().addFlags(Barcode.ITF);

        UtilPreferences.saveDeviceLanguageCode(this, deviceDefaultCode);
        UtilPreferences.saveDeviceLanguageName(this, deviceDefaultName);
        SetAppLanguage();
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_khoi_hanh);
        mapObject = this;
        getWindow().addFlags(Barcode.ITF);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        this.mTracker = ((GPSTripTrackerGoogleAnalytics) getApplication()).getTracker(GPSTripTrackerGoogleAnalytics.TrackerName.APP_TRACKER);
        this.mTracker.setScreenName("GTT_Map_Activity");
        this.mTracker.send(new HitBuilders.AppViewBuilder().build());
        this.mTracker.enableExceptionReporting(true);
        new GetA_adsCount().execute(new String[0]);
        this.AdsCount = this.AppFocusLimit;
        createAds();
        this.speedDisplayLayout = (LinearLayout) findViewById(R.id.speed_display_module);
        this.startRecordLayout = (LinearLayout) findViewById(R.id.start_record_module);
        this.duringRecordLayout = (LinearLayout) findViewById(R.id.during_record_module);
        this.graphLayout = (LinearLayout) findViewById(R.id.graph_module);
        this.mapContentLayout = (LinearLayout) findViewById(R.id.map_module);
        this.ReviewGridDisplay = (LinearLayout) findViewById(R.id.review_module);
        this.mapToggleFab = (FloatingActionButton) findViewById(R.id.mapToggleFab);
        this.shareFab = (FloatingActionButton) findViewById(R.id.shareFab);
        this.SpeedDisplayText = (TextView) findViewById(R.id.speedTextView);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/digital.ttf");
        this.SpeedDisplayText.setTypeface(custom_font);
        this.kmphButton = (TextView) findViewById(R.id.kmphButton);
        this.kmphButton.setTypeface(custom_font);
        this.speedUnitContainer = (LinearLayout) findViewById(R.id.SpeedButtonLayout);
        this.speedUnitContainer.setOnClickListener(new C05771());
        this.mphButton = (Button) findViewById(R.id.mphButton);
        this.mphButton.setTypeface(custom_font);
        this.newRecordButton = (TextView) findViewById(R.id.NewRecordButton);
        this.newRecordButton.setOnClickListener(new C05782());
        this.highestSpeedTextview = (TextView) findViewById(R.id.highestSpeedTextview);
        this.walkSelectImage = (ImageView) findViewById(R.id.walkSelectImage);
        this.cycleSelectImage = (ImageView) findViewById(R.id.cycleSelectImage);
        this.carSelectImage = (ImageView) findViewById(R.id.carSelectImage);
        this.realTimeDiaplay = (TextView) findViewById(R.id.dateTimeDisplayText);
        ((LinearLayout) findViewById(R.id.walkButton)).setOnClickListener(new C05793());
        ((LinearLayout) findViewById(R.id.cycleButton)).setOnClickListener(new C05804());
        ((LinearLayout) findViewById(R.id.carButton)).setOnClickListener(new C05815());
        this.TimeDisplayText = (Chronometer) findViewById(R.id.TimerDisplay);
        this.DistanceDisplayText = (TextView) findViewById(R.id.distanceview);
        this.AvgSpeedTextview = (TextView) findViewById(R.id.avgSpeedview);
        this.MaxSpeedTextview = (TextView) findViewById(R.id.maxSpeedview);
        this.mapToggleFab.setOnClickListener(new C05826());
        this.shareFab.setOnClickListener(new C05837());
        this.rTimeDurationText = (TextView) findViewById(R.id.durationTextview);
        this.rDistanceText = (TextView) findViewById(R.id.distanceTextview);
        this.rStartPlaceText = (TextView) findViewById(R.id.fromTextview);
        this.rEndPlaceText = (TextView) findViewById(R.id.toTextview);
        this.rMaxSpeedText = (TextView) findViewById(R.id.maxSpeedTextview);
        this.rAvgSpeedText = (TextView) findViewById(R.id.avgSpeedTextview);
        this.SpeedGraphObj = (SpeedGraph) getSupportFragmentManager().findFragmentById(R.id.graph_fragment);
        this.DBhandler = new DbHandler(this, null, null, 2);
        this.client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        InitialViewDisplay();
        kmphButtonClicked();
        this.MODE_OF_TRIP = 1;
        this.minTime = GamesStatusCodes.STATUS_REQUEST_UPDATE_PARTIAL_SUCCESS;
        this.minDistance = 2;
        selectMode();
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionReporter(this.mTracker, Thread.getDefaultUncaughtExceptionHandler(), this));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        this.action_review = menu.getItem(2);
        this.action_record = menu.getItem(1);
        this.action_pause = menu.getItem(0);
        this.action_pause.setVisible(false);
        this.action_record.setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        this.AdsCount++;
        switch (item.getItemId()) {
            case R.id.action_pause /*2131689750*/:
                PauseButtonClicked();
                return true;
            case R.id.action_record /*2131689751*/:
                RecordButton();
                return true;
            case R.id.action_review /*2131689752*/:
                this.mActivities = ACTIVITIES.LISTVIEW_DIALOG;
                displayAd();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        this.mMap.getUiSettings().setMapToolbarEnabled(false);
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, this.REQUEST_ACCESS_MY_LOCATION);
            return;
        }
        GetLocationService();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != this.REQUEST_ACCESS_MY_LOCATION) {
            return;
        }
        if (permissions.length == 1 && grantResults[0] == 0) {
            GetLocationService();
        } else {
            Log.d(TAG, "Location Access Denied");
        }
    }

    public void onLocationChanged(Location location) {
        if (location != null) {
            this.currentPosition = location;
            this.currentSpeed = location.getSpeed() * 3.6f;
            if (this.currentSpeed > this.CurrentHighestSpeed) {
                this.CurrentHighestSpeed = this.currentSpeed;
                if (this.SPEED_UNIT == 1) {
                    this.highestSpeedTextview.setText(String.format("%.1f", new Object[]{Float.valueOf(this.CurrentHighestSpeed)}) + " " + getResources().getString(R.string.KPHbutton));
                } else if (this.SPEED_UNIT == 2) {
                    this.highestSpeedTextview.setText(String.format("%.1f", new Object[]{Float.valueOf(this.CurrentHighestSpeed * 0.621371f)}) + " " + getResources().getString(R.string.KPHbutton));
                }
            }
            if (this.IsRecording && !this.isPause) {
                DrawRoute(location);
            } else if (this.SPEED_UNIT == 1) {
                if (this.currentSpeed < 100.0f) {
                    this.SpeedDisplayText.setText(String.format("%.1f", new Object[]{Float.valueOf(this.currentSpeed)}));
                    return;
                }
                this.SpeedDisplayText.setText(String.format("%.0f", new Object[]{Float.valueOf(this.currentSpeed)}));
            } else if (this.SPEED_UNIT != 2) {
            } else {
                if (this.currentSpeed * 0.621371f < 100.0f) {
                    this.SpeedDisplayText.setText(String.format("%.1f", new Object[]{Float.valueOf(this.currentSpeed * 0.621371f)}));
                    return;
                }
                this.SpeedDisplayText.setText(String.format("%.0f", new Object[]{Float.valueOf(this.currentSpeed * 0.621371f)}));
            }
        }
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    void GetLocationService() {
        if (this.locationManager == null) {
            this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        }
        this.isGPSEnabled = this.locationManager.isProviderEnabled("gps");
        this.isNetworkEnabled = this.locationManager.isProviderEnabled("network");
        if (this.isGPSEnabled || this.isNetworkEnabled) {
            GetLastKnownLocation();
        } else {
            GpsNotFoundAlertDialog();
        }
    }

    void DrawRoute(Location location) {
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            if (this.polyline != null) {
                this.polyline.remove();
            }
            this.polylineOptions.add(latLng);
            this.polyline = this.mMap.addPolyline(this.polylineOptions);
            float speed = location.getSpeed() * 3.6f;
            if (this.SPEED_UNIT == 1) {
                if (speed < 100.0f) {
                    this.SpeedDisplayText.setText(String.format("%.1f", new Object[]{Float.valueOf(speed)}));
                } else {
                    this.SpeedDisplayText.setText(String.format("%.0f", new Object[]{Float.valueOf(speed)}));
                }
            } else if (this.SPEED_UNIT == 2) {
                if (0.621371f * speed < 100.0f) {
                    this.SpeedDisplayText.setText(String.format("%.1f", new Object[]{Float.valueOf(0.621371f * speed)}));
                } else {
                    this.SpeedDisplayText.setText(String.format("%.0f", new Object[]{Float.valueOf(0.621371f * speed)}));
                }
            }
            if (this.PrevLoc != null) {
                this.DistanceTravelled += this.PrevLoc.distanceTo(location) / 1000.0f;
            } else {
                Toast.makeText(this, "Record Started ", Toast.LENGTH_SHORT).show();
                this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(latLng).zoom(15.0f).build()));
                this.maxSpeed = 0.0f;
                this.avgSpeed = 0.0f;
                this.DistanceTravelled = 0.0f;
                this.start = System.currentTimeMillis();
            }
            this.PrevLoc = location;
            this.travelTime = TimeUnit.MILLISECONDS.toSeconds(SystemClock.elapsedRealtime() - this.TimeDisplayText.getBase());
            MapDetails mDetails = new MapDetails();
            mDetails.mLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mDetails.mSpeed = speed;
            mDetails.mTime = (float) this.travelTime;
            this.mMapDetails.add(mDetails);
            if (this.maxSpeed <= speed) {
                this.maxSpeed = speed;
            }
            if (this.travelTime != 0) {
                this.avgSpeed = this.DistanceTravelled / (((float) this.travelTime) / 3600.0f);
            } else {
                this.avgSpeed = 0.0f;
            }
            this.hashMap.put(Double.valueOf((double) this.travelTime), Integer.valueOf(this.mMapDetails.size() - 1));
            this.SpeedGraphObj.AssignGraphData(speed, (float) this.travelTime);
            SpeedUnitDuringDisplay();
            SaveRouteDetails(String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude()) + "," + String.valueOf(speed) + "," + String.valueOf(this.travelTime) + "," + String.valueOf(this.DistanceTravelled) + "\n", this.mfileName);
            return;
        }
        Log.d(TAG, "Location is NULL");
    }

    public void RecordButton() {
        this.AdsCount++;
        this.isPause = false;
        this.action_pause.setIcon(R.drawable.ic_pause);
        if (this.locationManager == null) {
            this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        } else if (!this.isGPSEnabled && !this.isNetworkEnabled) {
            GpsNotFoundAlertDialog();
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
        } else {
            if (this.IsRecording) {
                this.action_record.setIcon(R.drawable.record);
                this.action_record.setVisible(false);
                Toast.makeText(this, "Record Stoped ", Toast.LENGTH_SHORT).show();
                this.IsRecording = false;
                this.TimeDisplayText.setFormat(null);
                this.TimeDisplayText.stop();
                this.action_pause.setVisible(false);
                this.action_review.setVisible(true);

                this.PrevLoc = null;
                if (this.mMapDetails.size() > 5) {
                    this.saveDialog = new C05848(this, R.style.AppThemeActionBar);
                    this.saveDialog.setContentView(R.layout.save_dialog_layout);
                    EditText TripNameInput = (EditText) this.saveDialog.findViewById(R.id.tripInput);
                    this.StartPlaceInput = (EditText) this.saveDialog.findViewById(R.id.startplaceInput);
                    this.EndPlaceInput = (EditText) this.saveDialog.findViewById(R.id.endplaceInput);
                    if (IsNetworkAvailable()) {
                        new assignAddressAsync().execute(new Void[0]);
                    }
                    TripNameInput.setText(getResources().getString(R.string.tripName) + "_" + DateFormat.getDateTimeInstance().format(new Date()));
                    this.saveDialog.show();
                    ((Button) this.saveDialog.findViewById(R.id.saveButton)).setOnClickListener(new C05859(TripNameInput));
                    return;
                }
                Toast.makeText(this, "Dữ liệu không đủ để lưu lại", Toast.LENGTH_LONG).show();
                this.mActivities = ACTIVITIES.MAIN_PAGE;
                displayAd();
                this.mTracker.send(new HitBuilders.EventBuilder().setCategory("Recording Activity").setAction("Record Stop Button Clicked").setLabel("Not Enough Data to Save").build());
                return;
            }
            this.mTracker.send(new HitBuilders.EventBuilder().setCategory("Main Activity").setAction("Live Record Started").setLabel(this.modeOfTravel).build());
            duringRecordViewDisplay();
            this.SpeedGraphObj.ResetGraph();
            this.mMap.clear();
            this.hashMap = new HashMap();
            this.PrevLoc = null;
            this.polylineOptions = new PolylineOptions().width(5.0f).color(SupportMenu.CATEGORY_MASK);
            this.mMapDetails = new ArrayList();
            this.action_record.setIcon(R.drawable.ic_stop);
            this.action_record.setVisible(true);
            this.mfileName = "temp";
            this.IsRecording = true;
            this.systemTime = SystemClock.elapsedRealtime();
            this.TimeDisplayText.setBase(this.systemTime);
            this.TimeDisplayText.start();
            if (fileExistance(this.mfileName + ".txt")) {
                getBaseContext().getFileStreamPath(this.mfileName + ".txt").delete();
            }
            this.action_pause.setVisible(true);
            this.action_review.setVisible(false);

            this.SpeedGraphObj.InitializeRealTimeGraph();
            if (this.mMap.getMyLocation() != null) {
                Location location = new Location(BuildConfig.FLAVOR);
                location.setLatitude(this.mMap.getMyLocation().getLatitude());
                location.setLongitude(this.mMap.getMyLocation().getLongitude());
                DrawRoute(location);
                return;
            }
            Log.d(TAG, "Location is NULL");
        }
    }

    void SaveRouteDetails(String Data, String fileName) {
        try {
            OutputStreamWriter outputStreamWriter;
            if (fileExistance(fileName + ".txt")) {
                outputStreamWriter = new OutputStreamWriter(openFileOutput(fileName + ".txt", AccessibilityNodeInfoCompat.ACTION_PASTE));
                outputStreamWriter.write(Data);
                outputStreamWriter.close();
                return;
            }
            outputStreamWriter = new OutputStreamWriter(openFileOutput(fileName + ".txt", 0));
            outputStreamWriter.write(Data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }
    }

    private void readFromFile(String fileName) {
        try {
            InputStream inputStream = openFileInput(fileName + ".txt");
            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String str = BuildConfig.FLAVOR;
                while (true) {
                    str = bufferedReader.readLine();
                    if (str != null) {
                        String[] words = str.split(",");
                        MapDetails mDetails = new MapDetails();
                        mDetails.mLocation = new LatLng(Double.parseDouble(words[0]), Double.parseDouble(words[1]));
                        mDetails.mSpeed = Float.parseFloat(words[2]);
                        mDetails.mTime = Float.parseFloat(words[3]);
                        this.mMapDetails.add(mDetails);
                    } else {
                        inputStream.close();
                        return;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e2) {
            Log.e(TAG, "Can not read file: " + e2.toString());
        }
    }

    public boolean fileExistance(String fname) {
        return getBaseContext().getFileStreamPath(fname).exists();
    }

    public void onStart() {
        super.onStart();
        this.client.connect();
        AppIndex.AppIndexApi.start(this.client, Action.newAction(Action.TYPE_VIEW, "Maps Page", Uri.parse("http://host/path"), Uri.parse("android-app://com.dclover.gpsutilities/http/host/path")));
    }

    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(this.client, Action.newAction(Action.TYPE_VIEW, "Maps Page", Uri.parse("http://host/path"), Uri.parse("android-app://com.dclover.gpsutilities/http/host/path")));
        this.client.disconnect();
    }

    public void DrawPreviousRoute(String fileName) {
        this.mMapDetails = new ArrayList();
        readFromFile(fileName);
        this.polylineOptions = new PolylineOptions().width(5.0f).color(SupportMenu.CATEGORY_MASK);
        int maxSpeedPos = 0;
        if (this.polyline != null) {
            this.polyline.remove();
        }
        if (this.mMapDetails.size() > 1) {
            this.points = new DataPoint[this.mMapDetails.size()];
            this.hashMap = new HashMap();
            for (int i = 0; i < this.mMapDetails.size(); i++) {
                this.polylineOptions.add(((MapDetails) this.mMapDetails.get(i)).mLocation);
                this.points[i] = new DataPoint((double) ((MapDetails) this.mMapDetails.get(i)).mTime, (double) ((MapDetails) this.mMapDetails.get(i)).mSpeed);
                this.hashMap.put(Double.valueOf((double) ((MapDetails) this.mMapDetails.get(i)).mTime), Integer.valueOf(i));
                if (this.maxSpeed == ((MapDetails) this.mMapDetails.get(i)).mSpeed) {
                    this.maxSpeed = ((MapDetails) this.mMapDetails.get(i)).mSpeed;
                    maxSpeedPos = i;
                }
            }
            this.polyline = this.mMap.addPolyline(this.polylineOptions);
            if (((MapDetails) this.mMapDetails.get(0)).mLocation != null) {
                this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(((MapDetails) this.mMapDetails.get(0)).mLocation).zoom(15.0f).build()));
                this.mMap.addMarker(new MarkerOptions().title(getResources().getString(R.string.startingPlace)).position(((MapDetails) this.mMapDetails.get(0)).mLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).showInfoWindow();
            } else {
                Log.d(TAG, "First point in map is null");
            }
            if (((MapDetails) this.mMapDetails.get(this.mMapDetails.size() - 1)).mLocation != null) {
                this.mMap.addMarker(new MarkerOptions().title(getResources().getString(R.string.endingPlace)).position(((MapDetails) this.mMapDetails.get(this.mMapDetails.size() - 1)).mLocation).icon(BitmapDescriptorFactory.defaultMarker(0.0f))).showInfoWindow();
            }
            if (((MapDetails) this.mMapDetails.get(maxSpeedPos)).mLocation != null) {
                MarkerOptions options = new MarkerOptions().position(((MapDetails) this.mMapDetails.get(maxSpeedPos)).mLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                if (this.SPEED_UNIT == 1) {
                    options.title(getResources().getString(R.string.maxSpeed) + " : " + String.format("%.1f", new Object[]{Float.valueOf(((MapDetails) this.mMapDetails.get(maxSpeedPos)).mSpeed)}) + " " + getResources().getString(R.string.KPHbutton));
                } else if (this.SPEED_UNIT == 2) {
                    options.title(getResources().getString(R.string.maxSpeed) + " : " + String.format("%.1f", new Object[]{Double.valueOf(((double) ((MapDetails) this.mMapDetails.get(maxSpeedPos)).mSpeed) * 0.621371d)}) + " " + getResources().getString(R.string.MPHbutton));
                }
                this.mMap.addMarker(options).showInfoWindow();
            }
            if (this.points == null || this.points.length == 0) {
                Log.d(TAG, "Speed Data not available");
                return;
            } else {
                this.SpeedGraphObj.AssignGraph(this.points);
                return;
            }
        }
        Log.d(TAG, "Data is Empty");
    }

    public void GetLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            Location location = new Location(BuildConfig.FLAVOR);
            this.mMap.setMyLocationEnabled(true);
            if (this.isGPSEnabled) {
                this.locationManager.requestLocationUpdates("gps", (long) this.minTime, (float) this.minDistance, this);
                location = this.locationManager.getLastKnownLocation("gps");
            }
            if (this.isNetworkEnabled && location == null) {
                this.locationManager.removeUpdates(this);
                this.locationManager.requestLocationUpdates("network", (long) this.minTime, (float) this.minDistance, this);
                location = this.locationManager.getLastKnownLocation("network");
            }
            if (location != null) {
                this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(15.0f).build()));
                return;
            }
            Log.e(TAG, "Last known Location is NULL");
        }
    }

    public void GpsNotFoundAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.gps_not_found_title));
        dialog.setMessage(getResources().getString(R.string.gps_not_found_message));
        dialog.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                ActivityKhoiHanh.this.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                ActivityKhoiHanh.this.isNetworkEnabled = true;
                ActivityKhoiHanh.this.isGPSEnabled = true;
                ActivityKhoiHanh.this.GetLastKnownLocation();
            }
        });
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                ActivityKhoiHanh.this.finish();
            }
        });
        dialog.show();
    }

    public void ExitApplication() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.exit_application_title));
        dialog.setMessage(getResources().getString(R.string.exit_application_message));
        dialog.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                ActivityKhoiHanh.this.finish();
            }
        });
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
            }
        });
        dialog.show();
    }

    public void DeleteFile(String filename, int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.confirm_delete_title));
        dialog.setMessage(getResources().getString(R.string.confirm_delete_message));
        dialog.setPositiveButton(getResources().getString(R.string.yes), new AnonymousClass14(filename, position));
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
            }
        });
        dialog.show();
    }

    public void SaveAlert() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.exit_application_title));
        dialog.setMessage(getResources().getString(R.string.save_data_message));
        dialog.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                ActivityKhoiHanh.this.saveDialog.dismiss();
                ActivityKhoiHanh.this.mActivities = ACTIVITIES.MAIN_PAGE;
                ActivityKhoiHanh.this.displayAd();
                ActivityKhoiHanh.this.mTracker.send(new HitBuilders.EventBuilder().setCategory("Save Activity").setAction("Back Button Clicked").setLabel("Data is not saved").build());
            }
        });
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
            }
        });
        dialog.show();
    }

    public void PointClicked(double xAxis) {
        if (this.mMapDetails.size() != 0) {
            this.mTracker.send(new HitBuilders.EventBuilder().setCategory("Graph View").setAction("Point Clicked").setLabel("GPS Location Focused Successfully").build());
            int position = ((Integer) this.hashMap.get(Double.valueOf(xAxis))).intValue();
            if (((MapDetails) this.mMapDetails.get(position)).mLocation != null) {
                this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(((MapDetails) this.mMapDetails.get(position)).mLocation).zoom(17.0f).build()));
                if (this.speedMarker != null) {
                    this.speedMarker.remove();
                }
                MarkerOptions options = new MarkerOptions().position(((MapDetails) this.mMapDetails.get(position)).mLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                if (this.SPEED_UNIT == 1) {
                    options.title(getResources().getString(R.string.Speed) + " : " + String.format("%.1f", new Object[]{Float.valueOf(((MapDetails) this.mMapDetails.get(position)).mSpeed)}) + " " + getResources().getString(R.string.KPHbutton));
                } else if (this.SPEED_UNIT == 2) {
                    options.title(getResources().getString(R.string.Speed) + " : " + String.format("%.1f", new Object[]{Double.valueOf(((double) ((MapDetails) this.mMapDetails.get(position)).mSpeed) * 0.621371d)}) + " " + getResources().getString(R.string.MPHbutton));
                }
                this.speedMarker = this.mMap.addMarker(options);
                this.speedMarker.showInfoWindow();
                return;
            }
            Log.d(TAG, "PointClicked: Clicked Point is Null");
            return;
        }
        this.mTracker.send(new HitBuilders.EventBuilder().setCategory("Graph View").setAction("Point Clicked").setLabel("Point is NULL").build());
        Log.d(TAG, "PointClicked: mMapdetails is Null");
    }

    public String GetAddress(LatLng location) {
        try {
            return ((Address) new Geocoder(this, Locale.getDefault()).getFromLocation(location.latitude, location.longitude, 1).get(0)).getSubLocality();
        } catch (IOException e) {
            Log.d(TAG, "Address is NULL");
            return null;
        }
    }

    private void DisplayAlert() {
        Toast.makeText(this, "Chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
    }

    public void PauseButtonClicked() {
        if (this.isPause) {
            this.isPause = false;
            this.action_pause.setIcon(R.drawable.ic_pause);
            this.TimeDisplayText.setBase(SystemClock.elapsedRealtime() - this.systemTime);
            this.systemTime = SystemClock.elapsedRealtime() - this.systemTime;
            this.TimeDisplayText.start();
            this.start = System.currentTimeMillis() - this.start;
            this.mTracker.send(new HitBuilders.EventBuilder().setCategory("Tool Bar Items").setAction("Record Resumed").setLabel("Resume Button Clicked").build());
            return;
        }
        this.isPause = true;
        this.action_pause.setIcon(R.drawable.ic_play);
        this.systemTime = SystemClock.elapsedRealtime() - this.systemTime;
        this.TimeDisplayText.stop();
        this.start = System.currentTimeMillis() - this.start;
        this.mTracker.send(new HitBuilders.EventBuilder().setCategory("Tool Bar Items").setAction("Record Paused").setLabel("Pause Button Clicked").build());
    }

    void selectMode() {
        this.AdsCount++;
        if (this.MODE_OF_TRIP == 1) {
            this.modeOfTravel = "Walk Mode";
            this.walkSelectImage.setVisibility(View.VISIBLE);
            this.cycleSelectImage.setVisibility(View.INVISIBLE);
            this.carSelectImage.setVisibility(View.INVISIBLE);
        } else if (this.MODE_OF_TRIP == 2) {
            this.modeOfTravel = "Cycle Mode";
            this.walkSelectImage.setVisibility(View.INVISIBLE);
            this.cycleSelectImage.setVisibility(View.VISIBLE);
            this.carSelectImage.setVisibility(View.INVISIBLE);
        } else if (this.MODE_OF_TRIP == 3) {
            this.modeOfTravel = "Car Mode";
            this.walkSelectImage.setVisibility(View.INVISIBLE);
            this.cycleSelectImage.setVisibility(View.INVISIBLE);
            this.carSelectImage.setVisibility(View.VISIBLE);
        }
    }

    void ResetLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, this.REQUEST_ACCESS_MY_LOCATION);
            return;
        }
        this.locationManager.removeUpdates(this);
        GetLocationService();
    }

    void InitialViewDisplay() {
        new AnonymousClass19(new Handler() {
            public void handleMessage(Message msg) {
                DateFormat dateFormatter = new SimpleDateFormat("hh:mm:ss \n dd/MM/yyyy");
                dateFormatter.setLenient(false);
                ActivityKhoiHanh.this.realTimeDiaplay.setText(dateFormatter.format(new Date()));
            }
        }).start();
        if (this.SPEED_UNIT == 1) {
            kmphButtonClicked();
        } else {
            mphButtonClicked();
        }
        this.speedDisplayLayout.setVisibility(View.VISIBLE);
        this.startRecordLayout.setVisibility(View.VISIBLE);
        this.duringRecordLayout.setVisibility(View.GONE);
        this.graphLayout.setVisibility(View.GONE);
        this.mapContentLayout.setVisibility(View.GONE);
        this.ReviewGridDisplay.setVisibility(View.GONE);
        this.mapToggleFab.setVisibility(View.GONE);
        this.shareFab.setVisibility(View.GONE);
    }

    void duringRecordViewDisplay() {
        SpeedUnitDuringDisplay();
        this.speedDisplayLayout.setVisibility(View.VISIBLE);
        this.startRecordLayout.setVisibility(View.GONE);
        this.duringRecordLayout.setVisibility(View.VISIBLE);
        this.graphLayout.setVisibility(View.VISIBLE);
        this.mapContentLayout.setVisibility(View.GONE);
        this.ReviewGridDisplay.setVisibility(View.GONE);
        this.mapToggleFab.setVisibility(View.VISIBLE);
        this.mapToggleFab.setImageResource(R.drawable.ic_map);
        this.shareFab.setVisibility(View.GONE);
        this.IsMapView = false;
    }

    void viewMap() {
        this.speedDisplayLayout.setVisibility(View.GONE);
        this.startRecordLayout.setVisibility(View.GONE);
        this.duringRecordLayout.setVisibility(View.VISIBLE);
        this.graphLayout.setVisibility(View.VISIBLE);
        this.ReviewGridDisplay.setVisibility(View.GONE);
        this.mapContentLayout.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams parems = (LinearLayout.LayoutParams) this.mapContentLayout.getLayoutParams();
        parems.weight = 25.0f;
        this.mapContentLayout.setLayoutParams(parems);
    }

    void ReviewModeDisplay(int position) {
        this.AdsCount++;
        LinearLayout.LayoutParams Mapparems = (LinearLayout.LayoutParams) this.mapContentLayout.getLayoutParams();
        Mapparems.weight = 27.0f;
        this.mapContentLayout.setLayoutParams(Mapparems);
        this.speedDisplayLayout.setVisibility(View.GONE);
        this.startRecordLayout.setVisibility(View.GONE);
        this.duringRecordLayout.setVisibility(View.GONE);
        this.graphLayout.setVisibility(View.VISIBLE);
        this.mapContentLayout.setVisibility(View.VISIBLE);
        this.mapToggleFab.setVisibility(View.GONE);
        this.shareFab.setVisibility(View.VISIBLE);
        this.ReviewGridDisplay.setVisibility(View.VISIBLE);
        this.rStartPlaceText.setText(Html.fromHtml("<font color='black'>" + this.DBhandler.GetValueAtPosition(position, DbHandler.COLUMN_STARTPLACE) + "</font>"));
        this.rEndPlaceText.setText(Html.fromHtml(" <font color='black'>" + this.DBhandler.GetValueAtPosition(position, DbHandler.COLUMN_ENDPLACE) + "</font>"));
        this.maxSpeed = Float.parseFloat(this.DBhandler.GetValueAtPosition(position, DbHandler.COLUMN_MAXSPEED));
        if (this.SPEED_UNIT == 1) {
            this.rMaxSpeedText.setText(Html.fromHtml(" <font color='black'>" + String.format("%.2f", new Object[]{Float.valueOf(this.maxSpeed)}) + " " + getResources().getString(R.string.KPHbutton) + "</font>"));
            this.rAvgSpeedText.setText(Html.fromHtml(" <font color='black'>" + String.format("%.2f", new Object[]{Double.valueOf(Double.parseDouble(this.DBhandler.GetValueAtPosition(position, DbHandler.COLUMN_AVGSPEED)))}) + " " + getResources().getString(R.string.KPHbutton) + "</font>"));
            this.rDistanceText.setText(Html.fromHtml(" <font color='black'>" + String.format("%.2f", new Object[]{Float.valueOf(Float.parseFloat(this.DBhandler.GetValueAtPosition(position, DbHandler.COLUMN_DISTANCE)))}) + " " + getResources().getString(R.string.kmUnit) + "</font>"));
        } else if (this.SPEED_UNIT == 2) {
            this.rMaxSpeedText.setText(Html.fromHtml(" <font color='black'>" + String.format("%.2f", new Object[]{Float.valueOf(this.maxSpeed * 0.621371f)}) + " " + getResources().getString(R.string.MPHbutton) + "</font>"));
            this.rAvgSpeedText.setText(Html.fromHtml(" <font color='black'>" + String.format("%.2f", new Object[]{Double.valueOf(Double.parseDouble(this.DBhandler.GetValueAtPosition(position, DbHandler.COLUMN_AVGSPEED)) * 0.6213709712028503d)}) + " " + getResources().getString(R.string.MPHbutton) + "</font>"));
            this.rDistanceText.setText(Html.fromHtml(" <font color='black'>" + String.format("%.2f", new Object[]{Float.valueOf(Float.parseFloat(this.DBhandler.GetValueAtPosition(position, DbHandler.COLUMN_DISTANCE)) * 0.621371f)}) + " " + getResources().getString(R.string.miUnit) + "</font>"));
        }
        float secs = Float.parseFloat(this.DBhandler.GetValueAtPosition(position, DbHandler.COLUMN_TIMEDURATION));
        this.rTimeDurationText.setText(Html.fromHtml(" <font color='black'>" + TimeUnit.SECONDS.toHours((long) secs) + " Hr " + TimeUnit.SECONDS.toMinutes((long) secs) + " min</font>"));
    }

    void reviewButtonClicked() {
        this.action_record.setVisible(false);
        showListview();
    }

    public void onBackPressed() {
        finish();
//        if (this.IsReview) {
//            this.IsReview = false;
//            showListview();
//        } else if (this.IsRecording) {
//            RecordButton();
//        } else {
//            this.mActivities = ACTIVITIES.EXIT;
//            displayAd();
//        }
    }

    void showListview() {
        this.mMap.clear();
        this.SpeedGraphObj.ResetGraph();
        if (this.DBhandler.getAllFileNames().GetfileNameList().size() != 0) {
            Dialog dialog = new AnonymousClass20(this, R.style.AppThemeActionBar);
            dialog.setContentView(R.layout.activity_list_view);
            dialog.setTitle("Khỏi hành");
            this.adapter = new CustomAdapter(this, this.DBhandler.getAllFileNames().GetfileNameList(), this.DBhandler.getAllFileNames().GetstartPlaceList(), this.DBhandler.getAllFileNames().GetendPlaceList(), this);
            ListView listView = (ListView) dialog.findViewById(R.id.listView);
            listView.setAdapter(this.adapter);
            listView.setOnItemClickListener(new AnonymousClass21(dialog));
            this.mTracker.send(new HitBuilders.EventBuilder().setCategory("Tool Bar Items").setAction("Review Button Clicked").setLabel("Trip List Activity Opened").build());
            dialog.show();
            return;
        }
        this.action_record.setVisible(false);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Khởi hành");
        alert.setMessage(getResources().getString(R.string.triplistview_empty));
        alert.setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
            }
        });
        alert.show();
        Log.d(TAG, "There is No files");
        this.mTracker.send(new HitBuilders.EventBuilder().setCategory("Tool Bar Items").setAction("Review Button Clicked").setLabel("Trip List is Empty").build());
    }

    void kmphButtonClicked() {
        this.SPEED_UNIT = 1;
        this.kmphButton.setText(getResources().getString(R.string.KPHbutton));
        this.mphButton.setText(getResources().getString(R.string.MPHbutton));
        this.CurrentHighestSpeed = this.DBhandler.getHighestSpeed();
        this.highestSpeedTextview.setText(String.format("%.1f", new Object[]{Float.valueOf(this.CurrentHighestSpeed)}) + " " + getResources().getString(R.string.KPHbutton));
        if (this.currentSpeed < 100.0f) {
            this.SpeedDisplayText.setText(String.format("%.1f", new Object[]{Float.valueOf(this.currentSpeed)}));
        } else {
            this.SpeedDisplayText.setText(String.format("%.0f", new Object[]{Float.valueOf(this.currentSpeed)}));
        }
        if (this.IsRecording) {
            SpeedUnitDuringDisplay();
        }
    }

    void mphButtonClicked() {
        this.SPEED_UNIT = 2;
        this.kmphButton.setText(getResources().getString(R.string.MPHbutton));
        this.mphButton.setText(getResources().getString(R.string.KPHbutton));
        this.CurrentHighestSpeed = this.DBhandler.getHighestSpeed();
        this.highestSpeedTextview.setText(String.format("%.1f", new Object[]{Float.valueOf(this.CurrentHighestSpeed * 0.621371f)}) + " " + getResources().getString(R.string.MPHbutton));
        if (this.currentSpeed * 0.621371f < 100.0f) {
            this.SpeedDisplayText.setText(String.format("%.1f", new Object[]{Float.valueOf(this.currentSpeed * 0.621371f)}));
        } else {
            this.SpeedDisplayText.setText(String.format("%.0f", new Object[]{Float.valueOf(this.currentSpeed * 0.621371f)}));
        }
        if (this.IsRecording) {
            SpeedUnitDuringDisplay();
        }
    }

    void SpeedUnitDuringDisplay() {
        if (this.SPEED_UNIT == 1) {
            this.DistanceDisplayText.setText(String.format("%.1f", new Object[]{Float.valueOf(this.DistanceTravelled)}) + " " + getResources().getString(R.string.kmUnit));
            this.AvgSpeedTextview.setText(String.format("%.1f", new Object[]{Float.valueOf(this.avgSpeed)}) + " " + getResources().getString(R.string.KPHbutton));
            this.MaxSpeedTextview.setText(String.format("%.1f", new Object[]{Float.valueOf(this.maxSpeed)}) + " " + getResources().getString(R.string.KPHbutton));
        } else if (this.SPEED_UNIT == 2) {
            this.DistanceDisplayText.setText(String.format("%.1f", new Object[]{Float.valueOf(this.DistanceTravelled * 0.621371f)}) + " " + getResources().getString(R.string.miUnit));
            this.AvgSpeedTextview.setText(String.format("%.1f", new Object[]{Double.valueOf(((double) this.avgSpeed) * 0.621371d)}) + " " + getResources().getString(R.string.MPHbutton));
            this.MaxSpeedTextview.setText(String.format("%.1f", new Object[]{Double.valueOf(((double) this.maxSpeed) * 0.621371d)}) + " " + getResources().getString(R.string.MPHbutton));
        }
    }

    boolean IsNetworkAvailable() {
        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public void onDeleteClickListner(String filename, int position) {
        DeleteFile(filename, position);
    }

    public void createAds() {
        String AdMob_InterstitialID = getResources().getString(R.string.adMob_interstitialid);
        this.mInterstitialAd = new InterstitialAd(this);
        this.mInterstitialAd.setAdUnitId(AdMob_InterstitialID);
        this.mInterstitialAd.setAdListener(new AnonymousClass23(this));
        show_ads(1);
    }

    public void show_ads(int typeofAd) {
        switch (typeofAd) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                if (this.mInterstitialAd != null) {
                    AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
                    if (this.currentPosition != null) {
                        adRequestBuilder.setLocation(this.currentPosition);
                    }
                    adRequestBuilder.addTestDevice("95E3BE1966879962498A9931A1C8BAAD");
                    adRequestBuilder.addTestDevice("D5AE9614D5A1C822B702220064231235");
                    adRequestBuilder.addTestDevice("160107CCAEC607690B9633369BA35E9A");
                    adRequestBuilder.addTestDevice("F6D15CF7813F6DDDA621F56C30C02C7F");
                    adRequestBuilder.addTestDevice("9FF3C8C702FEEDEFAE6C6860DDE1A10D");
                    adRequestBuilder.addTestDevice("DD6112AA95DD16230CA0323ABC7C5CDA");
                    this.mInterstitialAd.loadAd(adRequestBuilder.build());
                }
            default:
        }
    }

    public void displayAd() {
//        if (this.mInterstitialAd == null || !this.mInterstitialAd.isLoaded() || this.AdsCount < this.AppFocusLimit) {
            LoadActivity();
//            return;
//        }
//        dialogAdLoading();
//        this.mInterstitialAd.show();
//        this.AdsCount = 0;
    }

    public void LoadActivity() {
        switch (AnonymousClass29.f4xad5f64cd[this.mActivities.ordinal()]) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                InitialViewDisplay();
            case Barcode.PHONE /*4*/:
              //  dialogAppExitRateUs();
            case Barcode.PRODUCT /*5*/:
                reviewButtonClicked();
            default:
        }
    }

    public void dialogAdLoading() {
        this.dialog_adloading = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        this.dialog_adloading.requestWindowFeature(1);
        this.dialog_adloading.setContentView(R.layout.dialog_adloading);
        this.dialog_adloading.setCancelable(false);
        this.dialog_adloading.show();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private String convertStreamToString(java.io.InputStream r6) {
        java.util.Scanner s = new java.util.Scanner(r6).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
        //throw new UnsupportedOperationException("Method not decompiled: com.virtualmaze.gpstriptracker.ActivityKhoiHanh.convertStreamToString(java.io.InputStream):java.lang.String");
    }

    public void dialogAppExitRateUs() {
//        Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
//        dialog.requestWindowFeature(1);
//        dialog.setContentView(R.layout.dialog_appexit_rateus);
//        TextView shareusTextView = (TextView) dialog.findViewById(R.id.textView_share_us);
//        TextView rateusTextView = (TextView) dialog.findViewById(R.id.textView_rate_us);
//        TextView exitTextView = (TextView) dialog.findViewById(R.id.textView_exit);
//        ((TextView) dialog.findViewById(R.id.textView_more_Apps)).setOnClickListener(new AnonymousClass24(dialog));
//        shareusTextView.setOnClickListener(new AnonymousClass25(dialog));
//        rateusTextView.setOnClickListener(new AnonymousClass26(dialog));
//        exitTextView.setOnClickListener(new AnonymousClass27(dialog));
//        dialog.show();
    }

    public void captureScreen() {
        this.mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
            public void onSnapshotReady(Bitmap snapshot) {
                int[] mapLoc = new int[2];
                ActivityKhoiHanh.this.findViewById(R.id.map).getLocationOnScreen(mapLoc);
                View v = ActivityKhoiHanh.this.getWindow().getDecorView().getRootView();
                v.setDrawingCacheEnabled(true);
                Bitmap backBitmap = v.getDrawingCache();
                Bitmap bmOverlay = Bitmap.createBitmap(backBitmap.getWidth(), backBitmap.getHeight(), backBitmap.getConfig());
                Canvas canvas = new Canvas(bmOverlay);
                canvas.drawBitmap(backBitmap, 0.0f, 0.0f, null);
                canvas.drawBitmap(snapshot, (float) mapLoc[0], (float) mapLoc[1], null);
                String filePath = System.currentTimeMillis() + ".jpeg";
                try {
                    OutputStream fout = ActivityKhoiHanh.this.openFileOutput(filePath, 1);
                    bmOverlay.compress(Bitmap.CompressFormat.JPEG, 90, fout);
                    fout.flush();
                    fout.close();
                } catch (FileNotFoundException e) {
                    Log.d("ImageCapture", "FileNotFoundException");
                    Log.d("ImageCapture", e.getMessage());
                    Toast.makeText(ActivityKhoiHanh.this, "File not found!", Toast.LENGTH_SHORT).show();
                    filePath = BuildConfig.FLAVOR;
                } catch (IOException e2) {
                    Log.d("ImageCapture", "IOException");
                    Log.d("ImageCapture", e2.getMessage());
                    Toast.makeText(ActivityKhoiHanh.this, "IO Exception!", Toast.LENGTH_SHORT).show();
                    filePath = BuildConfig.FLAVOR;
                }
                ActivityKhoiHanh.this.openShareImageDialog(filePath);
            }
        });
    }

    public void openShareImageDialog(String filePath) {
        File file = getFileStreamPath(filePath);
        if (filePath.equals(BuildConfig.FLAVOR)) {
            Toast.makeText(this, "IMAGE Sharing Failed", Toast.LENGTH_SHORT).show();
        } else {
            ContentValues values = new ContentValues(2);
            values.put("mime_type", "image/jpeg");
            values.put("_data", file.getAbsolutePath());
            Uri contentUriFile = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("image/jpeg");
            intent.putExtra("android.intent.extra.STREAM", contentUriFile);
            startActivity(Intent.createChooser(intent, "Share Image"));
        }
        this.shareFab.setVisibility(View.VISIBLE);
    }

    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        try {
            Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth() > bmp2.getWidth() ? bmp1.getWidth() : bmp2.getWidth(), bmp1.getHeight() > bmp2.getHeight() ? bmp1.getHeight() : bmp2.getHeight(), bmp1.getConfig());
            Canvas canvas = new Canvas(bmOverlay);
            canvas.drawBitmap(bmp1, 0.0f, 0.0f, null);
            canvas.drawBitmap(bmp2, 0.0f, 0.0f, null);
            return bmOverlay;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void SetAppLanguage() {
        appLanguageCode = UtilPreferences.getSelectedLanguage(this);
        if (appLanguageCode == null) {
            appLanguageCode = UtilPreferences.getDeviceLanguageCode(this);
            UtilPreferences.saveSelectedLanguage(this, appLanguageCode);
        }
        Locale locale = new Locale(appLanguageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    public void checkfirst() {
        if (UtilPreferences.getFirstLaunch(this)) {
            CustomDialogManager.customdialogchooselanguage(this, true);
            UtilPreferences.saveFirstLaunch(this, false);
        }
    }

    public void changeSelectedLanguage() {
        Dialog dialog = new Dialog(this, R.style.AppTheme);
        dialog.requestWindowFeature(1);
        this.mTracker.send(new HitBuilders.EventBuilder().setCategory("Language Button").setAction("Click").setLabel("Language changed to : " + appLanguageCode).build());
        RelativeLayout relativeLayout_TransitionMain = new RelativeLayout(this);
        relativeLayout_TransitionMain.setBackgroundResource(R.drawable.shape_insdialog);
        relativeLayout_TransitionMain.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        TextView TextView_Loading = new TextView(this);
        TextView_Loading.setText(getResources().getString(R.string.text_TransitionMsg));
        TextView_Loading.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        TextView_Loading.setTypeface(Typeface.SERIF, 1);
        TextView_Loading.setGravity(17);
        RelativeLayout.LayoutParams mRLParams = new RelativeLayout.LayoutParams(-2, -2);
        mRLParams.addRule(13);
        relativeLayout_TransitionMain.addView(TextView_Loading, mRLParams);
        ProgressBar ProgressBar_Loading = new ProgressBar(this, null, 16842874);
        mRLParams = new RelativeLayout.LayoutParams(-2, -2);
        mRLParams.addRule(14);
        mRLParams.addRule(2, TextView_Loading.getId());
        relativeLayout_TransitionMain.addView(ProgressBar_Loading, mRLParams);
        dialog.setContentView(relativeLayout_TransitionMain);
        dialog.show();
        appLanguageCode = UtilPreferences.getSelectedLanguage(this);
        Locale locale = new Locale(appLanguageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        startActivity(new Intent(this, TransitionActivity.class));
        finish();
    }


}
