package com.example.placestogo.domain.google;

import android.content.Context;

import com.example.placestogo.R;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class GoogleApi {
    protected PlacesClient client;
    protected Context context;

    public GoogleApi(Context context) {
        this.context = context;
        this.setClient();
    }

    private void setClient() {
        Places.initialize(this.context, this.context.getString(R.string.google_api_key));

        this.client = Places.createClient(this.context);
    }
}
