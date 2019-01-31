package com.rachitgoyal.leadon.model;

/**
 * Created by Rachit Goyal on 30/01/19.
 */
public class Question {

    private String title;
    private String description; // Optional
    private String imageURL; // Optional

    public Question() {
    }

    public Question(String title, String description, String imageURL) {
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
