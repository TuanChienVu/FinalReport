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
import com.dclover.gpsutilities.taxi.Utils.CommonUtils;
import com.dclover.gpsutilities.taxi.Utils.Constants;
import com.dclover.gpsutilities.taxi.model.LoginRegisterResult;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivityTaxi extends AppCompatActivity {

    @Bind(R.id.edtTaxiRegisterPassSdt)
    EditText edtSdt;
    @Bind(R.id.edtTaxiLoginRegisterPass)
    EditText edtRegisterPass;
    @Bind(R.id.edtTaxiRegisterPassAgain)
    EditText edtRegisterPassAgain;
    @Bind(R.id.btnTaxiRegisterLogin)
    Button btnRegister;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_taxi);
        ButterKnife.bind(this);
        this.setTitle("Đăng Ký");
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!edtRegisterPass.getText().toString().equals(edtRegisterPassAgain.getText().toString()))
                    CommonUtils.showAlertDialog(RegisterActivityTaxi.this,"Đăng Ký","Mật khẩu không khớp!");
                else {
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
                            api.getRegister(edtSdt.getText().toString(), edtRegisterPass.getText().toString(), new Callback<LoginRegisterResult>() {
                                @Override
                                public void success(LoginRegisterResult loginRegisterResult, Response response) {
                                    progressBar.setVisibility(View.GONE);
                                    if (loginRegisterResult.getResult() == 0)
                                        CommonUtils.showAlertDialog(RegisterActivityTaxi.this, "Đăng nhập", loginRegisterResult.getErrorString());
                                    else {
                                        Toast.makeText(RegisterActivityTaxi.this, "Đăng ký thành công, Bạn có thể đăng nhập bây giừo", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
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
        });
    }
}
