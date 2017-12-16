package com.mfekim.testallo.config;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.mfekim.testallo.data.AVDataManager;
import com.mfekim.testallo.data.model.config.AVConfigResponseResult;

/**
 * Manages configuration data.
 */
public class AVConfigManager {
    /** Tag for logs. */
    private static final String TAG = AVConfigManager.class.getSimpleName();

    /** The name of the file which contains the last retrieved configuration data. */
    private static final String CONFIG_FILENAME = "indexes.json";

    /** Holder. */
    private static class SingletonHolder {
        /** Unique instance. */
        private final static AVConfigManager INSTANCE = new AVConfigManager();
    }

    /** Unique entry point to get the instance. */
    public static AVConfigManager getInstance() {
        return AVConfigManager.SingletonHolder.INSTANCE;
    }

    /** Private constructor. */
    private AVConfigManager() {
    }

    /** The configuration data. */
    private AVConfigResponseResult mConfig;

    /**
     * Checks if there is a config file in the internal memory.
     *
     * @param context Context.
     * @return true if there is a config file, false otherwise.
     */
    public boolean isConfigExist(Context context) {
        return AVDataManager.getInstance().isDataExist(context, CONFIG_FILENAME);
    }

    /**
     * Saves config asynchronously in internal memory.
     *
     * @param context Context.
     * @param config  Config data.
     */
    public void saveConfigInBackground(Context context, AVConfigResponseResult config) {
        if (context != null && config != null) {
            mConfig = config;
            AVDataManager.getInstance().saveDataInBackground(context, config, CONFIG_FILENAME);
        } else {
            Log.e(TAG, "Saving config failed");
        }
    }

    /**
     * @return the configuration saved into the app's internal memory, null otherwise.
     */
    private AVConfigResponseResult getConfig(Context context) {
        if (mConfig == null) {
            mConfig = AVDataManager.getInstance().getData(context, CONFIG_FILENAME,
                    new TypeToken<AVConfigResponseResult>() {
                    });
        }
        if (mConfig == null) {
            Log.e(TAG, "Getting config failed");
        }
        return mConfig;
    }
}
