package com.haiprj.apps.hotelmanager.room;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.haiprj.apps.hotelmanager.models.AccountManager;
import com.haiprj.apps.hotelmanager.models.Bills;
import com.haiprj.apps.hotelmanager.models.Booking;
import com.haiprj.apps.hotelmanager.models.Client;
import com.haiprj.apps.hotelmanager.models.Floor;
import com.haiprj.apps.hotelmanager.models.Hotel;
import com.haiprj.apps.hotelmanager.models.Room;

@Database(entities = {Hotel.class, Floor.class, Room.class, Bills.class, Booking.class, AccountManager.class, Client.class}, version = 15)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DataHotel getDataHotel();
    public abstract DataFloor getDataFloor();
    public abstract DataRoom getDataRoom();
    public abstract DataBills getDataBills();
    public abstract DataBooking getDataBooking();
    public abstract DataClient getDataClient();
    public abstract DataAccount getDataAccount();

}
