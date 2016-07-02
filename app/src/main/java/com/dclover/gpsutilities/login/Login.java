package com.dclover.gpsutilities.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dclover.gpsutilities.MainActivity;
import com.dclover.gpsutilities.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity {

    @Bind(R.id.ll_login)
    LinearLayout llLogin;
    @Bind(R.id.edt_login_email)
    EditText editEmail;
    @Bind(R.id.edt_login_pass)
    EditText edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        String linkBD = getBaseContext().getResources().getString(R.string.linkDB);
        Firebase.setAndroidContext(this);
        final Firebase myFirebaseRef = new Firebase(linkBD);
        llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFirebaseRef.authWithPassword(editEmail.getText().toString(), edtPass.getText().toString(),
                        new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {

                                new AsyncTask<Void,Void,Void>()
                                {

                                    @Override
                                    protected Void doInBackground(Void... params) {
                                        return null;
                                    }
                                }.execute();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);

                            }

                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {
                                // there was an error
                               Log.d("login.error", firebaseError.getMessage());
                            }
                        });


            }
        });


    }
}
