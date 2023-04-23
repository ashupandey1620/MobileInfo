package com.example.mobileinfo;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private MainPresenter presenter;
    private String manufacturer, modelName, modelNumber, mProcessorInfostr;
    private long ram, storage;
    private int batteryLevel;

    private TextView mManufacturer;
    private TextView mModel;
    private TextView mRam;
    private TextView mStorage;
    private TextView mBatteryLevel;

    private TextView mVersion;
    private TextView mCameraMP;
    private TextView mCameraAperture;
    private TextView mProcessorInfo;
    private TextView mGPUInfo;
    private TextView mIMEIInfo;
    private TextView mProximity;

    @BindView(R.id.orientation_x_axis) TextView textOrientationXAxis;
    @BindView(R.id.orientation_y_axis) TextView textOrientationYAxis;
    @BindView(R.id.orientation_z_axis) TextView textOrientationZAxis;
    @BindView(R.id.gyro_x_axis) TextView textGyroXAxis;
    @BindView(R.id.gyro_y_axis) TextView textGyroYAxis;
    @BindView(R.id.gyro_z_axis) TextView textGyroZAxis;
    @BindView(R.id.acceleration_x_axis) TextView textAccelerationXAxis;
    @BindView(R.id.acceleration_y_axis) TextView textAccelerationYAxis;
    @BindView(R.id.acceleration_z_axis) TextView textAccelerationZAxis;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        presenter = new MainPresenter((MainContract.View) this, sensorManager);

        mManufacturer = findViewById(R.id.tv_manufacturer);
        mModel = findViewById(R.id.tv_model_name);
        mRam = findViewById(R.id.tv_ram);
        mStorage = findViewById(R.id.tv_storage);
        mBatteryLevel = findViewById(R.id.tv_battery_level);

        mVersion = findViewById(R.id.tv_android_version);
        mCameraMP = findViewById(R.id.tv_camera_mp);
        mCameraAperture = findViewById(R.id.tv_camera_aperture);
        mProcessorInfo = findViewById(R.id.tv_processor_info);
        mGPUInfo = findViewById(R.id.tv_gpu_info);
        mIMEIInfo = findViewById(R.id.tv_imei);

        mProximity = findViewById(R.id.tv_proximity);

        // Retrieve device information
        getDeviceInfo();
    }

    private  void getDeviceInfo() {
        // Get device manufacturer, model name, and model number
        manufacturer = Build.MANUFACTURER;
        modelName = Build.MODEL;
        modelNumber = Build.DEVICE;

        // Get device RAM and storage
        long ramBytes = Runtime.getRuntime().totalMemory();
        double ramGB = Math.round(ramBytes / (1024.0 * 1024.0 * 1024.0) * 100.0) / 100.0;

        long storageBytes = new File(getFilesDir().getAbsolutePath()).getTotalSpace();
        double storageGB = Math.round(storageBytes / (1024.0 * 1024.0 * 1024.0) * 100.0) / 100.0;


        // Get battery level
        Intent intent = this.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        batteryLevel = (int) ((float) batteryLevel / (float) scale * 100.0f);

        // Print device information
        System.out.println("Manufacturer: " + manufacturer);
        mManufacturer.setText(manufacturer);

        System.out.println("Model Name: " + modelName);
        mModel.setText(modelName);

        System.out.println("RAM: " + ram + " bytes");
        mRam.setText(ramGB + " GB");

        System.out.println("Storage: " + storage + " bytes");
        mStorage.setText(storageGB + " GB");

        mBatteryLevel.setText(batteryLevel + "%");
        System.out.println("Battery Level: " + batteryLevel + "%");

        mVersion.setText("Android " + Build.VERSION.RELEASE);

        // Get the camera megapixel and aperture


        //processor
        mProcessorInfostr = Build.HARDWARE + " " + Build.BRAND + " " + Build.MODEL;
        Log.i(TAG, mProcessorInfostr);
        mProcessorInfo.setText(mProcessorInfostr);


        //Gpu


        //imei


        //Sensors Data
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);






    }



    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType()==Sensor.TYPE_PROXIMITY){
            ((TextView)findViewById(R.id.tv_proximity)).setText((""+ event.values[0]));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void updateOrientationSensorDataChanged(float xAngle, float yAngle, float zAngle) {
        textOrientationXAxis.setText(String.valueOf(xAngle));
        textOrientationYAxis.setText(String.valueOf(yAngle));
        textOrientationZAxis.setText(String.valueOf(zAngle));
    }


    public void updateGyroSensorDataChanged(float xRotationRate, float yRotationRate, float zRotationRate) {
        textGyroXAxis.setText(String.valueOf(xRotationRate));
        textGyroYAxis.setText(String.valueOf(yRotationRate));
        textGyroZAxis.setText(String.valueOf(zRotationRate));
    }


    public void updateAccelerationSensorDataChanged(float xAcceleration, float yAcceleration, float zAcceleration) {
        textAccelerationXAxis.setText(String.valueOf(xAcceleration));
        textAccelerationYAxis.setText(String.valueOf(yAcceleration));
        textAccelerationZAxis.setText(String.valueOf(zAcceleration));
    }
    protected void onResume() {
        super.onResume();
        presenter.registerSensorsListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unregisterSensorsListeners();
    }
}
