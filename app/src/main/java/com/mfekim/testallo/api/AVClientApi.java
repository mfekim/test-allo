package com.mfekim.testallo.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mfekim.testallo.api.request.AVGsonRequest;
import com.mfekim.testallo.model.demand.AVDemandResponse;
import com.mfekim.testallo.network.AVNetworkClient;

/**
 * Client API.
 */
public class AVClientApi {
    /** Tag for logs. */
    private static final String TAG = AVClientApi.class.getSimpleName();

    /** URLs. */
    private static final String URL_DEMANDS = "https://firebasestorage.googleapis.com/v0/b/test-allo.appspot.com/o/service.json?alt=media&token=79528d72-b75b-44d4-b293-33b275fab3e4";

    /** Holder. */
    private static class SingletonHolder {
        /** Unique instance. */
        private final static AVClientApi INSTANCE = new AVClientApi();
    }

    /** Unique entry point to get the instance. */
    public static AVClientApi getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /** Private constructor. */
    private AVClientApi() {
    }

    /**
     * Fetches a list of demands.
     *
     * @param context       Context.
     * @param listener      Success listener.
     * @param errorListener Error listener.
     * @param requestTag    Request tag.
     */
    public void fetchDemands(Context context, final Response.Listener<AVDemandResponse> listener,
                             final Response.ErrorListener errorListener, String requestTag) {
        AVGsonRequest request = new AVGsonRequest<>(getDemandsUrl(), AVDemandResponse.class, null,
                new Response.Listener<AVDemandResponse>() {
                    @Override
                    public void onResponse(AVDemandResponse response) {
                        Log.d(TAG, "Fetching demands succeeded");
                        if (listener != null) {
                            listener.onResponse(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Fetching demands failed - " + error.getLocalizedMessage());
                        if (errorListener != null) {
                            errorListener.onErrorResponse(error);
                        }
                    }
                });
        request.setTag(requestTag);
        AVNetworkClient.getInstance().addToRequestQueue(context, request);
    }

    //region URL

    /**
     * @return The URL to fetch demands.
     */
    private String getDemandsUrl() {
        return logUrl(URL_DEMANDS);
    }

    /**
     * Logs the url passed as parameter.
     *
     * @param url An URL.
     * @return The URL passed as parameter.
     */
    private String logUrl(String url) {
        Log.d(TAG, "URL=" + url);
        return url;
    }
    //endregion
}
