package com.haiprj.apps.hotelmanager.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.haiprj.apps.hotelmanager.models.Hotel;

import java.util.List;

@Dao
public interface DataHotel {
    @Query("SELECT * FROM Hotel")
    List<Hotel> getAll();
    @Query("SELECT * FROM Hotel WHERE id = :id")
    Hotel get(int id);
    @Query("SELECT * FROM Hotel WHERE id IN (:hotelId)")
    List<Hotel> loadAllByIds(int... hotelId);
    @Query("SELECT * FROM Hotel WHERE name LIKE :name LIMIT 1")
    Hotel findByName(String name);
    @Update
    int update(Hotel hotel);
    @Insert
    void insertAll(Hotel... hotels);
    @Delete
    void delete(Hotel hotel);
}
