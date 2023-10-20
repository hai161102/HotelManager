package com.haiprj.apps.hotelmanager.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.haiprj.apps.hotelmanager.App;
import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ContentInsertFloorBinding;
import com.haiprj.apps.hotelmanager.databinding.PopupCrudBinding;
import com.haiprj.apps.hotelmanager.databinding.PopupOptionsBinding;
import com.haiprj.apps.hotelmanager.interfaces.OnItemClick;
import com.haiprj.apps.hotelmanager.models.Floor;
import com.haiprj.apps.hotelmanager.models.Hotel;
import com.haiprj.apps.hotelmanager.ui.adapters.BaseAdapter;
import com.haiprj.apps.hotelmanager.ui.adapters.FloorAdapter;
import com.haiprj.apps.hotelmanager.ui.adapters.HotelSpinnerAdapter;
import com.haiprj.apps.hotelmanager.ui.dialogs.BaseDialog;
import com.haiprj.apps.hotelmanager.ui.dialogs.RenameDialog;
import com.haiprj.apps.hotelmanager.ui.dialogs.WarningDialog;
import com.haiprj.apps.hotelmanager.utils.AnimationUtils;
import com.haiprj.apps.hotelmanager.utils.PopupCustom;

public class InsertFloorActivity extends BaseActivity<ContentInsertFloorBinding>{

    private HotelSpinnerAdapter hotelPickerAdapter;
    private FloorAdapter floorAdapter;
    private PopupCustom<PopupCrudBinding> popupCustom;

    private Floor currentFloor;

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
        popupCustom = new PopupCustom<>(this, R.layout.popup_crud);
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
        this.floorAdapter.setOnItemLongClick(new OnItemClick<Floor>() {
            @Override
            public void onClick(View v, Floor arg, BaseAdapter<?, Floor> adapter) {
                int index = adapter.getList().indexOf(arg);
                popupCustom.binding.update.setOnClickListener(v1 -> {
                    RenameDialog renameDialog = new RenameDialog(InsertFloorActivity.this, (key, objects) -> {
                        if (key.equals("apply")) {
                            arg.name = (String) objects[0];
                            adapter.notifyItemChanged(index);
                            App.database.getDataFloor().update(arg);
                        }
                    });
                    renameDialog.showDialog();
                    popupCustom.dismiss();
                });
                popupCustom.binding.delete.setOnClickListener(v1 -> {
                    adapter.getList().remove(arg);
                    adapter.notifyItemRemoved(index);
                    arg.deleteSelf();
                });
                popupCustom.show(v);
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
