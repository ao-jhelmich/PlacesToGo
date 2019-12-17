package com.example.placestogo.domain.places;

import android.location.Location;

import java.io.Serializable;


public class Place implements Serializable {
    private String name;
    private String photo;
    private String id;

    public Place(String id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return new Location("");
    }
}
