package com.mfekim.testallo.model.demand;

import com.google.gson.annotations.SerializedName;

/**
 * A demand.
 */
public class AVDemand {
    /** Tag for logs. */
    private static final String TAG = AVDemand.class.getSimpleName();

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
    private String mConversationCount;
}
