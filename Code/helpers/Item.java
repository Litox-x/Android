package com.example.bstufinderv2.helpers;

import java.util.Date;
import java.util.List;

public class Item {
    private String id;
    private String name;
    private String description;
    private byte[] image;
    private String place;
    private String date;
    private String founder_id;

    public Item(String id, String name, String description, byte[] image, String place, String date, String founder_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.place = place;
        this.date = date;
        this.founder_id = founder_id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public String getPlace() {
        return place;
    }

    public String getDate() {
        return date;
    }

    public String getFounder_id() {
        return founder_id;
    }

    public byte[] getImage() {
        return image;
    }
}
