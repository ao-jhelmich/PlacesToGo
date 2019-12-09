package com.example.placestogo.persistence;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Visited {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private boolean visited;

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
