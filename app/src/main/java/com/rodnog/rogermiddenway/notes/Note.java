package com.rodnog.rogermiddenway.notes;

public class Note {
    private String text;
    private int id;

    public Note(String text, int id) {
        this.text = text;
        this.id = id;
    }
    public Note() {
    }

    public String getText() {
        return text;
    }
    public String getShortText() {
        int maxLength = 40;
        if(text.length() < maxLength) return text;
        else return text.substring(0, 60) + "...";
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
