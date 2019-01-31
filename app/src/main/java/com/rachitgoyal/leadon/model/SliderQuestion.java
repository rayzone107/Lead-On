package com.rachitgoyal.leadon.model;

/**
 * Created by Rachit Goyal on 30/01/19.
 */
public class SliderQuestion extends Question {

    private int start;
    private int end;
    private int chunkSize;

    public SliderQuestion() {
    }

    public SliderQuestion(String title, String description, String imageURL) {
        super(title, description, imageURL);
    }

    public SliderQuestion(String title, String description, String imageURL, int start, int end, int chunkSize) {
        super(title, description, imageURL);
        this.start = start;
        this.end = end;
        this.chunkSize = chunkSize;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }
}
