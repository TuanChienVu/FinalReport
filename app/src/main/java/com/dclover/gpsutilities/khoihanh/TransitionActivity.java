package com.dclover.gpsutilities.khoihanh;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dclover.gpsutilities.R;

public class TransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout relativeLayout_TransitionMain = new RelativeLayout(this);
        relativeLayout_TransitionMain.setBackgroundResource(R.drawable.shape_insdialog);
        relativeLayout_TransitionMain.setLayoutParams(new ActionBar.LayoutParams(-1, -1));
        TextView TextView_Loading = new TextView(this);
        TextView_Loading.setText(getResources().getString(R.string.text_TransitionMsg));
        TextView_Loading.setTypeface(Typeface.SERIF, 1);
        TextView_Loading.setGravity(17);
        RelativeLayout.LayoutParams mRLParams = new RelativeLayout.LayoutParams(-2, -2);
        mRLParams.addRule(13);
        relativeLayout_TransitionMain.addView(TextView_Loading, mRLParams);
        setContentView(relativeLayout_TransitionMain);
        startActivity(new Intent(this, ActivityKhoiHanh.class));
        finish();
    }
    public void onBackPressed() {
    }
}
