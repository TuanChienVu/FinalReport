package com.dclover.gpsutilities.thoitiet.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dclover.gpsutilities.BuildConfig;
import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.ketnoi.Location.GPSTracker;
import com.dclover.gpsutilities.thoitiet.ConnectionDetector;
import com.dclover.gpsutilities.thoitiet.datas.Channel;
import com.dclover.gpsutilities.thoitiet.datas.ForeCastData;
import com.dclover.gpsutilities.thoitiet.datas.Item;
import com.dclover.gpsutilities.thoitiet.datas.OverViewDetails;
import com.dclover.gpsutilities.thoitiet.services.SuggestionAdapter;
import com.dclover.gpsutilities.thoitiet.services.WeatherServiceCallback;
import com.dclover.gpsutilities.thoitiet.services.YahooWeatherService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class Fragment1 extends Fragment implements WeatherServiceCallback {
    public static Channel schannel;
    public static YahooWeatherService service;
    private TextView conditionTextView;
    Context context;
    String country;
    private TextView dateTextView;
    private ProgressDialog dialog;
    boolean flag;
    String globalIp;
    private List<ForeCastTemp> infolst;
    private TextView locationTextView;
    private List<OverView> ov_lst;
    RecyclerView recyclerView;
    RecyclerView rv_ov;
    String state;
    private TextView temperatureTextView;
    private Toolbar toolbar;
    private ImageView weatherIconImageView;

    /* renamed from: com.sundroid.myapplication.Fragments.Fragment1.1 */
    class C01991 implements View.OnClickListener {
        final /* synthetic */ AutoCompleteTextView val$acTextView;
        final /* synthetic */ Dialog val$dialog1;

        C01991(AutoCompleteTextView autoCompleteTextView, Dialog dialog) {
            this.val$acTextView = autoCompleteTextView;
            this.val$dialog1 = dialog;
        }

        public void onClick(View v) {
            Toast.makeText(Fragment1.this.getActivity(), this.val$acTextView.getText().toString(), Toast.LENGTH_SHORT).show();
            Fragment1.this.dialog = new ProgressDialog(Fragment1.this.getActivity());
            Fragment1.this.dialog.setMessage("Loading...");
            Fragment1.this.dialog.setCancelable(false);
            Fragment1.this.dialog.show();
            Fragment1.service.refreshWeather(this.val$acTextView.getText().toString());
            this.val$dialog1.dismiss();
        }
    }

    class ForeCastTemp {
        String date;
        String high;
        int imgID;
        String low;

        ForeCastTemp(String date, int imgID, String high, String low) {
            this.date = date;
            this.imgID = imgID;
            this.high = high;
            this.low = low;
        }
    }

    class GetLocation extends AsyncTask<Void, Void, Void> {
        String ipaddrjson;
        String mcountry;
        String mstate;

        GetLocation() {
            this.mstate = BuildConfig.FLAVOR;
            this.mcountry = BuildConfig.FLAVOR;
        }

        protected Void doInBackground(Void... params) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://api.ipinfodb.com/v3/ip-city/?key=b2dbb7d658bb724b08d4ddd0ede018376a1c2cd09dd4f18213f3f3b357990c84&ip=" + Fragment1.this.globalIp + "&format=json").openConnection().getInputStream()));
                StringBuilder result = new StringBuilder();
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    result.append(line);
                }
                this.ipaddrjson = result.toString();
                JSONObject jsonObject = new JSONObject(this.ipaddrjson);
                this.mstate = jsonObject.optString("regionName");
                this.mcountry = jsonObject.optString("countryName");
              //  Log.d(NotificationCompatApi21.CATEGORY_MESSAGE, "state:-" + Fragment1.this.state + "\ncountry:-" + Fragment1.this.country);
            } catch (Exception e) {
               // Log.d(NotificationCompatApi21.CATEGORY_MESSAGE, e.toString());
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            Fragment1.this.state = this.mstate;
            Fragment1.this.country = this.mcountry;
            Fragment1.this.startService();
        }
    }

    class IpLocation extends AsyncTask<Void, Void, Void> {
        String ipaddrjson;

        IpLocation() {
        }

        protected Void doInBackground(Void... params) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://www.trackip.net/ip?json").openConnection().getInputStream()));
                StringBuilder result = new StringBuilder();
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    result.append(line);
                }
                this.ipaddrjson = result.toString();
              //  Log.d(NotificationCompatApi21.CATEGORY_MESSAGE, "Result:-" + result.toString());
            } catch (Exception e) {
             //   Log.d(NotificationCompatApi21.CATEGORY_MESSAGE, e.toString());
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            Fragment1.this.parseJsonData(this.ipaddrjson);
        }
    }

    class OverView {
        String details;
        int imgID;
        String title;

        OverView(String title, String details, int imgID) {
            this.title = title;
            this.details = details;
            this.imgID = imgID;
        }
    }

    class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {
        List<ForeCastTemp> infolst;

        /* renamed from: com.sundroid.myapplication.Fragments.Fragment1.RVAdapter.1 */
        class C02001 implements View.OnClickListener {
            C02001() {
            }

            public void onClick(View view) {
                Toast.makeText(Fragment1.this.getActivity(), ((ForeCastTemp) RVAdapter.this.infolst.get(((PersonViewHolder) view.getTag()).getPosition())).date, Toast.LENGTH_SHORT).show();
            }
        }

        public class PersonViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView date;
            TextView high;
            ImageView imgID;
            TextView low;

            PersonViewHolder(View itemView) {
                super(itemView);
                this.cv = (CardView) itemView.findViewById(R.id.fr_cv);
                this.date = (TextView) itemView.findViewById(R.id.fr_date);
                this.imgID = (ImageView) itemView.findViewById(R.id.fr_iv);
                this.high = (TextView) itemView.findViewById(R.id.fr_temp_high);
                this.low = (TextView) itemView.findViewById(R.id.fr_temp_low);
            }
        }

        RVAdapter(List<ForeCastTemp> infolst) {
            this.infolst = infolst;
        }

        public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PersonViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_row, parent, false));
        }

        public void onBindViewHolder(PersonViewHolder holder, int i) {
            holder.date.setText(((ForeCastTemp) this.infolst.get(i)).date);
            holder.imgID.setImageResource(((ForeCastTemp) this.infolst.get(i)).imgID);
            holder.high.setText(((ForeCastTemp) this.infolst.get(i)).high);
            holder.low.setText(((ForeCastTemp) this.infolst.get(i)).low);
            holder.date.setOnClickListener(new C02001());
            holder.date.setTag(holder);
        }

        public int getItemCount() {
            return this.infolst.size();
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }

    class RVAdapterOverView extends RecyclerView.Adapter<RVAdapterOverView.OverViewHolder> {
        List<OverView> infolst;

        /* renamed from: com.sundroid.myapplication.Fragments.Fragment1.RVAdapterOverView.1 */
        class C02011 implements View.OnClickListener {
            C02011() {
            }

            public void onClick(View view) {
                OverView feedItem = (OverView) RVAdapterOverView.this.infolst.get(((OverViewHolder) view.getTag()).getPosition());
            }
        }

        public class OverViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView details;
            ImageView imgID;
            TextView title;

            OverViewHolder(View itemView) {
                super(itemView);
                this.cv = (CardView) itemView.findViewById(R.id.ov_cv);
                this.title = (TextView) itemView.findViewById(R.id.ov_title);
                this.imgID = (ImageView) itemView.findViewById(R.id.ov_iv);
                this.details = (TextView) itemView.findViewById(R.id.ov_details);
            }
        }

        RVAdapterOverView(List<OverView> infolst) {
            this.infolst = infolst;
        }

        public OverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OverViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.overview_row, parent, false));
        }

        public void onBindViewHolder(OverViewHolder holder, int i) {
            holder.title.setText(((OverView) this.infolst.get(i)).title);
            Log.d("dd","d:"+this.infolst.get(i).imgID);
            holder.imgID.setImageResource(this.infolst.get(i).imgID);
            holder.details.setText(((OverView) this.infolst.get(i)).details);
            View.OnClickListener clickListener = new C02011();
            holder.title.setOnClickListener(clickListener);
            holder.cv.setOnClickListener(clickListener);
            holder.title.setTag(holder);
            holder.cv.setTag(holder);
        }

        public int getItemCount() {
            return this.infolst.size();
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }

    public Fragment1() {
        this.flag = true;
        this.country = BuildConfig.FLAVOR;
        this.state = BuildConfig.FLAVOR;
        this.globalIp = BuildConfig.FLAVOR;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.context = getActivity();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag1, container, false);
        this.weatherIconImageView = (ImageView) view.findViewById(R.id.weathericonimageview);
        this.temperatureTextView = (TextView) view.findViewById(R.id.temperaturetextview);
        this.conditionTextView = (TextView) view.findViewById(R.id.conditiontextview);
        this.locationTextView = (TextView) view.findViewById(R.id.locationtextview);
        this.dateTextView = (TextView) view.findViewById(R.id.datetextview);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.forecast_rv);
        this.rv_ov = (RecyclerView) view.findViewById(R.id.ov_rv);
        SharedPreferences sharedPreferences = getActivity().getPreferences(0);
        String sp_state = sharedPreferences.getString("state", null);
        String sp_country = sharedPreferences.getString("country", null);
        //Log.d(NotificationCompatApi21.CATEGORY_MESSAGE, "State:-" + sp_state + "\ncountry:-" + this.country);
        GPSTracker tracker = new GPSTracker(getActivity());
        if (!tracker.canGetLocation()) {
            tracker.showSettingsAlert();
        } else {
            double latitude = tracker.getLatitude();
            double longitude = tracker.getLongitude();
            Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                List<Address> addresses = geoCoder.getFromLocation(latitude, longitude, 1);

                String add = "";
                if (addresses.size() > 0)
                {
                    for (int i=0; i<addresses.get(0).getMaxAddressLineIndex();i++)
                        add += addresses.get(0).getAddressLine(i) + "\n";
                }

                this.dialog = new ProgressDialog(getActivity());
                this.dialog.setMessage("Loading...");
                this.dialog.show();
                this.state = sp_state;
                this.country = sp_country;
                service = new YahooWeatherService(this);
                service.refreshWeather(addresses.get(0).getLocality() + ", " + addresses.get(0).getCountryName());
                Log.d("dd",latitude+":"+longitude+"_"+addresses.get(0).getLocality()+"_"+addresses.get(0).getCountryName());
            }
            catch (IOException e1) {
                e1.printStackTrace();
                Log.d("dd","error"+e1.toString());
                this.dialog = new ProgressDialog(getActivity());
                this.dialog.setMessage("Loading...");
                this.dialog.show();
                this.state = sp_state;
                this.country = sp_country;
                service = new YahooWeatherService(this);
                service.refreshWeather( "Ho chi minh, Viet nam" );
            }
        }
//        if (sp_state == null || sp_country == null) {
//           // Log.d(NotificationCompatApi21.CATEGORY_MESSAGE, "in else");
//            refreshLayout();
//        } else {
//           // Log.d(NotificationCompatApi21.CATEGORY_MESSAGE, "in null");
//            this.dialog = new ProgressDialog(getActivity());
//            this.dialog.setMessage("Loading...");
//            this.dialog.show();
//            this.state = sp_state;
//            this.country = sp_country;
//            service = new YahooWeatherService(this);
//            service.refreshWeather(this.state + ", " + this.country);
//        }
        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment1, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav /*2131492996*/:
                showDialog();
                break;
            case R.id.refresh /*2131492997*/:
                GPSTracker tracker = new GPSTracker(getActivity());
                if (!tracker.canGetLocation()) {
                    tracker.showSettingsAlert();
                } else {
                    double latitude = tracker.getLatitude();
                    double longitude = tracker.getLongitude();
                    Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        List<Address> addresses = geoCoder.getFromLocation(latitude, longitude, 1);

                        String add = "";
                        if (addresses.size() > 0)
                        {
                            for (int i=0; i<addresses.get(0).getMaxAddressLineIndex();i++)
                                add += addresses.get(0).getAddressLine(i) + "\n";
                        }

                        this.dialog = new ProgressDialog(getActivity());
                        this.dialog.setMessage("Loading...");
                        this.dialog.show();
                        service = new YahooWeatherService(this);
                        service.refreshWeather(addresses.get(0).getLocality() + ", " + addresses.get(0).getCountryName());
                        Log.d("dd",latitude+":"+longitude+"_"+addresses.get(0).getLocality()+"_"+addresses.get(0).getCountryName());
                    }
                    catch (IOException e1) {
                        e1.printStackTrace();
                        Log.d("dd","error"+e1.toString());
                        this.dialog = new ProgressDialog(getActivity());
                        this.dialog.setMessage("Loading...");
                        this.dialog.show();
                        service = new YahooWeatherService(this);
                        service.refreshWeather( "Ho chi minh, Viet nam" );
                    }
                }
                break;
        }
        return true;
    }

    public void showDialog() {
        Dialog dialog1 = new Dialog(this.context);
        dialog1.setContentView(R.layout.dialog);
        dialog1.setTitle("Tìm địa phương");
        dialog1.show();
        AutoCompleteTextView acTextView = (AutoCompleteTextView) dialog1.findViewById(R.id.edtCity);
        acTextView.setAdapter(new SuggestionAdapter(getActivity(), acTextView.getText().toString()));
        ((Button) dialog1.findViewById(R.id.btn)).setOnClickListener(new C01991(acTextView, dialog1));
    }

    private List<OverView> initializeOverView(OverViewDetails overViewDetails) {
        this.ov_lst = new ArrayList();
        this.ov_lst.add(new OverView("Mặt trười mọc", overViewDetails.getSunrise(), R.drawable.ic_sunrise));
        this.ov_lst.add(new OverView("Mặt trời lặn", overViewDetails.getSunset(), R.drawable.ic_sunset));
        this.ov_lst.add(new OverView("Tốc gió", overViewDetails.getWind(), R.drawable.ic_wind));
        this.ov_lst.add(new OverView("Độ ẩm", overViewDetails.getHumidity(), R.drawable.ic_humidity));
        this.ov_lst.add(new OverView("Tầm nhìn", overViewDetails.getVisibility(), R.drawable.ic_visibility));
        this.ov_lst.add(new OverView("Áp suất", overViewDetails.getPressure(), R.drawable.ic_pressure));
        return this.ov_lst;
    }

    private List<ForeCastTemp> initializeData(ArrayList<ForeCastData> foreCastData) {
        this.infolst = new ArrayList();
        Iterator it = foreCastData.iterator();
        while (it.hasNext()) {
            ForeCastData fd = (ForeCastData) it.next();
            String date = fd.getDate();
            String high = fd.getHigh();
            date = date.replace(" 2016", BuildConfig.FLAVOR);
            String low = fd.getLow();
            String cond = fd.getText();
            int resourceId = getResources().getIdentifier("drawable/img_" + fd.getCode(), null, getActivity().getPackageName());
            this.infolst.add(new ForeCastTemp(date, resourceId, tempconversion(high) + "\u00b0", tempconversion(low) + "\u00b0"));
        }
        return this.infolst;
    }


    public String tempconversion(String temp) {
        return (((Integer.parseInt(temp) - 32) * 5) / 9) + BuildConfig.FLAVOR;
    }

    public void refreshLayout() {
        if (Boolean.valueOf(new ConnectionDetector(getActivity().getApplicationContext()).isConnectingToInternet()).booleanValue()) {
            this.dialog = new ProgressDialog(getActivity());
            this.dialog.setMessage("Loading...");
            this.dialog.setCancelable(false);
            this.dialog.show();
            getLocation();
            return;
        }
        Toast.makeText(getActivity(), "Xin kiểm tra lại kết nối internet", Toast.LENGTH_LONG).show();
    }

    public void getLocation() {
        new IpLocation().execute(new Void[0]);
    }

    public void parseJsonData(String data) {
        try {
            String ip = new JSONObject(data).optString("IP");
            this.globalIp = ip;
            new GetLocation().execute(new Void[0]);
          //  Log.d(NotificationCompatApi21.CATEGORY_MESSAGE, ip);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void startService() {
        SharedPreferences.Editor editor = getActivity().getPreferences(0).edit();
        editor.putString("state", this.state);
        editor.putString("country", this.country);
        editor.commit();
        service = new YahooWeatherService(this);
        service.refreshWeather(this.state + ", " + this.country);
    }

    public void serviceSuccess(Channel channel) {
        this.dialog.hide();
        schannel = channel;
        Item item = channel.getItem();
        this.weatherIconImageView.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("drawable/img_" + item.getCondition().getCode(), null, getActivity().getPackageName())));
        try {
            this.temperatureTextView.setText(((int) ((double) ((((item.getCondition().getTemperature() + 6) - 32) * 5) / 9))) + "\u00b0 C");
        } catch (Exception e) {
            Log.d("message", e.toString());
        }
        OverViewDetails ovd = new OverViewDetails();
        ovd.setHumidity(channel.getAtmosphere().getHumidity() + "%");
        ovd.setWind(channel.getWind().getSpeed() + getResources().getString(R.string.kmh) + "\t\t" + channel.getWind().getDirection());
        ovd.setSunrise(channel.getAstronomy().getSunrise());
        ovd.setSunset(channel.getAstronomy().getSunset());
        ovd.setVisibility(channel.getAtmosphere().getVisibility() + " km");
        ovd.setPressure(channel.getAtmosphere().getPressure() + " mbar");
        this.conditionTextView.setText(item.getCondition().getDescription());
        this.locationTextView.setText(service.getLocation());
        this.dateTextView.setText(item.getCondition().getDate());
        setRecyclerView(channel.getItem().getForeCast().getForeCastDatas(), ovd);
    }

    public void setRecyclerView(ArrayList<ForeCastData> foreCastData, OverViewDetails ovd) {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.recyclerView.setLayoutManager(llm);
        initializeData(foreCastData);
        this.recyclerView.setAdapter(new RVAdapter(this.infolst));
        LinearLayoutManager llm1 = new LinearLayoutManager(getActivity());
        llm1.setOrientation(LinearLayoutManager.VERTICAL);
        this.rv_ov.setLayoutManager(llm1);
        initializeOverView(ovd);
        this.rv_ov.setAdapter(new RVAdapterOverView(this.ov_lst));
    }

    public void serviceFailure(Exception exception) {
        this.dialog.hide();
        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}

