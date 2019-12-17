package com.example.placestogo.domain.places;

import android.location.Location;

import java.io.Serializable;

public class Place implements Serializable {
    private String name;
    private String photo;
    private String id;
    private Location location;

    public Place(String id, String name, String photo, Double lat, Double lng) {
        this.id = id;
        this.name = name;
        this.photo = photo;

        this.location = new Location("");
        this.location.setLatitude(lat);
        this.location.setLongitude(lng);
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return this.location;
    }

    public String getPhoto() {
        return photo;
    }
}
