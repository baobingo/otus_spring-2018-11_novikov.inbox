package ru.otus.spring13.domain;

public class Review {

    private int reviewId;
    private String author;
    private String body;

    public Review(int reviewId, String author, String body) {
        this.reviewId = reviewId;
        this.author = author;
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public String toString() {
        return "[ID] " + getReviewId()
                + " [AUTHOR] " + getAuthor()
                + " [REVIEW] " + getBody() +"\n";
    }
}
