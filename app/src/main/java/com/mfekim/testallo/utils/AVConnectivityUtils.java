package com.mfekim.testallo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Regroups helpful methods about connectivity.
 */
public class AVConnectivityUtils {
    /** Tag for logs. */
    private static final String TAG = AVConnectivityUtils.class.getSimpleName();

    /**
     * @param context Context.
     * @return true if there is a connection, false otherwise.
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ?
                connectivityManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();
    }
}
