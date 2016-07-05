package com.dclover.gpsutilities.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.dclover.gpsutilities.R;

public class ComPass extends AppCompatActivity implements SensorEventListener {

    // define the display assembly compass picture
    private static final float ALPHA = 0.03f;
    private Sensor accel;
    private float currentDegree;
    private ImageView image;
    private float lastTextUpdateValue;
    private Sensor magnetic;
    private SensorManager sensorManager;


    public ComPass() {
        this.currentDegree = 0.0f;
        this.lastTextUpdateValue = 0.0f;
    }
    @Override
    protected void onResume() {
        super.onResume();
        this.sensorManager.registerListener(this, this.sensorManager.getDefaultSensor(3), 1);

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.sensorManager.unregisterListener(this);

    }
    private float[] lowPass(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2) {
        if (paramArrayOfFloat2 == null) {
            return paramArrayOfFloat1;
        }
        for (int i = 0; i < paramArrayOfFloat1.length; i++) {
            paramArrayOfFloat2[i] = paramArrayOfFloat2[i] + (ALPHA * (paramArrayOfFloat1[i] - paramArrayOfFloat2[i]));
        }
        return paramArrayOfFloat2;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.accel = this.sensorManager.getDefaultSensor(1);
        this.magnetic = this.sensorManager.getDefaultSensor(2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_pass);
        this.image = (ImageView) findViewById(R.id.imageViewCompass);

        this.setTitle("Compass");

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float degree = event.values[0];
        RotateAnimation ra = new RotateAnimation(this.currentDegree, -degree, 1, 0.5f, 1, 0.5f);
        ra.setDuration(210);
        ra.setFillAfter(true);
        this.image.startAnimation(ra);
        this.currentDegree = -degree;
        if (Math.abs(this.lastTextUpdateValue - this.currentDegree) > 0.2f) {
            this.lastTextUpdateValue = this.currentDegree;
            float cdegree = 360.0f - degree;
            int degrees = (int) Math.floor((double) cdegree);
            float minutes = (cdegree - ((float) degrees)) * 60.0f;
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
