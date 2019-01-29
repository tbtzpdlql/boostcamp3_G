package com.boostcamp.dreampicker.model;

import java.util.List;

public class Feed {
    private String feedId;
    private Image[] images;
    private User user;
    private String content;
    private String date;
    private int voteCount;

    // TODO : 마감 조건 확인 이후 작업
    private boolean isEnded;

    // TODO : Firestore 연동 이후 작업
    private List<String> upVotedUserIdList;
    private List<String> downVotedUserIdList;

    public Feed(String feedId, Image[] images, User user, String content, String date, boolean isEnded) {
        this.feedId = feedId;
        this.images = images;
        this.user = user;
        this.content = content;
        this.date = date;
        this.isEnded = isEnded;

        // Todo : Firebase 연동 이후 작업
        // voteCount = upVotedUserIdList.size() + downVotedUserIdList.size();
        voteCount = 100;
    }

    public String getId() {
        return feedId;
    }

    public void setId(String feedId) {
        this.feedId = feedId;
    }

    public Image[] getImages() {
        return images;
    }

    public void setImages(Image[] images) {
        this.images = images;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getVoteCount() {
        return voteCount;
    }
}
