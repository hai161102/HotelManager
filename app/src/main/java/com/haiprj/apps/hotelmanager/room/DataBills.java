package com.haiprj.apps.hotelmanager.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.haiprj.apps.hotelmanager.models.Bills;

import java.util.List;

@Dao
public interface DataBills {
    @Query("select * from bill")
    List<Bills> getAll();
    @Query("select * from bill where id = :id")
    Bills get(int id);
    @Query("select * from bill where roomId =:id")
    List<Bills> getByRoomId(int id);
    @Query("select * from bill where totalMoney =:cost")
    List<Bills> getByCost(float cost);
    @Query("select * from bill where time >= :time")
    List<Bills> getAllFromTime(long time);
    @Query("select * from bill where time >= :starTime and time <= :endTime")
    List<Bills> getAllBetweenTime(long starTime, long endTime);
    @Insert
    void insert(Bills bills);
    @Delete
    void delete(Bills bills);
}
