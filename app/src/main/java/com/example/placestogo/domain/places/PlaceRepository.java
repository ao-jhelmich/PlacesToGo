package com.example.placestogo.domain.places;

import android.content.Context;
import android.location.Location;

import com.example.placestogo.domain.google.GoogleApi;

import java.util.ArrayList;
import java.util.List;

public class PlaceRepository {
    private List<Place> places = new ArrayList<Place>();
    private GoogleApi googleApi;

    public PlaceRepository(Context context) {
        googleApi = new GoogleApi(context);
    }

    public List<Place> getPlaces(Location location) {
        if (places.isEmpty()) {
            loadPlaces(location);
        }

        return places;
    }

    public void loadPlaces(Location location) {
        googleApi.fetchPlaces(location);
    }

    public Place getByPos(int pos) {
        return places.get(pos);
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}
