package com.mfekim.testallo.demand.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfekim.testallo.R;
import com.mfekim.testallo.base.AVBaseFragment;
import com.mfekim.testallo.data.model.demand.AVDemand;
import com.mfekim.testallo.utils.AVPicassoUtils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Shows the demand detail.
 */
public class AVDemandDetailFragment extends AVBaseFragment {
    /** Arguments. */
    public static final String ARG_DEMAND = "arg_demand";

    /** Tag for logs. */
    private static final String TAG = AVDemandDetailFragment.class.getSimpleName();

    /** Format to use to display date. */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);

    /** A demand. */
    private AVDemand mDemand;

    /**
     * @param demand A demand.
     * @return A new instance of {@link AVDemandDetailFragment}.
     */
    public static AVDemandDetailFragment newInstance(AVDemand demand) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_DEMAND, demand);

        AVDemandDetailFragment fragment = new AVDemandDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_DEMAND)) {
            mDemand = args.getParcelable(ARG_DEMAND);
        } else {
            Log.e(TAG, "No demand found");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.av_fragment_detail_demand, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mDemand != null) {
            // Date
            TextView tvDate = view.findViewById(R.id.av_fragment_detail_demand_date);
            Date publishDate = mDemand.getPublishDate();
            if (publishDate != null) {
                tvDate.setText(String.format(getString(R.string.av_published_at),
                        DATE_FORMAT.format(publishDate)));
                tvDate.setVisibility(View.VISIBLE);
            } else {
                tvDate.setVisibility(View.GONE);
            }

            // Description
            TextView tvDescription = view.findViewById(R.id.av_fragment_detail_demand_description);
            tvDescription.setMovementMethod(new ScrollingMovementMethod());
            String description = mDemand.getDescription();
            tvDescription.setText(TextUtils.isEmpty(description) ?
                    getString(R.string.av_demand_detail_no_description_message) : description);

            // Answer count
            TextView tvAnswerCount = view.findViewById(R.id.av_fragment_detail_demand_answer_count);
            int answerCount = mDemand.getAnswerCount();
            if (answerCount > 0) {
                tvAnswerCount.setText(getResources()
                        .getQuantityString(R.plurals.av_answer_count_pattern, answerCount,
                                answerCount));
                tvAnswerCount.setVisibility(View.VISIBLE);
            } else {
                tvAnswerCount.setVisibility(View.GONE);
            }

            // Thumbnail
            ImageView imgThumbnail = view.findViewById(R.id.av_fragment_detail_demand_thumbnail);
            Picasso.with(getActivity())
                   .load(mDemand.getThumbnailUrl())
                   .error(R.drawable.ic_placeholder_user)
                   .placeholder(R.drawable.ic_placeholder_user)
                   .transform(AVPicassoUtils.getCircleTransformation())
                   .into(imgThumbnail);

            // Name
            TextView tvName = view.findViewById(R.id.av_fragment_detail_demand_name);
            String name = mDemand.getFirstName();
            if (TextUtils.isEmpty(name)) {
                tvName.setVisibility(View.GONE);
            } else {
                tvName.setText(name);
                tvName.setVisibility(View.VISIBLE);
            }

            // Distance TODO
            TextView tvDistance = view.findViewById(R.id.av_fragment_detail_demand_distance);
            tvDistance.setVisibility(View.GONE);
        } else {
            // TODO show an error page
        }
    }
}
