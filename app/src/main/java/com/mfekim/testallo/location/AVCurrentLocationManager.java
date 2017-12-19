package com.mfekim.testallo.location;

import android.Manifest;
import android.app.Activity;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Manages the current location.
 */
public class AVCurrentLocationManager {
    /** Request code. */
    public static final int CHECK_REQUEST_LOCATION = 1100;
    public static final int PERMISSION_REQUEST_LOCATION = 1200;

    /** Tag for logs. */
    private static final String TAG = AVCurrentLocationManager.class.getSimpleName();

    /** Location client. */
    private FusedLocationProviderClient mFusedLocationClient;

    /** Holder. */
    private static class SingletonHolder {
        /** Unique instance. */
        private final static AVCurrentLocationManager INSTANCE = new AVCurrentLocationManager();
    }

    /** Unique entry point to get the instance. */
    public static AVCurrentLocationManager getInstance() {
        return AVCurrentLocationManager.SingletonHolder.INSTANCE;
    }

    /** Private constructor. */
    private AVCurrentLocationManager() {
    }

    /**
     * Gets the current location asynchronously.
     *
     * @param activity        Activity.
     * @param successListener Success listener which will contain the current location.
     */
    public void getCurrentLocationAsync(final Activity activity,
                                        final OnSuccessListener<Location> successListener) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                // No explanation needed, we can request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQUEST_LOCATION);
                // PERMISSION_REQUEST_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            getLocationClient(activity)
                    .getLastLocation()
                    .addOnFailureListener(activity, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof ResolvableApiException) {
                                // Location settings are not satisfied, but this can be fixed
                                // by showing the user a dialog.
                                try {
                                    // Show the dialog by calling startResolutionForResult(),
                                    // and check the result in onActivityResult().
                                    ResolvableApiException resolvable = (ResolvableApiException) e;
                                    resolvable.startResolutionForResult(activity,
                                            CHECK_REQUEST_LOCATION);
                                } catch (IntentSender.SendIntentException sendEx) {
                                    // Ignore the error.
                                }
                            }
                        }
                    })
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                successListener.onSuccess(location);
                            }
                        }
                    });
        }
    }

    /**
     * @param activity Activity.
     * @return The location client.
     */
    private FusedLocationProviderClient getLocationClient(Activity activity) {
        if (mFusedLocationClient == null) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        }
        return mFusedLocationClient;
    }
}
