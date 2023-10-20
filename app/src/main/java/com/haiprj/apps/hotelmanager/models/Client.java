package com.haiprj.apps.hotelmanager.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.haiprj.apps.hotelmanager.App;

@Entity(tableName = "client", foreignKeys = {@ForeignKey(entity = Room.class, parentColumns = {"id"}, childColumns = {"roomId"}), @ForeignKey(entity = Booking.class, parentColumns = {"id"}, childColumns = {"bookingId"})})
public class Client {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "clientName")
    public String name;
    @ColumnInfo(name = "roomId", index = true)
    public int roomId;
    @ColumnInfo(name = "bookingId", index = true)
    public int bookingId;

    public Room getRoom() {
        return App.database.getDataRoom().get(this.roomId);
    }
    public Booking getBooking() {return App.database.getDataBooking().get(this.bookingId);}

    public void deleteSelf() {
        App.database.getDataClient().delete(this);
    }
}
