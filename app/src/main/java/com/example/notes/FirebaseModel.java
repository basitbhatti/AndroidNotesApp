package com.example.notes;

public class FirebaseModel {

    String title, note;

    public FirebaseModel(String title, String note) {
        this.title = title;
        this.note = note;
    }

    public FirebaseModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
