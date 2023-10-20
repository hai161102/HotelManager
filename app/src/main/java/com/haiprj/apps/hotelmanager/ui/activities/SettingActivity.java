package com.haiprj.apps.hotelmanager.ui.activities;

import android.content.Intent;

import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ActivitySettingsBinding;
import com.haiprj.apps.hotelmanager.utils.AnimationUtils;

public class SettingActivity extends BaseActivity<ActivitySettingsBinding>{
    @Override
    protected void initUI() {

    }

    @Override
    protected void addEvents() {
        binding.back.setOnClickListener(v -> {
            AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
                @Override
                public void end() {
                    finish();
                }
            });
        });
        binding.btnAddHotel.setOnClickListener(v -> {
            AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
                @Override
                public void end() {
                    startActivity(new Intent(SettingActivity.this, HotelManagerActivity.class));
                }
            });

        });
        binding.btnAddFloor.setOnClickListener(v -> {
            AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
                @Override
                public void end() {
                    InsertFloorActivity.start(SettingActivity.this);
                }
            });
        });
        binding.btnAddRoom.setOnClickListener(v -> {
            AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
                @Override
                public void end() {
                    InsertRoomActivity.start(SettingActivity.this);
                }
            });
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_settings;
    }
}
