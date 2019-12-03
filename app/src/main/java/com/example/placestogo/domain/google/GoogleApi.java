package com.example.placestogo.domain.google;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.placestogo.MainActivity;
import com.example.placestogo.R;
import com.example.placestogo.domain.GPS;
import com.example.placestogo.domain.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GoogleApi {
    private MainActivity context;
    RequestQueue queue;

    public GoogleApi(Context context) {
        this.context = (MainActivity) context;
        this.queue = Volley.newRequestQueue(this.context);
    }

    public void fetchPlaces() {
        final List<Place> places = new ArrayList<>();
        GPS gps = new GPS(this.context);

        //Show location in toast if not null
        if (gps.getLocation() != null) {
            String url ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+ gps.getLocation().toString() +"&radius=15000&key=" + this.context.getString(R.string.google_api_key);
            Log.d("Api", url);

            // Request a string response from the provided URL.
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray results = response.getJSONArray("results");
                                for (int i = 0; i < results.length(); i++) {
                                    JSONObject object = results.getJSONObject(i);
                                    JSONArray photos = object.getJSONArray("photos");

                                    places.add(new Place(
                                        object.getString("id"),
                                        object.getString("name"),
                                        photos.getJSONObject(0).getString("photo_reference")
                                    ));
                                }

                                context.getAdapter().setPlaces(places);
                                context.getAdapter().notifyDataSetChanged();
                            } catch (JSONException e) {
                                Log.d("Api", e.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Api", error.toString());
                        }
                }
            );

            // Add the request to the RequestQueue.
            this.queue.add(jsonRequest);
        }
    }
}
