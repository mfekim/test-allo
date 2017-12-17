package com.mfekim.testallo.demand.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mfekim.testallo.R;
import com.mfekim.testallo.base.AVBaseFragment;

/**
 * Shows the demand detail.
 */
public class AVDemandDetailFragment extends AVBaseFragment {
    /** Tag for logs. */
    private static final String TAG = AVDemandDetailFragment.class.getSimpleName();

    /**
     * @return A new instance of {@link AVDemandDetailFragment}.
     */
    public static AVDemandDetailFragment newInstance() {
        return new AVDemandDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.av_fragment_detail_demand, container, false);
    }
}
