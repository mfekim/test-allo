package com.mfekim.testallo.data.model.demand;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
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
public class AVDemand implements Parcelable {
    /* Demand Types. */
    public static final String OBJECT = "1";
    public static final String SERVICE = "2";

    /** Tag for logs. */
    private static final String TAG = AVDemand.class.getSimpleName();

    /** The URL to get thumbnails. */
    private static final String PATTERN_THUMBNAIL_URL = "https://www.allovoisins.com/uploads/avatars/%s";

    /** Date format. */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRENCH);

    @SerializedName("search_id")
    private String mSearchId;

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

    @SerializedName("published_at")
    private String mPublishedAt;

    @SerializedName("city_name")
    private String mCityName;

    @SerializedName("category_type")
    private String mCategoryType;

    @SerializedName("nb_conversation")
    private String mAnswerCount;

    @SerializedName("description")
    private String mDescription;

    // Update date
    private Date mUpdateDate;

    // Publish date
    private Date mPublishDate;

    // Location
    private Location mLocation;

    //region Parcelable Methods
    public static final Parcelable.Creator<AVDemand> CREATOR =
            new Parcelable.Creator<AVDemand>() {
                @Override
                public AVDemand createFromParcel(Parcel source) {
                    return new AVDemand(source);
                }

                @Override
                public AVDemand[] newArray(int size) {
                    return new AVDemand[size];
                }
            };

    public AVDemand(Parcel in) {
        mSearchId = in.readString();
        mThumbnailFilename = in.readString();
        mFirstName = in.readString();
        mLatitude = in.readString();
        mLongitude = in.readString();
        mCategoryId = in.readString();
        mUpdatedAt = in.readString();
        mPublishedAt = in.readString();
        mCityName = in.readString();
        mCategoryType = in.readString();
        mAnswerCount = in.readString();
        mDescription = in.readString();
        mUpdateDate = (Date) in.readSerializable();
        mPublishDate = (Date) in.readSerializable();
        mLocation = in.readParcelable(Location.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSearchId);
        dest.writeString(mThumbnailFilename);
        dest.writeString(mFirstName);
        dest.writeString(mLatitude);
        dest.writeString(mLongitude);
        dest.writeString(mCategoryId);
        dest.writeString(mUpdatedAt);
        dest.writeString(mPublishedAt);
        dest.writeString(mCityName);
        dest.writeString(mCategoryType);
        dest.writeString(mAnswerCount);
        dest.writeString(mDescription);
        dest.writeSerializable(mUpdateDate);
        dest.writeSerializable(mPublishDate);
        dest.writeParcelable(mLocation, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    //endregion

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

    /**
     * @return The publish date, null otherwise.
     */
    public Date getPublishDate() {
        if (mPublishDate == null && !TextUtils.isEmpty(mPublishedAt)) {
            try {
                mPublishDate = DATE_FORMAT.parse(mPublishedAt);
            } catch (ParseException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

        return mPublishDate;
    }

    /**
     * @return A location, null otherwise.
     */
    public Location getLocation() {
        if (mLocation == null) {
            if (hasLatitude() && hasLongitude()) {
                try {
                    mLocation = new Location("Demand " + mSearchId);
                    mLocation.setLatitude(Double.valueOf(mLatitude));
                    mLocation.setLongitude(Double.valueOf(mLongitude));
                } catch (NumberFormatException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    mLocation = null;
                }
            }
        }

        return mLocation;
    }

    /**
     * @return True if there is a latitude, false otherwise.
     */
    public boolean hasLatitude() {
        return !TextUtils.isEmpty(mLatitude) && getLatitude() != null;
    }

    /**
     * @return True if there is a longitude, false otherwise.
     */
    public boolean hasLongitude() {
        return !TextUtils.isEmpty(mLongitude) && getLongitude() != null;
    }

    //region Getters
    public String getThumbnailUrl() {
        return TextUtils.isEmpty(mThumbnailFilename) ? null :
                String.format(PATTERN_THUMBNAIL_URL, mThumbnailFilename);
    }

    public String getFirstName() {
        return mFirstName;
    }

    public Double getLatitude() {
        try {
            return Double.valueOf(mLatitude);
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return null;
        }
    }

    public Double getLongitude() {
        try {
            return Double.valueOf(mLongitude);
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return null;
        }
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

    public String getDescription() {
        return mDescription;
    }

    //endregion
}
