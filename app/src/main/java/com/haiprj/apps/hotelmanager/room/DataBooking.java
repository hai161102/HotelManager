package com.haiprj.apps.hotelmanager.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.haiprj.apps.hotelmanager.models.Booking;

import java.util.List;

@Dao
public interface DataBooking {
    @Query("select * from booking")
    List<Booking> getAll();
    @Query("select * from booking where roomId = :roomId")
    List<Booking> getAllByRoomId(int roomId);
    @Query("select * from booking where time >= :time")
    List<Booking> getAllFromTime(long time);
    @Query("select * from booking where time >= :starTime and time <= :endTime")
    List<Booking> getAllBetweenTime(long starTime, long endTime);
    @Query("select * from booking where id = :id")
    Booking get(int id);
    @Update
    int update(Booking booking);
    @Delete
    void delete(Booking booking);
    @Insert
    void insert(Booking booking);
}
