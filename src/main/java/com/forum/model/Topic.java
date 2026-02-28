package com.forum.model;

import java.sql.Timestamp;

public class Topic {
    private long id;
    private int category_id;
    private long user_id;
    private String title;
    private String content;
    private Timestamp created_at;
    
    // Join fields
    private String authorName;
    private String categoryName;
    private int postCount;

    public Topic() {}

    public Topic(long id, int category_id, long user_id, String title, String content, Timestamp created_at, 
                 String authorName, String categoryName, int postCount) {
        this.id = id;
        this.category_id = category_id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.created_at = created_at;
        this.authorName = authorName;
        this.categoryName = categoryName;
        this.postCount = postCount;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public int getCategory_id() { return category_id; }
    public void setCategory_id(int category_id) { this.category_id = category_id; }

    public long getUser_id() { return user_id; }
    public void setUser_id(long user_id) { this.user_id = user_id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Timestamp getCreated_at() { return created_at; }
    public void setCreated_at(Timestamp created_at) { this.created_at = created_at; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public int getPostCount() { return postCount; }
    public void setPostCount(int postCount) { this.postCount = postCount; }
}
