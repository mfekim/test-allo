package com.mfekim.testallo;

import android.app.Application;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;

/**
 * Application class.
 */
public class AVApplication extends Application {
    /** Tag for logs. */
    private static final String TAG = AVApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        //region Init Picasso (needed to work with HTTPS)
        OkHttpClient client = new OkHttpClient.Builder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();

        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(client))
                .build();

        Picasso.setSingletonInstance(picasso);
        //endregion
    }
}
