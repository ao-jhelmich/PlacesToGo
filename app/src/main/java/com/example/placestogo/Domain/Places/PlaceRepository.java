package com.example.placestogo.Domain.Places;

import com.example.placestogo.Domain.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceRepository {
    private List<Place> places = new ArrayList<Place>();

    public PlaceRepository() {}

    public List<Place> getPlaces() {
        if (places.isEmpty()) {
            loadPlaces();
        }

        return places;
    }

    private void loadPlaces() {
        for (int i = 0; i < 100; i++) {
            places.add(new Place("Kerk " + i, 1));
        }
    }

    public Place getByPos(int pos) {
        return places.get(pos);
    }
}
