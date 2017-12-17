package com.mfekim.testallo.data;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mfekim.testallo.utils.AVIOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Manages app data.
 */
public class AVDataManager {
    /** Tag for logs. */
    private static final String TAG = AVDataManager.class.getSimpleName();

    /** Unique Gson instance. */
    private static final Gson GSON = new Gson();

    /** Holder. */
    private static class SingletonHolder {
        /** Unique instance. */
        private final static AVDataManager INSTANCE = new AVDataManager();
    }

    /** Unique entry point to get the instance. */
    public static AVDataManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /** Private constructor. */
    private AVDataManager() {
    }

    /**
     * Saves data in background.
     *
     * @param context  Context.
     * @param data     Data to save.
     * @param fileName Filename where the data will be saved.
     */
    public void saveDataInBackground(final Context context, final Object data,
                                     final String fileName) {
        if (context != null && data != null && !TextUtils.isEmpty(fileName)) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                        fileOutputStream.write(new Gson().toJson(data).getBytes());
                    } catch (IOException e) {
                        Log.e(TAG, "Error during IO operation " + e.getLocalizedMessage());
                    } finally {
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e) {
                                Log.e(TAG, "Error during closing the stream " +
                                        e.getLocalizedMessage());
                            }
                        }
                    }
                }
            });
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();
        } else {
            Log.e(TAG, "Save data failed");
        }
    }

    /**
     * @param context   Context.
     * @param fileName  Filename where the data are saved.
     * @param typeToken Param used by {@link Gson}.
     * @param <T>       Data type.
     * @return the data stored into the file which has the name passed as parameter, null otherwise.
     */
    public <T> T getData(Context context, String fileName, TypeToken<T> typeToken) {
        if (context != null && !TextUtils.isEmpty(fileName) && typeToken != null) {
            FileInputStream outputStream = null;
            try {
                outputStream = context.openFileInput(fileName);
                String dataString = AVIOUtils.getStringContent(new InputStreamReader(outputStream));
                return GSON.fromJson(dataString, typeToken.getType());
            } catch (FileNotFoundException | JsonSyntaxException e) {
                Log.d(TAG, e.getLocalizedMessage() + "");
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        Log.d(TAG, "Error during closing the stream " + e.getLocalizedMessage());
                    }
                }
            }
        } else {
            Log.e(TAG, "Getting data failed");
        }

        return null;
    }

    /**
     * @param context      Context.
     * @param dataFilename Filename where the data are saved.
     * @return true if there is data, false otherwise.
     */
    public boolean isDataExist(Context context, String dataFilename) {
        if (context != null && !TextUtils.isEmpty(dataFilename)) {
            for (String file : context.fileList()) {
                if (file.equals(dataFilename)) {
                    return true;
                }
            }
        } else {
            Log.e(TAG, "Checking data failed");
        }
        return false;
    }
}
