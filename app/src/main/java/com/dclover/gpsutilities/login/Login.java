package com.dclover.gpsutilities.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dclover.gpsutilities.MainActivity;
import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.ketnoi.Message.SharedPrefsUtils;
import com.dclover.gpsutilities.taxi.Utils.Constants;
import com.dclover.gpsutilities.taxi.Utils.Env;
import com.dclover.gpsutilities.taxi.model.User;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    @Bind(R.id.ll_login)
    LinearLayout llLogin;
    @Bind(R.id.edt_login_email)
    EditText editEmail;
    @Bind(R.id.edt_login_pass)
    EditText edtPass;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tv_login_taotaioan)
    TextView tvTaoTaiKhoan;
    @Bind(R.id.ll_login_google)
    LinearLayout llLoginGoogle;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        final SharedPreferences sharedPref = getSharedPreferences(Constants.MY_APP, MODE_PRIVATE);
        final SharedPreferences.Editor edit = sharedPref.edit();
        String login = sharedPref.getString("login.email", "");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        llLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent=Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent,9001);
            }
        });
        if (login.equals("")) {
            String linkBD = getBaseContext().getResources().getString(R.string.linkDB);
            tvTaoTaiKhoan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Login.this, Logup.class);
                    startActivity(intent);
                }
            });
            Firebase.setAndroidContext(this);
            final Firebase myFirebaseRef = new Firebase(linkBD);
            llLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            myFirebaseRef.authWithPassword(editEmail.getText().toString(), edtPass.getText().toString(),
                                    new Firebase.AuthResultHandler() {
                                        @Override
                                        public void onAuthenticated(AuthData authData) {

                                            edit.putString("login.email", editEmail.getText().toString());
                                            SharedPrefsUtils.setStringPreference(Login.this,"UserID",authData.getUid());
                                            SharedPrefsUtils.setStringPreference(Login.this,"UserName",editEmail.getText().toString());
                                            edit.putString("login.email",editEmail.getText().toString());
                                            edit.commit();
                                            progressBar.setVisibility(View.GONE);
                                            User objUser = new User();
                                            objUser.setPhoneNo("01227987634");
                                            objUser.setPassword("6163");
                                            Env.writeUser(Login.this, objUser);
                                            Intent intent = new Intent(Login.this, MainActivity.class);
                                            startActivity(intent);

                                        }

                                        @Override
                                        public void onAuthenticationError(FirebaseError firebaseError) {
                                            // there was an erro
                                            Toast.makeText(Login.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                            Log.d("login.error", firebaseError.getMessage());
                                        }
                                    });
                        }

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        protected Void doInBackground(Void... params) {
                            return null;
                        }
                    }.execute();


                }
            });
        } else {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==9001)
        {
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess())
            {
                GoogleSignInAccount account=result.getSignInAccount();

                Log.d("dd","dd"+account.getEmail()+"_"+account.getDisplayName()+"_"+account.getId());


            }
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.d("dd","error:"+connectionResult.getErrorMessage());
    }
}
