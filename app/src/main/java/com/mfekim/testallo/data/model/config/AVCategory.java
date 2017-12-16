package com.mfekim.testallo.data.model.config;

import com.google.gson.annotations.SerializedName;

/**
 * Category.
 */
public class AVCategory {
    /** Tag for logs. */
    private static final String TAG = AVCategory.class.getSimpleName();

    @SerializedName("category_id")
    private String mId;

    @SerializedName("name")
    private String mName;
}
