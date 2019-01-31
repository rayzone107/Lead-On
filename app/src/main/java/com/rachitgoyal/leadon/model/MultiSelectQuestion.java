package com.rachitgoyal.leadon.model;

import java.util.List;

/**
 * Created by Rachit Goyal on 30/01/19.
 */
public class MultiSelectQuestion extends Question {

    private List<Option> options;

    public MultiSelectQuestion() {
    }

    public MultiSelectQuestion(List<Option> options) {
        this.options = options;
    }

    public MultiSelectQuestion(String title, String description, String imageURL, List<Option> options) {
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
