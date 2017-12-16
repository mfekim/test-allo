package com.mfekim.testallo.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Regroups IO utils methods.
 */
public class AVIOUtils {
    /** Tag for logs. */
    private static final String TAG = AVIOUtils.class.getSimpleName();

    /**
     * @param reader A reader.
     * @return The content of the object passed as parameter in string format.
     */
    public static String getStringContent(Reader reader) {
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            String line;
            bufferedReader = new BufferedReader(reader);
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            Log.e(AVIOUtils.class.getSimpleName(),
                    "Error during IO operation " + e.getLocalizedMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.e(AVIOUtils.class.getSimpleName(),
                            "Error during closing the stream " + e.getLocalizedMessage());
                }
            }
        }

        return result.toString();
    }
}
