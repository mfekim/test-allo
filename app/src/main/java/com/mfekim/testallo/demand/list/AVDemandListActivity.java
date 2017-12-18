package com.mfekim.testallo.demand.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.mfekim.testallo.R;
import com.mfekim.testallo.base.AVBaseActivity;
import com.mfekim.testallo.utils.AVGooglePlayServicesUtils;

/**
 * Shows a list of demands.
 */
public class AVDemandListActivity extends AVBaseActivity {
    /** Tag for logs. */
    private static final String TAG = AVDemandListActivity.class.getSimpleName();

    /** Fragment Tag. */
    private static final String FRAGMENT_TAG = "demand_list_fragment_tag";

    /** Demand list fragment. */
    private Fragment mDemandListFragment;

    /**
     * Launches the activity.
     *
     * @param context Context.
     */
    public static void launchActivity(Context context) {
        Intent intent = new Intent(context, AVDemandListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_activity_list_demand);

        // Set action bar title
        setTitle(R.string.av_activity_demand_list_title);

        // Add fragment
        mDemandListFragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (mDemandListFragment == null) {
            mDemandListFragment = AVDemandListFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.av_activity_list_demand_fragment_container, mDemandListFragment,
                            FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        AVGooglePlayServicesUtils.isGooglePlayServicesAvailable(this);
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mDemandListFragment != null) {
            mDemandListFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
