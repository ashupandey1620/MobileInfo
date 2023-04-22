package com.example.mobileinfo;

import static android.content.ContentValues.TAG;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Context;
import android.graphics.Camera;
import android.telephony.TelephonyManager;

import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Size;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.util.Arrays;

import javax.microedition.khronos.egl.EGLConfig;

public class MainActivity extends AppCompatActivity {





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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManufacturer = findViewById(R.id.tv_manufacturer);
        mModel = findViewById(R.id.tv_model_name);
        mRam = findViewById(R.id.tv_ram);
        mStorage = findViewById(R.id.tv_storage);
        mBatteryLevel = findViewById(R.id.tv_battery_level);

         mVersion = findViewById(R.id.tv_android_version);
         mCameraMP=findViewById(R.id.tv_camera_mp);
         mCameraAperture=findViewById(R.id.tv_camera_aperture);
         mProcessorInfo=findViewById(R.id.tv_processor_info);
         mGPUInfo=findViewById(R.id.tv_gpu_info);
         mIMEIInfo=findViewById(R.id.tv_imei);

        // Retrieve device information
        getDeviceInfo();
    }

    private void getDeviceInfo() {
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


    }


}
