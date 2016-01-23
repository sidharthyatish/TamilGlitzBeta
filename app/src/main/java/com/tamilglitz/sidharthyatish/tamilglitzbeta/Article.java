package com.tamilglitz.sidharthyatish.tamilglitzbeta;

/**
 * Created by Sidharth Yatish on 11-12-2015.
 */
public class Article {
    private String title;
    private String thumbUrl;
    private String date;
    private String url;
    private String content;
    private String author;
    private int id;


    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
