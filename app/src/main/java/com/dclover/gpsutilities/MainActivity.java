package com.dclover.gpsutilities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dclover.gpsutilities.caidat.ActivityCaiDat;
import com.dclover.gpsutilities.compass.ComPass;
import com.dclover.gpsutilities.khoihanh.ActivityKhoiHanh;
import com.dclover.gpsutilities.maps.MapsActivity;

public class MainActivity extends AppCompatActivity {

    LinearLayout llcompas,llcaidat,llhuongdan,llketnoi,lltaxi,llkhoihanh,llbando;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llcompas=(LinearLayout) findViewById(R.id.ll_compass);
        llcaidat=(LinearLayout) findViewById(R.id.ll_caidat);
        llhuongdan=(LinearLayout) findViewById(R.id.ll_huongdan);
        llketnoi=(LinearLayout) findViewById(R.id.ll_connect);
        lltaxi=(LinearLayout) findViewById(R.id.ll_taxi);
        llkhoihanh=(LinearLayout) findViewById(R.id.ll_khoihanh);
        llbando=(LinearLayout) findViewById(R.id.ll_map);

        llcompas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ComPass.class);
                startActivity(intent);
            }
        });
        llcaidat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customToast("Chức năng này đang phát triển");
            }
        });
        llhuongdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customToast("Chức năng này đang phát triển");
            }
        });
        llketnoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customToast("Chức năng này đang phát triển");
            }
        });
        llkhoihanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),ActivityKhoiHanh.class);
                startActivity(intent);
            }
        });
        llbando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        lltaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customToast("Chức năng này đang phát triển");
            }
        });
        llcaidat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),ActivityCaiDat.class);
                startActivity(intent);
            }
        });

    }


    private void customToast(String msg){
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = layoutInflater.inflate(R.layout.fb_comments_list_item, null,false);
        TextView tvTitle = (TextView) inflatedView.findViewById(R.id.tv_text_popup);
        tvTitle.setText(msg);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setView(inflatedView);
        toast.show();
    }
}
