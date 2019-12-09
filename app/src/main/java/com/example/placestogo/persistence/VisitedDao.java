package com.example.placestogo.persistence;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface VisitedDao {

    @Insert
    void insert(Visited... visits);

    @Update
    void update(Visited... visits);

    @Delete
    void delete(Visited... visits);

    @Query("SELECT * FROM Visited")
    List<Visited> getVisited();

    @Query("SELECT * FROM Visited WHERE visited = :visited")
    Visited getVisitedByVisited(Boolean visited);

}
