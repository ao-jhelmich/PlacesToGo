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

import java.util.List;

public class PlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle extra = getIntent().getExtras();

        /*
        TODO: Database stuff happening here
         */
        VisitedDatabase db = Room.databaseBuilder(this, VisitedDatabase.class, "db_visited")
                .allowMainThreadQueries()
                .build();

        Visited visited = new Visited();
        visited.setVisited(true);

        VisitedDao visitedDao = db.getVisitedDao();
        visitedDao.insert(visited);

        List<Visited> visiteds = visitedDao.getVisited();
        for(Visited vs : visiteds){
            System.out.println("Visisted: " + vs.getId() + ":" + vs.isVisited());
        }
        ///////////////////////////


        // TODO: intent.get can produce NullPointerException
        Place place = (Place)extra.get("placeName");

        // Set contentview to correct layout activity
        setContentView(R.layout.place_activity);

        // Get
        TextView textView = findViewById(R.id.textView);
        TextView boolViewVisited = findViewById(R.id.boolViewVisited);

        textView.setText(place.getName());
        boolViewVisited.setText("false");
    }

    public void startCompass(View view) {
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
    }
}
