package com.haiprj.apps.hotelmanager.ui.activities;

import com.haiprj.apps.hotelmanager.App;
import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ActivityInsertHotelBinding;
import com.haiprj.apps.hotelmanager.databinding.PopupCrudBinding;
import com.haiprj.apps.hotelmanager.models.Hotel;
import com.haiprj.apps.hotelmanager.ui.adapters.ItemHotelAdapter;
import com.haiprj.apps.hotelmanager.ui.dialogs.WarningDialog;
import com.haiprj.apps.hotelmanager.utils.AnimationUtils;
import com.haiprj.apps.hotelmanager.utils.PopupCustom;

public class HotelManagerActivity extends BaseActivity<ActivityInsertHotelBinding>{

    private ItemHotelAdapter adapter;
    @Override
    protected void initUI() {
        adapter = new ItemHotelAdapter(this);
        this.binding.floorList.setAdapter(adapter);
    }

    @Override
    protected void addEvents() {
        adapter.addData(App.database.getDataHotel().getAll());
        binding.back.setOnClickListener(v -> {
            AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
                @Override
                public void end() {
                    HotelManagerActivity.this.onBackPressed();
                }
            });

        });
        binding.done.setOnClickListener(v -> {
            AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
                @Override
                public void end() {
                    HotelManagerActivity.this.onDoneClick();
                }
            });
        });
    }

    private void onDoneClick() {
        Hotel hotel = new Hotel();
        hotel.name = this.binding.hotelName.getText().toString();
        hotel.address = this.binding.address.getText().toString();
        if (hotel.name.isEmpty() || hotel.address.isEmpty()) {
            WarningDialog.getInstance(this).showMessage("Name and address must not empty!");
            return;
        }
        App.database.getDataHotel().insertAll(hotel);
        this.adapter.getList().clear();
        this.adapter.addData(App.database.getDataHotel().getAll());
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_insert_hotel;
    }
}
