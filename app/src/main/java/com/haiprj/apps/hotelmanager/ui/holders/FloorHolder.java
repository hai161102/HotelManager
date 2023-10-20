package com.haiprj.apps.hotelmanager.ui.holders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.haiprj.apps.hotelmanager.App;
import com.haiprj.apps.hotelmanager.databinding.ItemFloorBinding;
import com.haiprj.apps.hotelmanager.models.Floor;
import com.haiprj.apps.hotelmanager.models.Room;
import com.haiprj.apps.hotelmanager.ui.adapters.BaseAdapter;
import com.haiprj.apps.hotelmanager.ui.adapters.BaseHolder;
import com.haiprj.apps.hotelmanager.ui.adapters.FloorAdapter;
import com.haiprj.apps.hotelmanager.ui.adapters.RoomAdapter;

import java.util.List;

public class FloorHolder extends BaseHolder<ItemFloorBinding, Floor> {

    @SuppressLint("StaticFieldLeak")
    protected RoomAdapter roomAdapter;
    public FloorHolder(@NonNull View itemView, Context context, BaseAdapter<ItemFloorBinding, Floor> adapter, ItemFloorBinding binding) {
        super(itemView, context, adapter, binding);
        if (roomAdapter == null) roomAdapter = new RoomAdapter(this.context);
        binding.rcvRoom.setAdapter(roomAdapter);
        this.roomAdapter.setFloorAdapter((FloorAdapter) adapter);
    }

    @Override
    public void load(Floor obj) {
        binding.floorName.setText(obj.name);
        List<Room> rooms = App.database.getDataRoom().getAllByFloor(obj.id);
        roomAdapter.getList().clear();
        roomAdapter.addData(rooms);
        this.binding.getRoot().setOnLongClickListener(v -> {
            if (this.adapter.getOnItemLongClick() != null) {
                this.adapter.getOnItemLongClick().onClick(v, obj);
                this.adapter.getOnItemLongClick().onClick(v, obj, this.adapter);
                return true;
            }
            return false;
        });
    }
}
