package com.haiprj.apps.hotelmanager.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.haiprj.apps.hotelmanager.App;

@Entity(tableName = "Floor", foreignKeys = {@ForeignKey(entity = Hotel.class, parentColumns = {
        "id"
}, childColumns = {
        "hotelId"
}, onDelete = ForeignKey.CASCADE)})
public class Floor {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "hotelId", index = true)
    public int hotelId;
    @ColumnInfo(name = "name")
    public String name;


    public Hotel getHotel() {
        return App.database.getDataHotel().get(this.hotelId);
    }
    public void deleteSelf() {

        for (Room room : App.database.getDataRoom().getAllByFloor(this.id)) {
            room.deleteSelf();
        }

        App.database.getDataFloor().delete(this);
    }
}
