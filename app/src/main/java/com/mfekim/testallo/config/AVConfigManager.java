package com.mfekim.testallo.config;

/**
 * Manages configuration data.
 */
public class AVConfigManager {
    /** Tag for logs. */
    private static final String TAG = AVConfigManager.class.getSimpleName();

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
}
