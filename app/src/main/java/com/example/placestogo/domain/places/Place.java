package com.example.placestogo.domain.places;

import android.location.Location;

import java.io.Serializable;

public class Place implements Serializable {
    private String name;
    private String photo;
    private String id;
    private Double lat;
    private Double lng;

    public Place(String id, String name, String photo, Double lat, Double lng) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        Location location = new Location("");
        location.setLatitude(this.lat);
        location.setLongitude(this.lng);

        return location;
    }

    public String getPhoto() {
        return photo;
    }
}
