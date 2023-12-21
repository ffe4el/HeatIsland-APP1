package com.example.projec7;

public class Post {

    public int id;
    private String title;
    private String content;
    private String localCategory;
    private String themeCategory;
    private String imageData;

    public Post(String title, String content, String localCategory, String themeCategory, String imageData) {
        this.title = title;
        this.content = content;
        this.localCategory = localCategory;
        this.themeCategory = themeCategory;
        this.imageData = imageData;
    }
    public Post(){};

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocalCategory() {
        return localCategory;
    }

    public void setLocalCategory(String localCategory) {
        this.localCategory = localCategory;
    }

    public String getThemeCategory() {
        return themeCategory;
    }

    public void setThemeCategory(String themeCategory) {
        this.themeCategory = themeCategory;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}