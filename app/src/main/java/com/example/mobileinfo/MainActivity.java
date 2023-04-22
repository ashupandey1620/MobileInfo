package com.example.mobileinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String manufacturer, modelName, modelNumber;
    private long ram, storage;
    private int batteryLevel;

    private TextView mManufacturer;
    private TextView mModel;
    private TextView mRam;
    private TextView mStorage;
    private TextView mBatteryLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManufacturer = findViewById(R.id.tv_manufacturer);
        mModel = findViewById(R.id.tv_model_name);
        mRam = findViewById(R.id.tv_ram);
        mStorage = findViewById(R.id.tv_storage);
        mBatteryLevel = findViewById(R.id.tv_battery_level);
        // Retrieve device information
        getDeviceInfo();
    }

    private void getDeviceInfo() {
        // Get device manufacturer, model name, and model number
        manufacturer = Build.MANUFACTURER;
        modelName = Build.MODEL;
        modelNumber = Build.DEVICE;

        // Get device RAM and storage
        ram = Runtime.getRuntime().totalMemory();
        storage = android.os.Environment.getExternalStorageDirectory().getTotalSpace();

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

       /** System.out.println("RAM: " + ram + " bytes");
        mRam.setText((int) ram);

        System.out.println("Storage: " + storage + " bytes");
        mStorage.setText((int) storage);

        mBatteryLevel.setText(batteryLevel);
        System.out.println("Battery Level: " + batteryLevel + "%");
        */
    }
}
