package com.haiprj.apps.hotelmanager.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.haiprj.apps.hotelmanager.models.Floor;

import java.util.List;

@Dao
public interface DataFloor {
    @Query("SELECT * FROM Floor WHERE hotelId = :hotelId")
    List<Floor> getAll(int hotelId);

    @Query("SELECT * FROM Floor WHERE id IN (:ids)")
    List<Floor> getAllByIds(int... ids);

    @Query("SELECT * FROM Floor WHERE name LIKE :name")
    List<Floor> findByName(String name);

    @Update
    int update(Floor floor);
    @Insert
    void insertAll(Floor... floors);
    @Delete
    void delete(Floor floor);

}
