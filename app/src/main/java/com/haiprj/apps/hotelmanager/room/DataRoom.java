package com.haiprj.apps.hotelmanager.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.haiprj.apps.hotelmanager.models.Room;

import java.util.List;

@Dao
public interface DataRoom {
    @Query("Select * from Room")
    List<Room> getAll();
    @Query("Select * from Room where id = :id")
    Room get(int id);
    @Query("Select * from Room where floorId = :id")
    List<Room> getAllByFloor(int id);
    @Query("select * from Room where name like :name")
    List<Room> findByName(String name);

    @Update
    int update(Room room);
    @Insert
    void insertAll(Room... rooms);
    @Delete
    void delete(Room room);
}
