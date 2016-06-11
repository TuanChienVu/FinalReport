package com.dclover.gpsutilities.taxi.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.taxi.Utils.APIs;
import com.dclover.gpsutilities.taxi.Utils.CommonUtils;
import com.dclover.gpsutilities.taxi.Utils.Constants;
import com.dclover.gpsutilities.taxi.Utils.Env;
import com.dclover.gpsutilities.taxi.model.LoginRegisterResult;
import com.dclover.gpsutilities.taxi.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.edtTaxiRegisterPassSdt)
    EditText edtSdt;
    @Bind(R.id.edtTaxiLoginPass)
    EditText edtPass;
    @Bind(R.id.btnTaxiLoginLogin)
    Button btnLogin;
    @Bind(R.id.btnTaxiLoginForgetPass)
    Button btnForgotPass;
    @Bind(R.id.btnTaxiRegister)
    Button btnRegister;
    APIs api;
    private User objUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_login);
        ButterKnife.bind(this);
        this.objUser = Env.readUser(this);


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Constants.endpoint).build();
        api = restAdapter.create(APIs.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginTask loginTask=new LoginTask();
                loginTask.execute();



            }
        });
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ResetPassActivityTaxi.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivityTaxi.class);
                startActivity(intent);
            }
        });

    }

    private class LoginTask extends AsyncTask<Void,Void,Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }



        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            api.getLogin(edtSdt.getText().toString(),edtPass.getText().toString(),new Callback<LoginRegisterResult>() {
                @Override
                public void success(LoginRegisterResult loginRegisterResult, Response response) {

                    progressBar.setVisibility(View.GONE);
                    if(loginRegisterResult.getResult()==0)
                        CommonUtils.showAlertDialog(LoginActivity.this,"Đăng nhập",loginRegisterResult.getErrorString());
                    else
                    {
                        User objUser = new User();
                        objUser.setPhoneNo(edtSdt.getText().toString());
                        objUser.setPassword(edtPass.getText().toString());
                        Env.writeUser(LoginActivity.this, objUser);
                        Intent intent=new Intent(LoginActivity.this,MainActivityTaxi.class);
                        startActivity(intent);

                    }
                    Log.d("login:",loginRegisterResult.getAll());
                }

                @Override
                public void failure(RetrofitError error) {
                    progressBar.setVisibility(View.GONE);
                    Log.d("login:","Error:"+error.getMessage());
                }
            });

        }
    }
}
