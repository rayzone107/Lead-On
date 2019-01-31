package com.rachitgoyal.leadon.model;

/**
 * Created by Rachit Goyal on 31/01/19.
 */
public class Answer {

    private String title;
    private String answer;

    public Answer(String title, String answer) {
        this.title = title;
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
