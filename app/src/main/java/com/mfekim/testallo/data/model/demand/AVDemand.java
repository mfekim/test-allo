package com.mfekim.testallo.data.model.demand;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A demand.
 */
public class AVDemand {
    /* Demand Types. */
    public static final String OBJECT = "1";
    public static final String SERVICE = "2";

    /** Tag for logs. */
    private static final String TAG = AVDemand.class.getSimpleName();

    /** The URL to get thumbnails. */
    private static final String PATTERN_THUMBNAIL_URL = "https://www.allovoisins.com/uploads/avatars/%s";

    /** Date format. */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRENCH);

    @SerializedName("avatar")
    private String mThumbnailFilename;

    @SerializedName("first_name")
    private String mFirstName;

    @SerializedName("latitude")
    private String mLatitude;

    @SerializedName("longitude")
    private String mLongitude;

    @SerializedName("category_id")
    private String mCategoryId;

    @SerializedName("updated_at")
    private String mUpdatedAt;

    @SerializedName("city_name")
    private String mCityName;

    @SerializedName("category_type")
    private String mCategoryType;

    @SerializedName("nb_conversation")
    private String mAnswerCount;

    // The update date
    private Date mUpdateDate;

    /**
     * @return The update date, null otherwise.
     */
    public Date getUpdateDate() {
        if (mUpdateDate == null && !TextUtils.isEmpty(mUpdatedAt)) {
            try {
                mUpdateDate = DATE_FORMAT.parse(mUpdatedAt);
            } catch (ParseException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

        return mUpdateDate;
    }

    //region Getters
    public String getThumbnailUrl() {
        return TextUtils.isEmpty(mThumbnailFilename) ? null :
                String.format(PATTERN_THUMBNAIL_URL, mThumbnailFilename);
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public String getCategoryId() {
        return mCategoryId;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public String getCityName() {
        return mCityName;
    }

    public String getCategoryType() {
        return mCategoryType;
    }

    public int getAnswerCount() {
        try {
            return Integer.valueOf(mAnswerCount);
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return 0;
        }
    }
    //endregion
}
