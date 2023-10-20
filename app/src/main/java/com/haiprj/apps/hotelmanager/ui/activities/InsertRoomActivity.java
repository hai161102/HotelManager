package com.haiprj.apps.hotelmanager.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.haiprj.apps.hotelmanager.App;
import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ContentInsertRoomBinding;
import com.haiprj.apps.hotelmanager.databinding.PopupCrudBinding;
import com.haiprj.apps.hotelmanager.interfaces.OnItemClick;
import com.haiprj.apps.hotelmanager.models.Floor;
import com.haiprj.apps.hotelmanager.models.Hotel;
import com.haiprj.apps.hotelmanager.models.Room;
import com.haiprj.apps.hotelmanager.models.RoomState;
import com.haiprj.apps.hotelmanager.models.RoomType;
import com.haiprj.apps.hotelmanager.ui.adapters.BaseAdapter;
import com.haiprj.apps.hotelmanager.ui.adapters.FloorPickerAdapter;
import com.haiprj.apps.hotelmanager.ui.adapters.HotelSpinnerAdapter;
import com.haiprj.apps.hotelmanager.ui.adapters.RoomAdapter;
import com.haiprj.apps.hotelmanager.ui.adapters.RoomTypePickerAdapter;
import com.haiprj.apps.hotelmanager.ui.dialogs.BaseDialog;
import com.haiprj.apps.hotelmanager.ui.dialogs.UpdateRoomDialog;
import com.haiprj.apps.hotelmanager.ui.dialogs.WarningDialog;
import com.haiprj.apps.hotelmanager.utils.AnimationUtils;
import com.haiprj.apps.hotelmanager.utils.PopupCustom;

import java.util.ArrayList;
import java.util.List;

public class InsertRoomActivity extends BaseActivity<ContentInsertRoomBinding>{

    private FloorPickerAdapter floorPickerAdapter;
    private HotelSpinnerAdapter hotelSpinnerAdapter;

    private RoomTypePickerAdapter roomTypePickerAdapter;
    private RoomAdapter roomAdapter;

    private RoomType crRoomType = RoomType.NORMAL;

    private PopupCustom<PopupCrudBinding> popupCustom;
    public static void start(Context context) {
        Intent starter = new Intent(context, InsertRoomActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void initUI() {
        List<String> listType = new ArrayList<>();
        listType.add("Normal");
        listType.add("Medium");
        listType.add("VIP");
        roomTypePickerAdapter = new RoomTypePickerAdapter(this, R.layout.item_hotel_picker, listType);
        popupCustom = new PopupCustom<>(this, R.layout.popup_crud);

        this.binding.roomTypes.setAdapter(roomTypePickerAdapter);
        this.binding.roomTypes.setSelection(0);

        hotelSpinnerAdapter = new HotelSpinnerAdapter(
                this,
                R.layout.item_hotel_picker,
                App.database.getDataHotel().getAll());
        this.binding.hotelChoose.setAdapter(hotelSpinnerAdapter);
        this.binding.hotelChoose.setSelection(0);
        floorPickerAdapter = new FloorPickerAdapter(
                this,
                R.layout.item_hotel_picker,
                App.database.getDataFloor().getAll(this.binding.hotelChoose.getSelectedItemPosition()));
        this.binding.floorChoose.setAdapter(floorPickerAdapter);
        this.binding.floorChoose.setSelection(0);
        roomAdapter = new RoomAdapter(this);
        this.binding.listRoomInFloor.setAdapter(roomAdapter);
//        roomAdapter.addData(
//                App.database.getDataRoom().getAllByFloor(((Floor)this.binding.floorChoose.getSelectedItem()).id)
//        );
    }

    @Override
    protected void addEvents() {

        this.binding.hotelChoose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                floorPickerAdapter.clear();
                int hotelId = ((Hotel) parent.getItemAtPosition(position)).id;
                floorPickerAdapter.addAll(App.database.getDataFloor().getAll(hotelId));
                binding.floorChoose.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(0);
            }
        });

        this.binding.floorChoose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int floorId = ((Floor) parent.getItemAtPosition(position)).id;
                roomAdapter.getList().clear();
                roomAdapter.addData(App.database.getDataRoom().getAllByFloor(floorId));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(0);
            }
        });
        this.binding.done.setOnClickListener(v -> {

            AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
                @Override
                public void end() {
                    InsertRoomActivity.this.onDoneClick();
                }
            });


        });

        this.binding.roomTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        crRoomType = RoomType.NORMAL;
                        break;
                    case 1:
                        crRoomType = RoomType.MEDIUM;
                        break;
                    case 2:
                        crRoomType = RoomType.VIP;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(0);
            }
        });
        this.binding.back.setOnClickListener(v -> AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
            @Override
            public void end() {
                onBackPressed();
            }
        }));
        roomAdapter.setOnItemLongClick(new OnItemClick<Room>() {
            @Override
            public void onClick(View v, Room arg, BaseAdapter<?, Room> adapter) {
                int index = adapter.getList().indexOf(arg);
                popupCustom.binding.update.setOnClickListener(v1 -> {
                    UpdateRoomDialog updateRoomDialog = new UpdateRoomDialog(InsertRoomActivity.this, arg, (key, objects) -> {
                        if (key.equals("apply")) {
                            adapter.notifyItemChanged(index);
                            App.database.getDataRoom().update(arg);
                        }
                    });
                    updateRoomDialog.showDialog();
                    popupCustom.dismiss();
                });
                popupCustom.binding.delete.setOnClickListener(v1 -> {
                    adapter.getList().remove(arg);
                    adapter.notifyItemRemoved(index);
                    arg.deleteSelf();
                    popupCustom.dismiss();
                });
                popupCustom.show(v);
            }
        });
    }

    private void onDoneClick() {
        String name = this.binding.roomName.getText().toString();
        if (name.isEmpty()) {
            WarningDialog.getInstance(this).showMessage("Name must not empty!");
            return;
        }
        if (this.binding.price.getText().toString().isEmpty()) {
            WarningDialog.getInstance(this).showMessage("Price must not empty!");
            return;
        }
        Floor floor = (Floor) this.binding.floorChoose.getSelectedItem();
        Room room = new Room();
        room.name = name;
        room.setNumberPeoples(0);
        room.setFrom(0);
        room.setTo(0);
        room.updateHours();
        room.setPrice(Float.parseFloat(this.binding.price.getText().toString()));
        room.setRoomState(RoomState.EMPTY);
        room.setRoomType(crRoomType);
        room.setFloorId(floor.id);
        App.database.getDataRoom().insertAll(room);
        roomAdapter.getList().clear();
        roomAdapter.addData(App.database.getDataRoom().getAllByFloor(floor.id));
    }

    @Override
    protected int getLayoutID() {
        return R.layout.content_insert_room;
    }
}
