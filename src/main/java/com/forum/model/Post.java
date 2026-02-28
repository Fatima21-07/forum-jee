package com.forum.model;

import java.sql.Timestamp;

public class Post {
    private long id;
    private long topic_id;
    private long user_id;
    private String content;
    private Timestamp created_at;
    
    // Join fields
    private String authorName;

    public Post() {}

    public Post(long id, long topic_id, long user_id, String content, Timestamp created_at, String authorName) {
        this.id = id;
        this.topic_id = topic_id;
        this.user_id = user_id;
        this.content = content;
        this.created_at = created_at;
        this.authorName = authorName;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getTopic_id() { return topic_id; }
    public void setTopic_id(long topic_id) { this.topic_id = topic_id; }

    public long getUser_id() { return user_id; }
    public void setUser_id(long user_id) { this.user_id = user_id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Timestamp getCreated_at() { return created_at; }
    public void setCreated_at(Timestamp created_at) { this.created_at = created_at; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
}
