package com.example.placestogo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.placestogo.domain.places.Place;
import com.example.placestogo.persistence.Visited;
import com.example.placestogo.persistence.VisitedDao;
import com.example.placestogo.persistence.VisitedDatabase;
import com.squareup.picasso.Picasso;

public class PlaceActivity extends AppCompatActivity {

    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.place_activity);

        this.place = (Place) getIntent().getExtras().get("place");
        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+ this.place.getPhoto() +"&key="+ getApplicationContext().getString(R.string.google_api_key);
        TextView placeName = findViewById(R.id.placeName);
        ImageView placeImage = findViewById(R.id.placeImage);

        placeName.setText(this.place.getName());

        Picasso.get()
                .load(url)
                .into(placeImage);
    }

    public void setVisited() {
       VisitedDatabase db = Room.databaseBuilder(this, VisitedDatabase.class, "db_visited")
                .allowMainThreadQueries()
                .build();

        Visited visited = new Visited();
        visited.setId(this.place.getId());
//        visited.setVisited(true);

        VisitedDao visitedDao = db.getVisitedDao();
        visitedDao.insert(visited);

        Visited vsDb = visitedDao.getVisitedById(this.place.getId());

        if (!vsDb.isVisited()) {

        }
    }

    public void startCompass(View view) {
        Intent intent = new Intent(this, CompassActivity.class);
        intent.putExtra("place", place);
        startActivity(intent);
    }
}
