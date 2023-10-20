package com.haiprj.apps.hotelmanager.ui.holders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;

import androidx.annotation.NonNull;

import com.haiprj.apps.hotelmanager.App;
import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ItemRoomBinding;
import com.haiprj.apps.hotelmanager.models.Booking;
import com.haiprj.apps.hotelmanager.models.Room;
import com.haiprj.apps.hotelmanager.models.RoomState;
import com.haiprj.apps.hotelmanager.ui.adapters.BaseAdapter;
import com.haiprj.apps.hotelmanager.ui.adapters.BaseHolder;
import com.haiprj.apps.hotelmanager.ui.adapters.RoomAdapter;
import com.haiprj.apps.hotelmanager.utils.AnimationUtils;

import java.util.Date;

public class RoomHolder extends BaseHolder<ItemRoomBinding, Room> {

    public RoomHolder(@NonNull View itemView, Context context, BaseAdapter<ItemRoomBinding, Room> adapter, ItemRoomBinding binding) {
        super(itemView, context, adapter, binding);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void load(Room obj) {
        binding.roomName.setText(obj.name);
        if (obj.getRoomState() == RoomState.NOT_EMPTY) {
            Date date = new Date();
            long currentTime = date.getTime();
            obj.setTo(currentTime);
            obj.updateHours();
        }
        long thirtyMinutes = 30 * 1000 * 60;
        long currentTime = new Date().getTime();

        for (Booking booking : App.database.getDataBooking().getAllByRoomId(obj.id)) {
            if (currentTime > booking.time + thirtyMinutes) {
                booking.deleteSelf();
            }
        }

        binding.time.setText(obj.getHours() + "h");
        binding.price.setText((obj.getPrice() * obj.getHours()) + "$");
        switch (obj.getRoomState()) {
            case EMPTY:
                ColorStateList colorStateList = ColorStateList.valueOf(context.getColor(R.color.beau_green));
                if (App.database.getDataBooking().getAllByRoomId(obj.id).size() > 0) {
                    colorStateList = ColorStateList.valueOf(context.getColor(R.color.orange));
                }
                binding.itemRoom.setBackgroundTintList(colorStateList);

                break;
            case NOT_EMPTY:
                binding.itemRoom.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.red)));
                break;
        }
        switch (obj.getRoomType()) {
            case VIP:
                binding.vipFlag.setVisibility(View.VISIBLE);
                break;
            case NORMAL:
            case MEDIUM:
                binding.vipFlag.setVisibility(View.GONE);
                break;
        }
        binding.itemRoom.setOnLongClickListener(v -> {
            AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
                @Override
                public void end() {
                    ((RoomAdapter) RoomHolder.this.adapter).setLongClickRoom(v, RoomHolder.this, obj);

                }
            });
            return true;
        });
    }
}
