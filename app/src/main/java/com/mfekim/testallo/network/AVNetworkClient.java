package com.mfekim.testallo.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * It is the only entry point to execute a request. If you need to execute a request, you MUST use
 * this class by calling the {@link #addToRequestQueue(Context, Request)} method.
 */
public class AVNetworkClient {
    /** Tag for logs. */
    private static final String TAG = AVNetworkClient.class.getSimpleName();

    /** Holder. */
    private static class SingletonHolder {
        /** Unique instance. */
        private final static AVNetworkClient INSTANCE = new AVNetworkClient();
    }

    /** Unique entry point to get the instance. */
    public static AVNetworkClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /** Private constructor. */
    private AVNetworkClient() {
    }

    /** A Volley request queue. */
    private RequestQueue mRequestQueue;

    /**
     * Adds a request to the queue to be executed.
     *
     * @param context Context.
     * @param request Request to execute.
     */
    public void addToRequestQueue(Context context, Request request) {
        getRequestQueue(context).add(request);
    }

    /**
     * Cancels all requests which have the tag passed as parameter.
     *
     * @param context    Context.
     * @param requestTag Request tag which indicates which request to cancel.
     */
    public void cancelAllRequest(Context context, String requestTag) {
        if (!TextUtils.isEmpty(requestTag)) {
            Log.d(TAG, "Cancel requests with tag " + requestTag);
            getRequestQueue(context).cancelAll(requestTag);
        }
    }

    /**
     * @param context Context.
     * @return The Volley request queue.
     */
    private RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }
}
