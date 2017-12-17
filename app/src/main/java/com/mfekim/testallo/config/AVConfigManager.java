package com.mfekim.testallo.config;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.mfekim.testallo.data.AVDataManager;
import com.mfekim.testallo.data.model.config.AVCategory;
import com.mfekim.testallo.data.model.config.AVConfigResponseResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages configuration data.
 */
public class AVConfigManager {
    /** Tag for logs. */
    private static final String TAG = AVConfigManager.class.getSimpleName();

    /** The name of the file which contains the last retrieved configuration data. */
    private static final String CONFIG_FILENAME = "configuration.json";

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

    /** Configuration data. */
    private AVConfigResponseResult mConfig;

    /** List of categories (id => object). */
    private Map<String, AVCategory> mMapCategory;

    /**
     * Checks if there is a config file in the internal memory.
     *
     * @param context Context.
     * @return true if there is a config file, false otherwise.
     */
    public boolean isConfigExist(Context context) {
        return mConfig != null || AVDataManager.getInstance().isDataExist(context, CONFIG_FILENAME);
    }

    /**
     * Saves config asynchronously in internal memory.
     *
     * @param context Context.
     * @param config  Config data.
     */
    public void saveConfigInBackground(Context context, AVConfigResponseResult config) {
        if (context != null && config != null) {
            AVDataManager.getInstance().saveDataInBackground(context, config, CONFIG_FILENAME);
            setConfig(config);
        } else {
            Log.e(TAG, "Saving config failed");
        }
    }

    /**
     * Gets a category with its id.
     *
     * @param context    Context.
     * @param categoryId Category id.
     * @return A category, null otherwise.
     */
    public AVCategory getCategory(Context context, String categoryId) {
        getConfig(context);
        return mMapCategory != null && !TextUtils.isEmpty(categoryId) ?
                mMapCategory.get(categoryId) : null;
    }

    /**
     * Builds a map of categories (id => object).
     *
     * @param config Configuration data.
     * @return A map of categories (id => object), null otherwise.
     */
    private Map<String, AVCategory> buildMapCategory(AVConfigResponseResult config) {
        Map<String, AVCategory> mapCategory = null;
        if (config != null) {
            List<AVCategory> categories = config.getCategories();
            if (categories != null) {
                mapCategory = new HashMap<>(categories.size());
                for (AVCategory category : categories) {
                    mapCategory.put(category.getId(), category);
                }
            }
        } else {
            Log.e(TAG, "Build map category failed - config null");
        }

        return mapCategory;
    }

    /**
     * @return the configuration saved into the app's internal memory, null otherwise.
     */
    private AVConfigResponseResult getConfig(Context context) {
        if (mConfig == null) {
            mConfig = AVDataManager.getInstance().getData(context, CONFIG_FILENAME,
                    new TypeToken<AVConfigResponseResult>() {
                    });
            setConfig(mConfig);
        }
        if (mConfig == null) {
            Log.e(TAG, "Getting config failed");
        }
        return mConfig;
    }

    /**
     * Sets the configuration data.
     *
     * @param config Configuration data.
     */
    private void setConfig(AVConfigResponseResult config) {
        mConfig = config;
        mMapCategory = buildMapCategory(config);
    }
}
