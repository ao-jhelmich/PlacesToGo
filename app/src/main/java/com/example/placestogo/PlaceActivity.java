package com.example.placestogo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.example.placestogo.domain.places.Place;
import com.example.placestogo.persistence.Visited;
import com.example.placestogo.persistence.VisitedDao;
import com.example.placestogo.persistence.VisitedDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class PlaceActivity extends AppCompatActivity {

    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle extra = getIntent().getExtras();

        this.place = (Place)extra.get("placeObject");


        // TODO: intent.get can produce NullPointerException
        System.out.println(place.getId());

        // Set contentview to correct layout activity
        setContentView(R.layout.place_activity);

        // Get
        TextView textView = findViewById(R.id.textView);
        TextView boolViewVisited = findViewById(R.id.boolViewVisited);

        textView.setText(place.getName());

        Button visitedButton = findViewById(R.id.button);

        boolViewVisited.setText("false");
    }

    public void setVisited(){
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

        if(!vsDb.isVisited()){

        }
    }
}
