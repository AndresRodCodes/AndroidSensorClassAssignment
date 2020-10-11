package com.example.detectthemagneticfield;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MagFieldApp";

    private SensorManager mSensorManager;
    private Sensor mMagneticField;

    private TextView textViewMagSensorX;
    private TextView textViewMagSensorY;
    private TextView textViewMagSensorZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Sensor Manager and Magnetic Field Sensor
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        // Connect the TextViews that will show that Magnetic Field data
        textViewMagSensorX = findViewById(R.id.magSensorX);
        textViewMagSensorY = findViewById(R.id.magSensorY);
        textViewMagSensorZ = findViewById(R.id.magSensorZ);
    }

    // Stop sensor when app is not open
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mMagneticField);
    }

    // Start the sensor when app is opened
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        // Check for active sensor
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            // Print Sensor values
            Log.d(TAG, Arrays.toString(event.values));

            // Show Sensor Values on screen
            textViewMagSensorX.setText("X Axis: " + event.values[0]);
            textViewMagSensorY.setText("Y Axis: " + event.values[1]);
            textViewMagSensorZ.setText("Z Axis: " + event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}