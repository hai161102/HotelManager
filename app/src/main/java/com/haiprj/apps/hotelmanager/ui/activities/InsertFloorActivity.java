package com.haiprj.apps.hotelmanager.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.haiprj.apps.hotelmanager.App;
import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ContentInsertFloorBinding;
import com.haiprj.apps.hotelmanager.models.Floor;
import com.haiprj.apps.hotelmanager.models.Hotel;
import com.haiprj.apps.hotelmanager.ui.adapters.FloorAdapter;
import com.haiprj.apps.hotelmanager.ui.adapters.HotelSpinnerAdapter;
import com.haiprj.apps.hotelmanager.ui.dialogs.WarningDialog;
import com.haiprj.apps.hotelmanager.utils.AnimationUtils;

public class InsertFloorActivity extends BaseActivity<ContentInsertFloorBinding>{

    private HotelSpinnerAdapter hotelPickerAdapter;
    private FloorAdapter floorAdapter;


    public static void start(Context context) {
        Intent starter = new Intent(context, InsertFloorActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initUI() {
        hotelPickerAdapter = new HotelSpinnerAdapter(this, R.layout.item_hotel_picker, App.database.getDataHotel().getAll());
        this.binding.hotelPicker.setAdapter(hotelPickerAdapter);
        floorAdapter = new FloorAdapter(this);
        this.binding.floorList.setAdapter(floorAdapter);
        floorAdapter.addData(App.database.getDataFloor().getAll(((Hotel)this.binding.hotelPicker.getSelectedItem()).id));
    }

    @Override
    protected void addEvents() {
        this.binding.done.setOnClickListener(v -> {
            AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
                @Override
                public void end() {
                    InsertFloorActivity.this.onDoneClick();
                }
            });

        });
        this.binding.back.setOnClickListener(v -> AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
            @Override
            public void end() {
                onBackPressed();
            }
        }));
        this.binding.hotelPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                floorAdapter.getList().clear();
                floorAdapter.addData(App.database.getDataFloor().getAll(((Hotel)parent.getSelectedItem()).id));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(0);
            }
        });
    }

    private void onDoneClick() {
        String name = this.binding.floorName.getText().toString();
        if (name.isEmpty()) {
            WarningDialog.getInstance(this).showMessage("Name must not empty!");
        }
        Hotel hotel = (Hotel) this.binding.hotelPicker.getSelectedItem();
        Floor floor = new Floor();
        floor.name = name;
        floor.hotelId = hotel.id;
        App.database.getDataFloor().insertAll(floor);
        this.floorAdapter.getList().clear();
        this.floorAdapter.addData(App.database.getDataFloor().getAll(hotel.id));
    }

    @Override
    protected int getLayoutID() {
        return R.layout.content_insert_floor;
    }
}
