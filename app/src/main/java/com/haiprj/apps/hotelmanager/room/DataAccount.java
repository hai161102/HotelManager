package com.haiprj.apps.hotelmanager.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.haiprj.apps.hotelmanager.models.AccountManager;

@Dao
public interface DataAccount {
    @Query("select * from AccountManager where username = :username and password = :password and storeCode = :storeCode")
    AccountManager getAccount(String username, String password, int storeCode);

    @Query("select * from AccountManager where username =:username")
    AccountManager getAccountFromUsername(String username);
    @Insert
    void insert(AccountManager accountManager);

    @Delete
    void delete(AccountManager accountManager);
}
