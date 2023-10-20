package com.haiprj.apps.hotelmanager.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.haiprj.apps.hotelmanager.App;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity(tableName = "Booking", foreignKeys = {@ForeignKey(entity = Room.class, parentColumns = {"id"}, childColumns = {"roomId"})})
public class Booking {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "roomId", index = true)
    public int roomId;
    @ColumnInfo(name = "clientName")
    public String clientName;
    @ColumnInfo(name = "numberPeoples")
    public int numPeoples;
    @ColumnInfo(name = "time")
    public long time;

    public Room getRoom() {
        return App.database.getDataRoom().get(this.roomId);
    }
    public List<Client> getAllClients() {
        return App.database.getDataClient().getAllByBooking(this.id);
    }
    public String getTimeString(String pattern) {
        Date date = new Date(this.time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return simpleDateFormat.format(date);
    }
    public void deleteSelf() {
        for (Client client : App.database.getDataClient().getAllByBooking(this.id)) {
            client.deleteSelf();
        }
        App.database.getDataBooking().delete(this);
    }
}
