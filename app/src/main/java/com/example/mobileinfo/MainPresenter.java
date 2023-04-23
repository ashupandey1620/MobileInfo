package com.example.mobileinfo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    private SensorManager sensorManager;

    private Sensor sensorGyroscope;
    private Sensor sensorAccelerometer;


    private SensorEventListener sensorGyroscopeEvenListener;
    private SensorEventListener sensorAccelerometerEvenListener;

    public MainPresenter(MainContract.View view, SensorManager sensorManager) {
        this.view = view;
        this.sensorManager = sensorManager;
        initSensors();
    }

    public void registerSensorsListeners() {
        sensorManager.registerListener(sensorGyroscopeEvenListener,
                sensorGyroscope,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorAccelerometerEvenListener,
                sensorAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterSensorsListeners() {

        sensorManager.unregisterListener(sensorGyroscopeEvenListener);
        sensorManager.unregisterListener(sensorAccelerometerEvenListener);
    }

    private void initSensors() {

        sensorGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        initSensorsListeners();
    }

    private void initSensorsListeners() {
        if (sensorGyroscopeEvenListener == null) {
            sensorGyroscopeEvenListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    // round value to 2 decimal points
                    float xRotationRate = roundFloat(sensorEvent.values[0]);
                    float yRotationRate = roundFloat(sensorEvent.values[1]);
                    float zRotationRate = roundFloat(sensorEvent.values[2]);

                    view.updateGyroSensorDataChanged(xRotationRate, yRotationRate, zRotationRate);
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {
                }
            };
        }
        if (sensorAccelerometerEvenListener == null) {
            sensorAccelerometerEvenListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    // round value to 2 decimal points
                    float xAcceleration = roundFloat(sensorEvent.values[0]);
                    float yAcceleration = roundFloat(sensorEvent.values[1]);
                    float zAcceleration = roundFloat(sensorEvent.values[2]);

                    view.updateAccelerationSensorDataChanged(xAcceleration, yAcceleration, zAcceleration);
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {
                }
            };
        }
    }

    /**
     * Rounds value to 2 decimal points
     *
     * @param value: float to round
     * @return float rounded to 2 decimal points
     */
    private float roundFloat(float value) {
        return (float) Math.round(value * 100) / 100;
    }
}
