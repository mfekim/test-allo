package com.mfekim.testallo.demand.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mfekim.testallo.R;
import com.mfekim.testallo.api.AVClientApi;
import com.mfekim.testallo.base.AVBaseFragment;
import com.mfekim.testallo.config.AVConfigManager;
import com.mfekim.testallo.data.model.config.AVCategory;
import com.mfekim.testallo.data.model.demand.AVDemand;
import com.mfekim.testallo.data.model.demand.AVDemandResponse;
import com.mfekim.testallo.demand.detail.AVDemandDetailActivity;
import com.mfekim.testallo.network.AVNetworkClient;
import com.mfekim.testallo.utils.AVPicassoUtils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Shows a list of demands.
 */
public class AVDemandListFragment extends AVBaseFragment {
    /** Tag for logs. */
    private static final String TAG = AVDemandListFragment.class.getSimpleName();

    /** Request tag. */
    private static final String REQUEST_TAG = "demand_list_request_tag";

    /** The format to use to display time. */
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH'h'mm", Locale.FRENCH);

    /** Views. */
    private RecyclerView mRvList;
    private View mVLoader;
    private View mTvNoDataMsg;

    /** Adapter. */
    private RecyclerView.Adapter<AVDemandListAdapter.AVViewHolder> mAdapter;

    /** Layout Manager. */
    private RecyclerView.LayoutManager mLayoutManager;

    /** Demands. */
    private List<AVDemand> mDemands = new ArrayList<>();

    /**
     * @return A new instance of {@link AVDemandListFragment}.
     */
    public static AVDemandListFragment newInstance() {
        return new AVDemandListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.av_fragment_list_demand, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);

        // Loader
        mVLoader = view.findViewById(R.id.av_fragment_list_demand_loader);

        // No data msg
        mTvNoDataMsg = view.findViewById(R.id.av_fragment_list_demand_no_data_msg);

        // Recycler view
        mRvList = view.findViewById(R.id.av_fragment_list_demand_recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRvList.setLayoutManager(mLayoutManager);

        if (mDemands.isEmpty()) {
            fetch();
        } else {
            mRvList.setAdapter(mAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        AVNetworkClient.getInstance().cancelAllRequest(getContext(), REQUEST_TAG);
        super.onDestroyView();
    }

    //region Fetching

    /**
     * Fetches demands.
     */
    private void fetch() {
        onFetchStarting();
        AVClientApi.getInstance().fetchDemands(getContext(),
                new Response.Listener<AVDemandResponse>() {
                    @Override
                    public void onResponse(AVDemandResponse response) {
                        if (response.hasError()) {
                            onFetchError();
                        } else {
                            onFetchSucceeded(response.getResult());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onFetchError();
                    }
                }, REQUEST_TAG);
    }

    /**
     * Called when fetching demands is starting.
     */
    private void onFetchStarting() {
        Log.d(TAG, "Fetching demands is starting");

        mVLoader.setVisibility(mDemands != null && !mDemands.isEmpty() ?
                View.INVISIBLE : View.VISIBLE);
        mTvNoDataMsg.setVisibility(View.INVISIBLE);
    }

    /**
     * Called when fetching demands succeeded.
     */
    private void onFetchSucceeded(List<AVDemand> demands) {
        Log.d(TAG, "Fetching demands succeeded");

        mDemands.addAll(demands);
        mAdapter = new AVDemandListAdapter();
        mRvList.setAdapter(mAdapter);

        onFetchFinished();
    }

    /**
     * Called when fetching demands failed.
     */
    private void onFetchError() {
        Log.e(TAG, "Fetching demands failed");

        onFetchFinished();
    }

    /**
     * Called when fetching demands finished.
     */
    private void onFetchFinished() {
        mVLoader.setVisibility(View.INVISIBLE);
        mTvNoDataMsg.setVisibility(mDemands != null && !mDemands.isEmpty() ?
                View.INVISIBLE : View.VISIBLE);
    }
    //endregion

    /**
     * Manages demand list.
     */
    /*package*/ class AVDemandListAdapter extends
            RecyclerView.Adapter<AVDemandListAdapter.AVViewHolder> {
        /** Types. */
        private static final int VIEW_TYPE_ITEM_DEFAULT = 0;

        @Override
        public AVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AVViewHolderDefault(LayoutInflater
                    .from(getActivity())
                    .inflate(R.layout.av_item_list_demand_default, null));
        }

        @Override
        public int getItemViewType(int position) {
            return VIEW_TYPE_ITEM_DEFAULT;
        }

        @Override
        public void onBindViewHolder(AVViewHolder holder, int position) {
            int itemViewType = getItemViewType(position);

            if (itemViewType == VIEW_TYPE_ITEM_DEFAULT) {
                final AVViewHolderDefault viewHolderDefault = (AVViewHolderDefault) holder;
                AVDemand demand = mDemands.get(position);

                // Thumbnail
                Picasso.with(getActivity())
                       .load(demand.getThumbnailUrl())
                       .error(R.drawable.ic_placeholder_user)
                       .placeholder(R.drawable.ic_placeholder_user)
                       .transform(AVPicassoUtils.getCircleTransformation())
                       .into(viewHolderDefault.imgThumbnail);

                // Name
                String name = demand.getFirstName();
                if (TextUtils.isEmpty(name)) {
                    viewHolderDefault.tvName.setVisibility(View.GONE);
                } else {
                    viewHolderDefault.tvName.setText(name);
                    viewHolderDefault.tvName.setVisibility(View.VISIBLE);
                }

                // Distance TODO
                viewHolderDefault.tvDistance.setVisibility(View.GONE);

                // Category
                AVCategory category = AVConfigManager
                        .getInstance()
                        .getCategory(getContext(), demand.getCategoryId());
                String categoryName = category != null ? category.getName() : null;
                if (TextUtils.isEmpty(categoryName)) {
                    viewHolderDefault.tvCategory.setVisibility(View.GONE);
                } else {
                    viewHolderDefault.tvCategory.setText(categoryName);
                    viewHolderDefault.tvCategory.setVisibility(View.VISIBLE);
                }

                //region Time and place
                // Time
                String time = null;
                Date updateDate = demand.getUpdateDate();
                if (updateDate != null) {
                    time = TIME_FORMAT.format(updateDate);
                }
                // Place
                String place = demand.getCityName();
                // Result
                String timeAndPlace = null;
                if (!TextUtils.isEmpty(time)) {
                    timeAndPlace = time;
                }
                if (!TextUtils.isEmpty(place)) {
                    if (!TextUtils.isEmpty(timeAndPlace)) {
                        timeAndPlace += " - ";
                    }
                    timeAndPlace += place;
                }
                if (TextUtils.isEmpty(timeAndPlace)) {
                    viewHolderDefault.tvTimeAndPlace.setVisibility(View.GONE);
                } else {
                    viewHolderDefault.tvTimeAndPlace.setText(timeAndPlace);
                    viewHolderDefault.tvTimeAndPlace.setVisibility(View.VISIBLE);
                }
                //endregion

                // Type
                String categoryTypeString = null;
                String categoryType = demand.getCategoryType();
                if (!TextUtils.isEmpty(categoryType)) {
                    switch (categoryType) {
                        case AVDemand.OBJECT:
                            categoryTypeString = getString(R.string.av_object);
                            break;
                        case AVDemand.SERVICE:
                            categoryTypeString = getString(R.string.av_service);
                            break;
                    }
                }
                if (TextUtils.isEmpty(categoryTypeString)) {
                    viewHolderDefault.tvCategoryType.setVisibility(View.GONE);
                } else {
                    viewHolderDefault.tvCategoryType.setText(categoryTypeString);
                    viewHolderDefault.tvCategoryType.setVisibility(View.VISIBLE);
                }

                // Answer count
                int answerCount = demand.getAnswerCount();
                if (answerCount > 0) {
                    viewHolderDefault.tvAnswerCount.setText(getResources()
                            .getQuantityString(R.plurals.av_answer_count_pattern, answerCount,
                                    answerCount));
                    viewHolderDefault.tvAnswerCount.setVisibility(View.VISIBLE);
                } else {
                    viewHolderDefault.tvAnswerCount.setVisibility(View.GONE);
                }

                // Divider
                viewHolderDefault.vDivider.setVisibility(position == getItemCount() - 1 ?
                        View.GONE : View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return mDemands.size();
        }

        /**
         * View holder.
         */
        /*package*/ abstract class AVViewHolder extends RecyclerView.ViewHolder {
            /** {@inheritDoc} */
            public AVViewHolder(View itemView) {
                super(itemView);
            }
        }

        /**
         * View holder for default item.
         */
        /*package*/ class AVViewHolderDefault extends AVViewHolder {
            /* Views. */
            ImageView imgThumbnail;
            TextView tvName;
            TextView tvDistance;
            TextView tvCategory;
            TextView tvTimeAndPlace;
            TextView tvCategoryType;
            TextView tvAnswerCount;
            View vDivider;

            /** {@inheritDoc} */
            public AVViewHolderDefault(View itemView) {
                super(itemView);

                // Click
                itemView.findViewById(R.id.av_item_list_demand_default_main)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // TODO pass data
                                AVDemandDetailActivity.launchActivity(getActivity());
                            }
                        });

                // Thumbnail
                imgThumbnail = itemView.findViewById(R.id.av_item_list_demand_thumbnail);

                // Name
                tvName = itemView.findViewById(R.id.av_item_list_demand_name);

                // Distance
                tvDistance = itemView.findViewById(R.id.av_item_list_demand_distance);

                // Category
                tvCategory = itemView.findViewById(R.id.av_item_list_demand_category);

                // Time and Place
                tvTimeAndPlace = itemView.findViewById(R.id.av_item_list_demand_time_and_place);

                // Type
                tvCategoryType = itemView.findViewById(R.id.av_item_list_demand_category_type);

                // Answer count
                tvAnswerCount = itemView.findViewById(R.id.av_item_list_demand_answer_count);

                // Divider
                vDivider = itemView.findViewById(R.id.av_item_list_demand_default_divider);
            }
        }
    }
}
