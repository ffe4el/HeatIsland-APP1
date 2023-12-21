package com.example.projec7;

public class Comment {
    private int id;
    private int postId;
    private String commentText;

    public Comment() {
        // Default constructor
    }

    public Comment( int postId, String commentText) {

        this.postId = postId;
        this.commentText = commentText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", postId=" + postId +
                ", commentText='" + commentText + '\'' +
                '}';
    }
}