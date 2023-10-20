package com.haiprj.apps.hotelmanager.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.haiprj.apps.hotelmanager.App;

@Entity(tableName = "Hotel")
public class Hotel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "address")
    public String address;


    public void deleteSelf() {
        for (Floor floor : App.database.getDataFloor().getAll(this.id)) {
            floor.deleteSelf();
        }
        App.database.getDataHotel().delete(this);
    }
}
