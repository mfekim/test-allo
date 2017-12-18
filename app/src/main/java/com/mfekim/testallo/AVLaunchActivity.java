package com.mfekim.testallo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mfekim.testallo.api.AVClientApi;
import com.mfekim.testallo.base.AVBaseActivity;
import com.mfekim.testallo.configuration.AVConfigManager;
import com.mfekim.testallo.data.model.config.AVConfigResponse;
import com.mfekim.testallo.demand.list.AVDemandListActivity;
import com.mfekim.testallo.network.AVNetworkClient;
import com.mfekim.testallo.utils.AVConnectivityUtils;

/**
 * Launch screen.
 */
public class AVLaunchActivity extends AVBaseActivity {
    /** Tag for logs. */
    private static final String TAG = AVLaunchActivity.class.getSimpleName();

    /** Minimum display time in milliseconds. */
    private static final int MINIMUM_DISPLAY_TIME_MILLIS = 1000;

    /** Used to display the next activity. */
    private Handler mHandler = new Handler();

    /** Task to start the next activity. */
    private Runnable mNextActivityStartingTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_activity_launch);

        mNextActivityStartingTask = new Runnable() {
            @Override
            public void run() {
                AVDemandListActivity.launchActivity(AVLaunchActivity.this);
                finish();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkConfig();
    }

    @Override
    protected void onPause() {
        AVNetworkClient.getInstance()
                       .cancelAllRequest(getApplicationContext(), String.valueOf(hashCode()));
        mHandler.removeCallbacks(mNextActivityStartingTask);
        super.onPause();
    }

    /**
     * Checks if we already fetched the config or not.
     */
    private void checkConfig() {
        final long startMillis = System.currentTimeMillis();
        if (AVConfigManager.getInstance().isConfigExist(this)) {
            handleFetchConfigEnd(startMillis);
        } else {
            if (AVConnectivityUtils.isConnected(this)) {
                AVClientApi.getInstance().fetchConfig(this,
                        new Response.Listener<AVConfigResponse>() {
                            @Override
                            public void onResponse(AVConfigResponse response) {
                                if (!response.hasError() && response.hasResult()) {
                                    AVConfigManager.getInstance()
                                                   .saveConfigInBackground(AVLaunchActivity.this,
                                                           response.getResult());
                                    handleFetchConfigEnd(startMillis);
                                } else {
                                    showAlertDialog(R.string.av_retrieve_data_failed_message);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                showAlertDialog(R.string.av_retrieve_data_failed_message);
                            }
                        }, String.valueOf(hashCode()));
            } else {
                showAlertDialog(R.string.av_no_internet_connection_first_launch_message);
            }
        }
    }

    /**
     * Handles the end of the fetch config.
     *
     * @param startTimeMillis Time in milliseconds when the config started to fetch.
     */
    private void handleFetchConfigEnd(long startTimeMillis) {
        long lastMillis = System.currentTimeMillis() - startTimeMillis;
        mHandler.postDelayed(mNextActivityStartingTask,
                lastMillis >= MINIMUM_DISPLAY_TIME_MILLIS ? 0 : MINIMUM_DISPLAY_TIME_MILLIS - lastMillis);
    }

    /**
     * Shows an alert dialog.
     *
     * @param messageId Id of the message to display.
     */
    private void showAlertDialog(final int messageId) {
        if (!isFinishing()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(AVLaunchActivity.this)
                            .setMessage(getString(messageId))
                            .setPositiveButton(R.string.av_retry,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            checkConfig();
                                        }
                                    })
                            .setNegativeButton(R.string.av_exit,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    })
                            .setCancelable(false)
                            .create()
                            .show();
                }
            });
        }
    }
}