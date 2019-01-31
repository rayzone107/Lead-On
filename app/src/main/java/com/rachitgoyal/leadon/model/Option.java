package com.rachitgoyal.leadon.model;

/**
 * Created by Rachit Goyal on 30/01/19.
 */
public class Option {

    private int position;
    private String title;
    private String description;  // Optional
    private String imageURL; // Optional
    private boolean isSelected;

    public Option(String title, String description, String imageURL) {
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
    }

    public Option(int position, String title, String description, String imageURL, boolean isSelected) {
        this.position = position;
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.isSelected = isSelected;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
