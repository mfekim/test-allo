package com.mfekim.testallo.demand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.mfekim.testallo.R;
import com.mfekim.testallo.base.AVBaseActivity;

/**
 * Shows a list of demands.
 */
public class AVDemandListActivity extends AVBaseActivity {
    /** Tag for logs. */
    private static final String TAG = AVDemandListActivity.class.getSimpleName();

    /** Fragment Tag. */
    private static final String FRAGMENT_TAG = "demand_list_fragment_tag";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_activity_list_demand);

        // Set action bar title
        setTitle(R.string.av_activity_demand_title);

        // Add fragment
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.av_activity_list_demand_fragment_container,
                            AVDemandListFragment.newInstance(), FRAGMENT_TAG)
                    .commit();
        }
    }
}
