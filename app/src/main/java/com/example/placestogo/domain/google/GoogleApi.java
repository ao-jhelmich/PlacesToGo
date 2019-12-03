package com.example.placestogo.domain.google;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoogleApi extends AsyncTask {
    protected Context context;

    public GoogleApi(Context context) {
        this.context = context;
    }

    public Object getPlaces() {
        OkHttpClient client = new OkHttpClient();

        String url  = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=AIzaSyD5rPxiZ2pFYYZNUxs2a-VHd3XCdDy5QDk";
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            Log.d("getPlaces: Api", response.body().string());
        } catch (IOException e) {
            Log.d("getPlaces: Api", e.toString());
        }

        return null;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        this.getPlaces();
        return null;
    }

}
