package com.mfekim.testallo.data.model.config;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Response of the config API.
 */
public class AVConfigResponse {
    /** Tag for logs. */
    private static final String TAG = AVConfigResponse.class.getSimpleName();

    /** GSON. */
    private static final Gson GSON = new Gson();

    @SerializedName("error")
    private String mError;

    @SerializedName("result")
    private AVConfigResponseResult mResult;

    /**
     * @return True if an error exist, false otherwise.
     */
    public boolean hasError() {
        return !TextUtils.isEmpty(mError);
    }

    /**
     * @return True if there is a result, false otherwise.
     */
    public boolean hasResult() {
        return getResult() != null;
    }

    /**
     * @return The response result.
     */
    public AVConfigResponseResult getResult() {
        return mResult;
    }


    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
