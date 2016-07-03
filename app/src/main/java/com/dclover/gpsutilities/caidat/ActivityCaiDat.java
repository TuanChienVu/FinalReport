package com.dclover.gpsutilities.caidat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.login.Login;
import com.dclover.gpsutilities.taxi.Utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityCaiDat extends AppCompatActivity {

    @Bind(R.id.cv_setting_logout)
    LinearLayout cvSettingLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_dat);
        ButterKnife.bind(this);
        cvSettingLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences sharedPref = getSharedPreferences(Constants.MY_APP, MODE_PRIVATE);
                final SharedPreferences.Editor edit=sharedPref.edit();
                edit.putString("login.email","");
                edit.commit();
                Intent intent=new Intent(ActivityCaiDat.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
