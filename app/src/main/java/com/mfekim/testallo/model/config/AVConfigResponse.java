package com.mfekim.testallo.model.config;

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

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
