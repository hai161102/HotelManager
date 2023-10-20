package com.haiprj.apps.hotelmanager;

import android.app.Application;

import androidx.room.Room;

import com.haiprj.apps.hotelmanager.models.AccountManager;
import com.haiprj.apps.hotelmanager.room.AppDatabase;

public class App extends Application {

    public static AppDatabase database;
    public static AccountManager account;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "hotel-manager").allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }
}
