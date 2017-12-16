package com.mfekim.testallo.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

/**
 * Util methods for bitmaps.
 */
public class AVBitmapUtils {
    /** Tag for logs. */
    private static final String TAG = AVBitmapUtils.class.getSimpleName();

    /**
     * Rounds a bitmap.
     *
     * @param bitmap Bitmap to round.
     * @return A rounded bitmap.
     */
    public static Drawable roundBitmap(Resources resources, Bitmap bitmap) {
        RoundedBitmapDrawable bitmapDrawable = null;
        if (resources != null && bitmap != null) {
            bitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap);
            bitmapDrawable.setCircular(true);
            bitmapDrawable.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getHeight()) / 2.0f);
        }

        return bitmapDrawable;
    }
}
