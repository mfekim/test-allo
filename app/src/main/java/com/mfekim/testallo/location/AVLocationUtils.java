package com.mfekim.testallo.location;

import android.location.Location;
import android.util.Log;

import java.util.Locale;

/**
 * Utils methods for location in general.
 */
public abstract class AVLocationUtils {
    /** Tag for logs. */
    private static final String TAG = AVLocationUtils.class.getSimpleName();

    /**
     * Gets the distance between two locations in formatted string.
     *
     * @param pointA First location.
     * @param pointB Second location.
     * @return The distance between the two locations in formatted string, null otherwise.
     */
    public static String distanceTo(Location pointA, Location pointB) {
        String formattedDistance = null;
        if (pointA != null && pointB != null) {
            float distanceInMeter = pointA.distanceTo(pointB);
            float distanceInKilometer = distanceInMeter / 1000f;
            formattedDistance = distanceInKilometer >= 1f ?
                    String.format(Locale.FRENCH, "%.1f km", distanceInKilometer) :
                    String.format(Locale.FRENCH, "%.0f m", distanceInMeter);
        } else {
            Log.e(TAG, "Calculate distance failed - location(s) null");
        }

        return formattedDistance;
    }
}
