package com.boostcamp.dreampicker.presentation.feed.voted;

import com.boostcamp.dreampicker.data.local.room.entity.VotedFeed;
import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class VotedViewModel extends BaseViewModel {
    private static final int PAGE_SIZE = 5;

    @NonNull
    private final LiveData<PagedList<VotedFeed>> votedFeedList;

    @Inject
    VotedViewModel(@NonNull final FeedRepository repository) {
        this.votedFeedList =
                new LivePagedListBuilder<>(repository.getMyVotedFeedList(), PAGE_SIZE).build();
    }

    @NonNull
    public LiveData<PagedList<VotedFeed>> getVotedFeedList() {
        return votedFeedList;
    }
}
