package com.mfekim.testallo.data.model.config;

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

    //region Getters
    public List<AVCategory> getCategories() {
        return mCategories;
    }
    //endregion

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
