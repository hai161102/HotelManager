package com.haiprj.apps.hotelmanager.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.haiprj.apps.hotelmanager.models.Client;

import java.util.List;

@Dao
public interface DataClient {
    @Query("select * from client")
    List<Client> getAll();
    @Query("select * from client where roomId = :roomId")
    List<Client> getAllByRoom(int roomId);
    @Query("select * from client where bookingId = :bookingId")
    List<Client> getAllByBooking(int bookingId);
    @Query("select * from client where id = :id")
    List<Client> get(int id);
    @Insert
    void inserts(Client... clients);
    @Delete
    void delete(Client client);
    @Update
    int update(Client client);
}
