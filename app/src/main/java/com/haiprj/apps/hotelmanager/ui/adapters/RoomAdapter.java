package com.haiprj.apps.hotelmanager.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ItemRoomBinding;
import com.haiprj.apps.hotelmanager.models.Room;
import com.haiprj.apps.hotelmanager.ui.holders.RoomHolder;

public class RoomAdapter extends BaseAdapter<ItemRoomBinding, Room>{

    protected FloorAdapter floorAdapter;

    public RoomAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseHolder<ItemRoomBinding, Room> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_room, parent, false);
        return new RoomHolder(this.binding.getRoot(), this.context, this, this.binding);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseHolder<ItemRoomBinding, Room> holder, int position) {
        holder.load(list.get(position));
    }

    public void setLongClickRoom(View v, RoomHolder holder, Room room) {
        if (this.floorAdapter != null) {
            this.floorAdapter.setLongClickRoom(v, holder, room);
        }
    }

    public void setFloorAdapter(FloorAdapter floorAdapter) {
        this.floorAdapter = floorAdapter;
    }
}
