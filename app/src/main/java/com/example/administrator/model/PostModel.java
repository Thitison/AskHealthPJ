package com.example.administrator.model;



public class PostModel {
    private String imgPath = "";
    private String post_id;
    private String description;
    private String category;
    private String date_time;
    private String user_id;
    private String id;
    private String name;
    private String token;
    private String likes;
    private boolean liked = false;

    public String getImgPath() {
        return imgPath;
    }

    public String getPost_id() {
        return post_id;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getDate_time() {
        return date_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
