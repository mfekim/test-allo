package com.mfekim.testallo.data.model.demand;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response of the demand API.
 */
public class AVDemandResponse {
    /** Tag for logs. */
    private static final String TAG = AVDemandResponse.class.getSimpleName();

    /** GSON. */
    private static final Gson GSON = new Gson();

    @SerializedName("error")
    private String mError;

    @SerializedName("result")
    private List<AVDemand> mResult;

    /**
     * @return True if an error exist, false otherwise.
     */
    public boolean hasError() {
        return !TextUtils.isEmpty(mError);
    }

    //region Getters

    /**
     * @return List of {@link AVDemand}.
     */
    public List<AVDemand> getResult() {
        return mResult;
    }
    //endregion

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
