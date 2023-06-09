package com.example.mobileinfo;

public interface MainContract {

    interface View {
        void updateGyroSensorDataChanged(float xRotationRate,
                                         float yRotationRate,
                                         float zRotationRate);
        void updateAccelerationSensorDataChanged(float xAcceleration,
                                                 float yAcceleration,
                                                 float zAcceleration);
    }

    interface Presenter {

        void registerSensorsListeners();

        void unregisterSensorsListeners();
    }
}
