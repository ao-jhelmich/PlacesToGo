package com.example.placestogo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.placestogo.domain.places.Place;
import com.example.placestogo.persistence.Visited;
import com.example.placestogo.persistence.VisitedDao;
import com.example.placestogo.persistence.VisitedDatabase;

public class PlaceActivity extends AppCompatActivity {

    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle extra = getIntent().getExtras();

        // TODO: intent.get can produce NullPointerException
        Place place = (Place)extra.get("place");

        // Set contentview to correct layout activity
        setContentView(R.layout.place_activity);

        // Get
        TextView textView = findViewById(R.id.textView);
        TextView boolViewVisited = findViewById(R.id.boolViewVisited);

        textView.setText(place.getName());
        System.out.println(place.getId());
;
    }

    public void setVisited() {
        /*
        TODO: Database stuff happening here
        */

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
        startActivity(intent);
    }
}
