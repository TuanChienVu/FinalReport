package com.dclover.gpsutilities.thoitiet;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AlertDialog.Builder;
import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.thoitiet.fragment.Fragment1;


public class ThoiTietActivity extends AppCompatActivity {
    private Toolbar toolbar;
    class C02031 implements DialogInterface.OnClickListener {
        C02031() {
        }

        public void onClick(DialogInterface dialog, int id) {
            ThoiTietActivity.this.finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thoi_tiet);
        this.toolbar = (Toolbar) findViewById(R.id.android_bar_testing3);
        setSupportActionBar(this.toolbar);
        setTitle("Thời tiết");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.frag_nav)).setUp(R.id.frag_nav, (DrawerLayout) findViewById(R.id.drawer_layout), this.toolbar);
        if (Boolean.valueOf(new ConnectionDetector(getApplicationContext()).isConnectingToInternet()).booleanValue()) {
            Fragment fragment1 = new Fragment1();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.containerView, fragment1, null);
            fragmentTransaction.commit();
            return;
        }
        showAlertDialog(this);
    }
    public void showAlertDialog(Context context) {
        Builder alertDialogBuilder = new Builder(context);
        alertDialogBuilder.setTitle((CharSequence) "Không có kết nối internet");
        alertDialogBuilder.setMessage((CharSequence) "Xin hãy kiểm tra lại kết nối").setCancelable(false).setPositiveButton((CharSequence) "OK", new C02031());
        alertDialogBuilder.create().show();
    }
}
