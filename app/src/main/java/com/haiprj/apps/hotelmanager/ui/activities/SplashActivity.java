package com.haiprj.apps.hotelmanager.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;

import com.haiprj.apps.hotelmanager.App;
import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ActivitySplashBinding;
import com.haiprj.apps.hotelmanager.models.Floor;
import com.haiprj.apps.hotelmanager.models.Hotel;
import com.haiprj.apps.hotelmanager.models.Room;
import com.haiprj.apps.hotelmanager.models.RoomState;
import com.haiprj.apps.hotelmanager.models.RoomType;
import com.haiprj.apps.hotelmanager.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity<ActivitySplashBinding>{
    @Override
    protected void initUI() {
        SharePreferenceUtil.getInstance().init(this);

//        if (!SharePreferenceUtil.getInstance().getSharedPreferences().getBoolean(getString(R.string.share_prf_key_has_data), false)) {
//            this.initData();
//        }
        if (App.database.getDataHotel().getAll().size() <= 0) this.initData();
        this.handler.postDelayed(SplashActivity.this::startSign, 2000);

    }

    private void initData() {
        Hotel[] hotels = new Hotel[5];
        for (int i = 0; i < hotels.length; i++) {
            hotels[i] = new Hotel();
            hotels[i].name =
                    "Hotel " + (i + 1);
            hotels[i].address =
                    "Viet Nam";

        }

        App.database.getDataHotel().insertAll(
                hotels
        );
        List<Floor> floorList = new ArrayList<>();

        for (Hotel hotel : App.database.getDataHotel().getAll()) {
            for (int i = 0; i < 5; i++) {
                Floor floor = new Floor();
                floor.name = hotel.name + "-" + "Floor " + (i + 1);
                floor.hotelId = hotel.id;
                floorList.add(floor);
            }
        }
        for (Floor floor : floorList) {
            App.database.getDataFloor().insertAll(floor);
        }

        List<Room> roomList = new ArrayList<>();

        for (Hotel hotel : App.database.getDataHotel().getAll()) {
            List<Floor> floors = App.database.getDataFloor().getAll(hotel.id);
            for (Floor floor : floors) {
                for (int i = 0; i < 5; i++) {
                    Room room = new Room();
                    room.name = "Room " + (floors.indexOf(floor) + 1) + "0" + (i);
                    room.setNumberPeoples(0);
                    room.setRoomType(RoomType.MEDIUM);
                    if (floors.indexOf(floor) == floors.size() - 1) {
                        room.setRoomType(RoomType.VIP);
                    }
                    room.setRoomState(RoomState.EMPTY);
                    room.setFrom(0);
                    room.setTo(0);
                    room.setHours(0);
                    room.setPrice(room.getRoomType() != RoomType.VIP ? 1000 : 2000);
                    room.setFloorId(floor.id);
                    roomList.add(room);
                }
            }
        }

        for (Room room : roomList) {
            App.database.getDataRoom().insertAll(room);
        }
        SharePreferenceUtil.getInstance().getEditor().putBoolean(getString(R.string.share_prf_key_has_data), true);

    }

    private void startMain() {
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
    private void startSign() {
        startActivity(new Intent(this, SignActivity.class));
        this.finish();
    }
    @Override
    protected void addEvents() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_splash;
    }
}
