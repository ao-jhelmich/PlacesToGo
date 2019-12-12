package com.example.placestogo.domain.places;

import android.content.Context;

import com.example.placestogo.domain.google.GoogleApi;

import java.util.ArrayList;
import java.util.List;

public class PlaceRepository {
    private List<Place> places = new ArrayList<Place>();
    private GoogleApi googleApi;

    public PlaceRepository(Context context) {
         googleApi = new GoogleApi(context);
    }

    public List<Place> getPlaces() {
        if (places.isEmpty()) {
            loadPlaces();
        }

        return places;
    }

    public void loadPlaces() {
        googleApi.fetchPlaces();
    }

    public Place getByPos(int pos) {
        return places.get(pos);
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}
