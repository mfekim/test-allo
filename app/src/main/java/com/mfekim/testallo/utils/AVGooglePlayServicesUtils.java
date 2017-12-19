package com.mfekim.testallo.utils;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Utils methods for Google Play Services.
 */
public class AVGooglePlayServicesUtils {
    /** Tag for logs. */
    private static final String TAG = AVGooglePlayServicesUtils.class.getSimpleName();

    /**
     * Checks the Google Play Services availability and usability.
     * <p>
     * MUST BE CALLED into the {@link Activity#onResume()} method.
     *
     * @param activity Activity.
     * @return true if the Google Play Service is available and can be used, false otherwise.
     */
    public static boolean isGooglePlayServicesAvailable(Activity activity) {
        int statusCode = GoogleApiAvailability.getInstance()
                                              .isGooglePlayServicesAvailable(activity);
        switch (statusCode) {
            case ConnectionResult.SUCCESS:
                Log.d(TAG, "Checking Google play services availability succeeded");
                return true;
            case ConnectionResult.SERVICE_MISSING:
            case ConnectionResult.SERVICE_UPDATING:
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
            case ConnectionResult.SERVICE_DISABLED:
            case ConnectionResult.SERVICE_INVALID:
            default:
                Log.e(TAG, "Checking Google play service availability failed");
                GoogleApiAvailability.getInstance()
                                     .getErrorDialog(activity, statusCode, 0)
                                     .show();
        }

        return false;
    }
}
