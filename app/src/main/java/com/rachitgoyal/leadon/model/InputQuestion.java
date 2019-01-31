package com.rachitgoyal.leadon.model;

/**
 * Created by Rachit Goyal on 30/01/19.
 */
public class InputQuestion extends Question {

    private String hint;
    private int inputType;

    public InputQuestion() {
    }

    public InputQuestion(String hint, int inputType) {
        this.hint = hint;
        this.inputType = inputType;
    }

    public InputQuestion(String title, String description, String imageURL, String hint, int inputType) {
        super(title, description, imageURL);
        this.hint = hint;
        this.inputType = inputType;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }
}
