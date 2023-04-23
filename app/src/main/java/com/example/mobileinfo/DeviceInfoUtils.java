package com.example.mobileinfo;



import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

import java.util.List;
import java.util.UUID;

/**
 * Created by yovi.putra
 */
@SuppressLint({"HardwareIds","MissingPermission"})
public class DeviceInfoUtils {
    // ICCID (Integrated Circuit Card Identifier)
    public static String getICCID(Context context) {
        if (isPermissionGranted(context)) {
            if (isICCIDGranted()) {
                StringBuilder iccid = new StringBuilder();
                List<SubscriptionInfo> subscription = SubscriptionManager.from(context).getActiveSubscriptionInfoList();
                if (subscription == null) return null;

                for (SubscriptionInfo info : subscription) {
                    iccid.append(info.getIccId());
                }
                return iccid.toString().isEmpty() ? null : iccid.toString();
            } else {
                //dual sim not support SDK < lollipop
                return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
            }
        }
        return null;
    }

    // IMSI (International Mobile Subscriber Identity)
    public static String getIMSI(Context context) {
        String iccid = getICCID(context);
        iccid = iccid == null ? "" : iccid;
        return new UUID(getAndroidId(context).hashCode(), iccid.hashCode()).toString();
    }

    // IMEI (International Mobile Equipment Identity)
    public static String getIMEI(Context context){
        return generateIMEI(context);
    }

    private static boolean isPermissionGranted(Context context) {
        String wantPermission = Manifest.permission.READ_PHONE_STATE;
        return ActivityCompat.checkSelfPermission(context, wantPermission) == PackageManager.PERMISSION_GRANTED;
    }

    private static boolean isICCIDGranted() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    private static String generateIMEI(Context context) {
        String uniquePseudoID = "" +
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10;
        return new UUID(uniquePseudoID.hashCode(), getAndroidId(context).hashCode()).toString();
    }

    private static String getAndroidId(Context context) {
        String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return id == null ? "" : id;
    }
}