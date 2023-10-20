package com.haiprj.apps.hotelmanager.models;

import android.annotation.SuppressLint;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.haiprj.apps.hotelmanager.App;

import java.util.List;

@Entity(tableName = "Room", foreignKeys = {@ForeignKey(entity = Floor.class, parentColumns = {
        "id"
},
        childColumns = {
        "floorId"
})})
public class Room {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "NumberOfPeoples")
    private int numberPeoples;
    @ColumnInfo(name = "RoomType")
    private RoomType roomType = RoomType.NORMAL;
    @ColumnInfo(name = "RoomState")
    private RoomState roomState = RoomState.EMPTY;
    @ColumnInfo(name = "FromTime")
    private long from;
    @ColumnInfo(name = "ToTime")
    private long to;
    @ColumnInfo(name = "Hours")
    private float hours;
    @ColumnInfo(name = "floorId", index = true)
    private int floorId;

    @ColumnInfo(name = "price")
    private float price;

    public int getNumberPeoples() {
        return numberPeoples;
    }

    public void setNumberPeoples(int numberPeoples) {
        this.numberPeoples = numberPeoples;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public RoomState getRoomState() {
        return roomState;
    }

    public void setRoomState(RoomState roomState) {
        this.roomState = roomState;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Floor getFloor() {
        return App.database.getDataFloor().getAllByIds(this.floorId).get(0);
    }

    public void deleteSelf() {
        for (Bills bills : App.database.getDataBills().getByRoomId(this.id)) {
            bills.deleteSelf();
        }
        for (Booking booking : App.database.getDataBooking().getAllByRoomId(this.id)) {
            booking.deleteSelf();
        }
        for (Client client : App.database.getDataClient().getAllByRoom(this.id)) {
            client.deleteSelf();
        }

        App.database.getDataRoom().delete(this);
    }
    public void updateHours() {
        float hours = ((to - from) / (1000f * 60f * 60f));
        @SuppressLint("DefaultLocale") String hour = String.format("%.2f", hours);
        float value = Float.parseFloat(hour);
        setHours(value);
    }

    public List<Client> getAllClients() {
        return App.database.getDataClient().getAllByRoom(this.id);
    }
}
