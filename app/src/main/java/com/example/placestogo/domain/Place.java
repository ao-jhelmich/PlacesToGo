package com.example.placestogo.domain;

public class Place {
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
}
