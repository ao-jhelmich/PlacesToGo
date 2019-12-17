package com.example.placestogo.domain.google;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.placestogo.MainActivity;
import com.example.placestogo.R;
import com.example.placestogo.domain.places.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GoogleApi {
    private MainActivity context;
    private RequestQueue queue;

    public GoogleApi(Context context) {
        this.context = (MainActivity) context;
        this.queue = Volley.newRequestQueue(this.context);
    }

    public void fetchPlaces(Location location) {
        final List<Place> places = new ArrayList<>();

        //Show location in toast if not null
        if (location != null) {
            String url ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+ location.getLatitude() + "," + location.getLongitude() +"&radius=1500&key=" + this.context.getString(R.string.google_api_key);
            Log.d("Api", url);

            // Request a string response from the provided URL.
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject object = results.getJSONObject(i);
                                String photo_reference = "";

                                try {
                                    JSONArray photos = object.getJSONArray("photos");
                                    photo_reference = photos.getJSONObject(0).getString("photo_reference");
                                } catch (JSONException e) {
                                    Log.d("Api", e.getMessage());
                                }

                                Double lat = 0.0;
                                Double lng = 0.0;

                                try {
                                    JSONObject locObj = object.getJSONObject("geometry").getJSONObject("location");

                                    lat = locObj.getDouble("lat");
                                    lng = locObj.getDouble("lng");

                                } catch (JSONException e) {
                                    Log.d("Api", e.getMessage());
                                }

                                places.add(new Place(
                                    object.getString("id"),
                                    object.getString("name"),
                                    photo_reference,
                                    lat,
                                    lng
                                ));
                            }

                            this.context.getRepository().setPlaces(places);
                            this.context.getAdapter().setPlaces(places);
                            this.context.getAdapter().notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.d("Api", e.toString());
                        }
                    }, error -> Log.d("Api", error.toString())
            );

            // Add the request to the RequestQueue.
            this.queue.add(jsonRequest);
        }
    }
}
