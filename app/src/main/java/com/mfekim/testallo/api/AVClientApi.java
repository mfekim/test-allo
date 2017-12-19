package com.mfekim.testallo.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mfekim.testallo.api.request.AVGsonRequest;
import com.mfekim.testallo.data.model.config.AVConfigResponse;
import com.mfekim.testallo.data.model.demand.AVDemandResponse;
import com.mfekim.testallo.network.AVNetworkClient;

/**
 * Client API.
 */
public class AVClientApi {
    /** Tag for logs. */
    private static final String TAG = AVClientApi.class.getSimpleName();

    /** Host. */
    private static String HOST = "https://firebasestorage.googleapis.com/";

    /* Base path. */
    private static String BASE_PATH = "v0/b/test-allo.appspot.com/o/";

    /** Filenames. */
    private static String CONFIG_FILENAME = "indexes.json";
    private static String DEMAND_FILENAME = "service.json";

    /** Tokens. */
    private static String CONFIG_TOKEN = "e11baacb-d377-4bf2-8b03-151e875dbcad";
    private static String DEMAND_TOKEN = "79528d72-b75b-44d4-b293-33b275fab3e4";

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

    //region API

    /**
     * Fetches config.
     *
     * @param context       Context.
     * @param listener      Success listener.
     * @param errorListener Error listener.
     * @param requestTag    Request tag.
     */
    public void fetchConfig(Context context, final Response.Listener<AVConfigResponse> listener,
                            final Response.ErrorListener errorListener, String requestTag) {
        AVGsonRequest request = new AVGsonRequest<>(getConfigUrl(), AVConfigResponse.class, null,
                new Response.Listener<AVConfigResponse>() {
                    @Override
                    public void onResponse(AVConfigResponse response) {
                        Log.d(TAG, "Fetching config succeeded");
                        if (listener != null) {
                            listener.onResponse(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Fetching config failed - " + error.getLocalizedMessage());
                        if (errorListener != null) {
                            errorListener.onErrorResponse(error);
                        }
                    }
                });
        request.setTag(requestTag);
        AVNetworkClient.getInstance().addToRequestQueue(context, request);
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
    //endregion

    //region URL

    /**
     * @return The URL to fetch config.
     */
    private String getConfigUrl() {
        String url = HOST + BASE_PATH + CONFIG_FILENAME;
        url = addParameters(url, "media", CONFIG_TOKEN);
        return logUrl(url);
    }

    /**
     * @return The URL to fetch demands.
     */
    private String getDemandsUrl() {
        String url = HOST + BASE_PATH + DEMAND_FILENAME;
        url = addParameters(url, "media", DEMAND_TOKEN);
        return logUrl(url);
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

    /**
     * Adds parameters to an URL.
     *
     * @param url   URL.
     * @param alt   Alt.
     * @param token Token.
     * @return The URL passed as parameter with parameters.
     */
    private String addParameters(String url, String alt, String token) {
        if (!TextUtils.isEmpty(url)) {
            String params = "";
            // Alt
            if (!TextUtils.isEmpty(alt)) {
                params += "?alt=" + alt;
            }
            // Token
            if (!TextUtils.isEmpty(token)) {
                params += TextUtils.isEmpty(params) ? "?" : "&";
                params += "token=" + token;
            }

            url += params;
        }

        return url;
    }
    //endregion
}
