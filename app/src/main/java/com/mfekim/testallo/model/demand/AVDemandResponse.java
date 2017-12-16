package com.mfekim.testallo.model.demand;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The API response when fetching demands.
 */
public class AVDemandResponse {
    /** Tag for logs. */
    private static final String TAG = AVDemandResponse.class.getSimpleName();

    /** GSON. */
    private static final Gson GSON = new Gson();

    @SerializedName("error")
    private String mError;

    @SerializedName("result")
    private List<AVDemand> mDemands;

    /**
     * @return True if an error exist, false otherwise.
     */
    public boolean hasError() {
        return !TextUtils.isEmpty(mError);
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
