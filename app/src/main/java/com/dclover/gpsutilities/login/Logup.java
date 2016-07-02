package com.dclover.gpsutilities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dclover.gpsutilities.Model.UserInformation;
import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.ketnoi.SharedPrefsUtils;
import com.dclover.gpsutilities.taxi.Activity.LoginActivity;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class Logup extends AppCompatActivity {

    Button btnSignUp;
    EditText edName, edEmail, edPass;

    String userName, userEmail, userPass, userID;
    UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);
        String linkBD = getBaseContext().getResources().getString(R.string.linkDB);
        Firebase.setAndroidContext(this);
        final Firebase myFirebaseRef = new Firebase(linkBD);
        final Firebase userInfo = myFirebaseRef.child("User Information");
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        edName = (EditText) findViewById(R.id.input_name);
        edEmail = (EditText) findViewById(R.id.input_email);
        edPass = (EditText) findViewById(R.id.input_password);


        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("Car", 20);
        myFirebaseRef.child("Transport Info").setValue(map);

        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userEmail = edEmail.getText().toString();
                userName = edName.getText().toString();
                userPass = edPass.getText().toString();


                myFirebaseRef.createUser(edEmail.getText().toString(), edPass.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        String un = edName.getText().toString();
                        String str = un;
                        String substr = "@";
                        String[] parts = str.split(substr);
                        String userName = parts[0];
                        SharedPrefsUtils.setStringPreference(Logup.this,"UserName",userName);
                        userID = result.get("uid").toString();
//                        myFirebaseRef.child("User Information").setValue(userInformation);
                        userInformation = new UserInformation(userName, userEmail, userPass, userID);
                        userInfo.push().setValue(userInformation, new Firebase.CompletionListener() {
                            @Override
                            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                if (firebaseError != null) {
                                    Toast.makeText(Logup.this, "Error", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Logup.this, "Success", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        finish();

                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        // there was an errora
                        Toast.makeText(Logup.this, firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void login(View view) {
        Intent intent = new Intent(Logup.this, LoginActivity.class);
        startActivity(intent);
    }
}
