package com.example.placestogo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.placestogo.domain.places.Place;
import com.example.placestogo.persistence.Visited;
import com.example.placestogo.persistence.VisitedDao;
import com.example.placestogo.persistence.VisitedDatabase;
import com.squareup.picasso.Picasso;

public class PlaceActivity extends AppCompatActivity {

    private Place place;
    private TextView visitedText;
    private TextView placeName;
    private ImageView placeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.place_activity);
        this.place = (Place) getIntent().getExtras().get("place");

        this.placeName = findViewById(R.id.placeName);
        this.placeImage = findViewById(R.id.placeImage);
        this.visitedText = findViewById(R.id.visitedText);

        checkVisited();

        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+ this.place.getPhoto() +"&key="+ getApplicationContext().getString(R.string.google_api_key);

        placeName.setText(this.place.getName());

        Picasso.get()
                .load(url)
                .into(placeImage);
    }

    public boolean checkPlaceVisited() {
        VisitedDatabase db = Room.databaseBuilder(this, VisitedDatabase.class, "db_visited")
                .allowMainThreadQueries()
                .build();

        VisitedDao visitedDao = db.getVisitedDao();

        Visited vsDb = visitedDao.getVisitedByPlaceId(this.place.getId());

        return vsDb != null;
    }

    public void checkVisited() {
        if (this.checkPlaceVisited()) {
            this.visitedText.setText(getString(R.string.place_visited));
        } else {
            this.visitedText.setText(getString(R.string.place_not_visited));
        }
    }

    public void setVisited(View view) {
        if (this.checkPlaceVisited()) {
            return;
        }

        VisitedDatabase db = Room.databaseBuilder(this, VisitedDatabase.class, "db_visited")
            .allowMainThreadQueries()
            .build();

        Visited visited = new Visited();
        visited.setPlaceId(this.place.getId());

        VisitedDao visitedDao = db.getVisitedDao();
        visitedDao.insert(visited);

        this.visitedText.setText(getString(R.string.place_visited));
    }

    public void startCompass(View view) {
        Intent intent = new Intent(this, CompassActivity.class);
        intent.putExtra("place", place);
        startActivity(intent);
    }
}
