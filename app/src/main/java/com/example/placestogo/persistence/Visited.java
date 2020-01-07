package com.example.placestogo.persistence;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

@Entity
public class Visited {

    @PrimaryKey(autoGenerate = true)
    @NotNull
    private int id;
    private String placeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
