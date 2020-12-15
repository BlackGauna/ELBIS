package model;

import java.util.Date;

public class Article {

    private int id;
    private String title;
    private int topic;
    private String content;
    private String expireDate;
    private String creationDate;
    private String publisherComment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getPublisherComment() {
        return publisherComment;
    }

    public void setPublisherComment(String publisherComment) {
        this.publisherComment = publisherComment;
    }

    public int getTopic() {
        return topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }
}
