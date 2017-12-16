package com.mfekim.testallo.model.config;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response result of the config API.
 */
public class AVConfigResponseResult {
    /** Tag for logs. */
    private static final String TAG = AVConfigResponseResult.class.getSimpleName();

    /** GSON. */
    private static final Gson GSON = new Gson();

    @SerializedName("categories")
    private List<AVCategory> mCategories;

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
