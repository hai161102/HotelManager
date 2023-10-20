package com.haiprj.apps.hotelmanager.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.haiprj.apps.hotelmanager.App;

@Entity(tableName = "bill", foreignKeys = {@ForeignKey(entity = Room.class, parentColumns = {"id"}, childColumns = {"roomId"})})
public class Bills {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "roomId", index = true)
    public int roomId;

    @ColumnInfo(name = "totalMoney")
    public float totalMoney;

    @ColumnInfo(name = "time")
    public long time;

    public Room getRoom() {
        return App.database.getDataRoom().get(this.roomId);
    }
    public void deleteSelf() {
        App.database.getDataBills().delete(this);
    }
}
