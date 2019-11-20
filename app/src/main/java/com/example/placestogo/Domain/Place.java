package com.example.placestogo.Domain;

public class Place {
    private String name;
    private int resourceId;

    Place(String name, int resourceId) {
        this.name = name;
        this.resourceId = resourceId;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
