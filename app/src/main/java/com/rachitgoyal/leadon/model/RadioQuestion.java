package com.rachitgoyal.leadon.model;

import java.util.List;

/**
 * Created by Rachit Goyal on 30/01/19.
 */
public class RadioQuestion extends Question {

    private List<Option> options;

    public RadioQuestion() {
    }

    public RadioQuestion(List<Option> options) {
        this.options = options;
    }

    public RadioQuestion(String title, String description, String imageURL, List<Option> options) {
        super(title, description, imageURL);
        this.options = options;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
