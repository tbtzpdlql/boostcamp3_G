package com.boostcamp.dreampicker.presentation.feed.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityFeedDetailBinding;
import com.boostcamp.dreampicker.di.scope.UserId;
import com.boostcamp.dreampicker.presentation.BaseActivity;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class FeedDetailActivity extends BaseActivity<ActivityFeedDetailBinding> {

    private static final int NUM_PAGES = 2;

    private static final String EXTRA_FEED_ID = "EXTRA_FEED_ID";
    private static final String EXTRA_IMAGE_URL_A = "EXTRA_IMAGE_URL_A";
    private static final String EXTRA_IMAGE_URL_B = "EXTRA_IMAGE_URL_B";

    private String imageUrlA, imageUrlB;
    private String feedId;

    @Inject
    FeedDetailViewModelFactory factory;
    @Inject
    @UserId
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        feedId = intent.getStringExtra(EXTRA_FEED_ID);
        imageUrlA = intent.getStringExtra(EXTRA_IMAGE_URL_A);
        imageUrlB = intent.getStringExtra(EXTRA_IMAGE_URL_B);

        initViewModel();
        initViews();
        subscribeViewModel();
        loadFeedDetail();
    }

    private void initViews() {
        initToolbar();
        initViewPager(imageUrlA, imageUrlB);
        initVoteButton();
    }

    private void initViewModel() {
        final FeedDetailViewModel vm =
                ViewModelProviders.of(this, factory).get(FeedDetailViewModel.class);
        binding.setVm(vm);
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setHomeAsUpIndicator(R.drawable.btn_toolbar_close);
            toolbar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initVoteButton() {
        binding.btnFeedDetailVote.setOnClickListener(__ -> binding.getVm().vote(userId));
    }

    private void initViewPager(@NonNull String imageUrlA, @NonNull String imageUrlB) {
        final Fragment[] fragments = new Fragment[NUM_PAGES];
        fragments[0] = FeedDetailFragment.newInstance(imageUrlA);
        fragments[1] = FeedDetailFragment.newInstance(imageUrlB);
        final PagerAdapter pagerAdapter = new FeedDetailPagerAdapter(getSupportFragmentManager(), fragments);
        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.getVm().changePosition();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.pager.setAdapter(pagerAdapter);
    }

    private void subscribeViewModel() {
        binding.getVm().getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.loading.setVisibility(View.VISIBLE);
            } else {
                binding.loading.setVisibility(View.GONE);
            }
        });
    }

    private void loadFeedDetail() {
        binding.getVm().loadFeedDetail(userId, feedId);
    }

    private void closeActivity() {
        final Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public static Intent getLaunchIntent(@NonNull Context context,
                                         @NonNull String feedId,
                                         @NonNull String imageUrlA,
                                         @NonNull String imageUrlB) {

        final Intent intent = new Intent(context, FeedDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_FEED_ID, feedId);
        intent.putExtra(EXTRA_IMAGE_URL_A, imageUrlA);
        intent.putExtra(EXTRA_IMAGE_URL_B, imageUrlB);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_detail;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            closeActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }
}
