package com.dclover.gpsutilities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.taxi.Activity.LoginActivity;

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
        Firebase.setAndroidContext(this);
        final Firebase myFirebaseRef = new Firebase(linkBD);
        llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFirebaseRef.authWithPassword(edEmailLogin.getText().toString(), edPassLogin.getText().toString(),
                        new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                Toast.makeText(Login.this, "User ID: " + authData.getUid() +
                                        ", Provider: " + authData.getProvider(), Toast.LENGTH_SHORT).show();
                                SharedPrefsUtils.setStringPreference(LoginActivity.this,"UserID",authData.getUid());
                                SharedPrefsUtils.setStringPreference(LoginActivity.this,"UserName",edEmailLogin.getText().toString());
                                Intent intent = new Intent(Login.this, ChatActivity.class);
                                startActivity(intent);

                            }

                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {
                                // there was an error
                               Log.d("login.error", firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });


    }
}
