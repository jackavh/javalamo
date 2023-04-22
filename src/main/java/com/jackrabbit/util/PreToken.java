package com.jackrabbit.util;

public class PreToken {
    private String text;
    private int start;
    private int end;

    public PreToken() {
        this.text = "";
        this.start = -1;
        this.end = -1;
    }

    public PreToken(String text, int start, int end) {
        this.text = text;
        this.start = start;
        this.end = end;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return this.end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return String.format("['%s', (%d, %d)]", this.text, this.start, this.end);
    }
}
