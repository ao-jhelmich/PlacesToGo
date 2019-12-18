package com.example.placestogo.persistence;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Visited {

    @PrimaryKey(autoGenerate = true)
    private String id;
    private boolean visited;

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
