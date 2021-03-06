package com.boostcamp.dreampicker.presentation.feed.main;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentFeedBinding;
import com.boostcamp.dreampicker.di.scope.UserId;
import com.boostcamp.dreampicker.presentation.BaseFragment;
import com.boostcamp.dreampicker.presentation.feed.detail.FeedDetailActivity;
import com.boostcamp.dreampicker.presentation.profile.ProfileActivity;
import com.boostcamp.dreampicker.utils.NetworkUtil;
import com.tedpark.tedonactivityresult.rx2.TedRxOnActivityResult;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import static android.app.Activity.RESULT_OK;

public class FeedFragment extends BaseFragment<FragmentFeedBinding> {

    private boolean isLastPage = false;

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    FeedViewModelFactory factory;
    @Inject
    Context context;
    @Inject
    @UserId
    String userId;

    @Inject
    public FeedFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initViews();
        subscribeViewModel();
    }

    private void initViewModel() {
        binding.setVm(ViewModelProviders.of(this, factory).get(FeedViewModel.class));
        binding.getVm().init(userId);
        binding.getVm().loadFeedList(userId);
    }

    private void initViews() {
        initRecyclerView();
        initSwipeRefreshLayout();
    }

    private void initRecyclerView() {
        final FeedAdapter adapter = new FeedAdapter(
                binding.getVm()::vote,
                this::startFeedDetailActivity,
                this::startProfileActivity);

        binding.rvFeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!binding.rvFeed.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                    if (NetworkUtil.isNetworkConnected(context)) {
                        if (isLastPage) {
                            showToast(getString(R.string.feed_last_page));
                        } else {
                            binding.getVm().loadFeedList(userId);
                        }
                    } else {
                        showToast(getString(R.string.network_connection_state_notification));
                    }

                }
            }
        });
        binding.rvFeed.setItemAnimator(null);
        binding.rvFeed.setAdapter(adapter);
    }

    private void initSwipeRefreshLayout() {
        binding.swipe.setOnRefreshListener(() -> {
            if (NetworkUtil.isNetworkConnected(context)) {
                binding.getVm().refresh(userId);
                binding.swipe.setRefreshing(false);
            } else {
                showToast(getString(R.string.network_connection_state_notification));
            }

        });
    }

    private void subscribeViewModel() {
        binding.getVm().getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.loading.setVisibility(View.VISIBLE);
            } else {
                binding.loading.setVisibility(View.GONE);
            }
        });

        binding.getVm().getIsLastPage().observe(this, isLastPage -> this.isLastPage = isLastPage);

        binding.getVm().getError().observe(this, e -> showToast(getString(R.string.feed_error_message)));
    }

    private void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed;
    }

    private void startFeedDetailActivity(@NonNull final String feedId,
                                         @NonNull final String imageUrlA,
                                         @NonNull final String imageUrlB) {
        disposable.add(TedRxOnActivityResult.with(getContext())
                .startActivityForResult(
                        FeedDetailActivity.getLaunchIntent(context, feedId, imageUrlA, imageUrlB))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        binding.getVm().getFeed(userId, feedId);
                    }
                }));
    }

    private void startProfileActivity(@NonNull final String writer) {
        startActivity(ProfileActivity.getLaunchIntent(context, writer));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
