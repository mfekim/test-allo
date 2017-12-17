package com.mfekim.testallo.demand.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.mfekim.testallo.R;
import com.mfekim.testallo.base.AVBaseActivity;
import com.mfekim.testallo.data.model.demand.AVDemand;

/**
 * Shows the demand detail.
 */
public class AVDemandDetailActivity extends AVBaseActivity {
    /** Extras. */
    public static final String EXTRA_DEMAND = "demand";

    /** Tag for logs. */
    private static final String TAG = AVDemandDetailActivity.class.getSimpleName();

    /** Fragment Tag. */
    private static final String FRAGMENT_TAG = "demand_detail_fragment_tag";

    /**
     * Launches the activity.
     *
     * @param context Context.
     */
    public static void launchActivity(Context context, AVDemand demand) {
        Intent intent = new Intent(context, AVDemandDetailActivity.class);
        intent.putExtra(EXTRA_DEMAND, demand);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_activity_detail_demand);

        // Set action bar title
        setTitle(R.string.av_activity_demand_detail_title);

        // Show back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Retrieve passed data
        AVDemand demand = null;
        if (getIntent().hasExtra(EXTRA_DEMAND)) {
            demand = getIntent().getParcelableExtra(EXTRA_DEMAND);
        } else {
            Log.e(TAG, "No demand found");
        }

        // Add fragment
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.av_activity_detail_demand_fragment_container,
                            AVDemandDetailFragment.newInstance(demand), FRAGMENT_TAG)
                    .commit();
        }
    }
}
