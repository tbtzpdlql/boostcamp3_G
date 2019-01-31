package com.boostcamp.dreampicker.data.source.feed;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.model.Feed;
import com.boostcamp.dreampicker.model.Image;
import com.boostcamp.dreampicker.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;

public class FeedRepository implements FeedDataSource {
    private static FeedRepository instance;

    private FeedRemoteDataSource remoteDataSource;

    private FeedRepository() {
        remoteDataSource = FeedRemoteDataSource.getInstance();
    }

    public static FeedRepository getInstance() {
        if(instance == null) {
            synchronized (FeedRepository.class) {
                if(instance == null) {
                    instance = new FeedRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public Single<List<Feed>> getAllFeed() {
        List<Feed> feeds = new ArrayList<>();

        Image image1 = new Image("image-0-up", R.drawable.image1, "카페");
        Image image2 = new Image("image-0-down", R.drawable.image2, "술집");
        User user1 = new User("user-0", "박신혜", R.drawable.profile);

        Feed feed1 = new Feed(
                "feed-0",
                Arrays.asList(image1, image2),
                user1,
                "내일 소개남이랑 첫 데이트인데 장소 좀 골라주세요~!ㅎㅎ",
                "2018.01.28",
                false
        );

        Image image3 = new Image("image-1-up", R.drawable.jajang, "짜장면");
        Image image4 = new Image("image-1-down", R.drawable.jambong, "짬뽕");
        User user2 = new User("user-1", "공유", R.drawable.profile2);

        Feed feed2 = new Feed(
                "feed-0",
                Arrays.asList(image3, image4),
                user2,
                "짬뽕, 짜장면",
                "2019.01.29",
                false
        );

        feeds.add(feed1);
        feeds.add(feed2);
        feeds.add(feed1);

        // Todo : 이후 Firestore 연동 필요
        return Single.just(feeds);
    }

    @Override
    public Single<List<Feed>> searchAllFeed(String searchKey) {

        return remoteDataSource.searchAllFeed(searchKey);
    }
}
