package com.example.mobileinfo;



import android.content.pm.PackageManager;
import android.hardware.Camera;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class CameraDetailsActivity extends AppCompatActivity {

    private TextView cameraDetailsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_details);

        cameraDetailsTextView = findViewById(R.id.camera_details_textview);

        // Check if the device has a camera
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            cameraDetailsTextView.setText("No camera detected");
            return;
        }

        // Get the first (rear) camera instance
        Camera camera = Camera.open();

        // Get the camera parameters
        Camera.Parameters params = camera.getParameters();

        // Get the camera megapixels
        int megapixels = getCameraMegapixels(params);


        // Display the camera details
        cameraDetailsTextView.setText("Megapixels: " + megapixels);

        // Release the camera instance
        camera.release();
    }

    private int getCameraMegapixels(Camera.Parameters params) {
        List<Camera.Size> sizes = params.getSupportedPictureSizes();
        Camera.Size size = sizes.get(0);
        double megapixels = (size.width * size.height) / 1000000.0;
        return (int) megapixels;
    }


}
