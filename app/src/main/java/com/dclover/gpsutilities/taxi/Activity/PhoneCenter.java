package com.dclover.gpsutilities.taxi.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.taxi.Utils.APIs;
import com.dclover.gpsutilities.taxi.Utils.Constants;
import com.dclover.gpsutilities.taxi.Utils.PhoneCenterAdapter;
import com.dclover.gpsutilities.taxi.model.PhoneCenterModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PhoneCenter extends AppCompatActivity {

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.progressBar2)
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_center);
        ButterKnife.bind(this);
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Constants.endpoint).build();
                APIs api = restAdapter.create(APIs.class);
                api.GetCenterCall(new Callback<List<PhoneCenterModel>>() {
                    @Override
                    public void success(List<PhoneCenterModel> phoneCenterModels, Response response) {
                        PhoneCenterAdapter adapter=new PhoneCenterAdapter(PhoneCenter.this,1,phoneCenterModels);
                        listView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }.execute();

    }
}
