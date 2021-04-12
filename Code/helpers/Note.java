package com.example.bstufinderv2.helpers;

public class Note {

    private String title;
    private String desctiption;

    public Note(String title,String desctiption){
        this.setTitle(title);
        this.setDesctiption(desctiption);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesctiption() {
        return desctiption;
    }

    public void setDesctiption(String desctiption) {
        this.desctiption = desctiption;
    }
}
