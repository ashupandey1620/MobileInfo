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

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String manufacturer, modelName, modelNumber;
    private long ram, storage;
    private int batteryLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        System.out.println("Model Name: " + modelName);
        System.out.println("Model Number: " + modelNumber);
        System.out.println("RAM: " + ram + " bytes");
        System.out.println("Storage: " + storage + " bytes");
        System.out.println("Battery Level: " + batteryLevel + "%");
    }
}
