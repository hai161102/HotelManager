package com.haiprj.apps.hotelmanager.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ItemFloorBinding;
import com.haiprj.apps.hotelmanager.models.Floor;
import com.haiprj.apps.hotelmanager.models.Room;
import com.haiprj.apps.hotelmanager.ui.holders.FloorHolder;
import com.haiprj.apps.hotelmanager.ui.holders.RoomHolder;

public class FloorAdapter extends BaseAdapter<ItemFloorBinding, Floor>{

    protected OnRoomLongClick onRoomLongClick;

    public void setOnRoomLongClick(OnRoomLongClick onRoomLongClick) {
        this.onRoomLongClick = onRoomLongClick;
    }

    public FloorAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseHolder<ItemFloorBinding,Floor> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this.context),R.layout.item_floor, parent, false);
        return new FloorHolder(this.binding.getRoot(), this.context, this, this.binding);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseHolder<ItemFloorBinding, Floor> holder, int position) {
        holder.load(this.list.get(position));
    }

    public void notifyAt(int floorIndex, int roomIndex) {

    }

    public void setLongClickRoom(View v, RoomHolder holder, Room room) {
        if (this.onRoomLongClick != null) this.onRoomLongClick.onLongClick(v, holder, room);
    }

    public interface OnRoomLongClick {
        void onLongClick(View v, RoomHolder holder, Room room);
    }
}
