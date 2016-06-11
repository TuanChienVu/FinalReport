package com.dclover.gpsutilities.taxi.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.taxi.Utils.APIs;
import com.dclover.gpsutilities.taxi.Utils.Constants;
import com.dclover.gpsutilities.taxi.model.LoginRegisterResult;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ResetPassActivityTaxi extends AppCompatActivity {


    @Bind(R.id.edtTaxiRegisterPassSdt)
    EditText edtTaxiResetPassSdt;
    @Bind(R.id.btnTaxiResetPassOK)
    Button btnOk;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass_taxi);
        ButterKnife.bind(this);
        this.setTitle("Reset password");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncTask<Void,Void,Void>()
                {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected Void doInBackground(Void... params) {
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);

                        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Constants.endpoint).build();
                        APIs api = restAdapter.create(APIs.class);
                        api.getResetPass(edtTaxiResetPassSdt.getText().toString(), new Callback<LoginRegisterResult>() {
                            @Override
                            public void success(LoginRegisterResult loginRegisterResult, Response response) {
                                Toast.makeText(ResetPassActivityTaxi.this,"Mật khẩu mới sẽ được gửi đến, xin đăng nhập lại!",Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                finish();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }.execute();



            }
        });
    }
}
