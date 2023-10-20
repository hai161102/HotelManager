package com.haiprj.apps.hotelmanager.ui.holders;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.haiprj.apps.hotelmanager.databinding.ItemBookingBinding;
import com.haiprj.apps.hotelmanager.models.Booking;
import com.haiprj.apps.hotelmanager.models.Floor;
import com.haiprj.apps.hotelmanager.models.Hotel;
import com.haiprj.apps.hotelmanager.models.Room;
import com.haiprj.apps.hotelmanager.ui.adapters.BaseAdapter;
import com.haiprj.apps.hotelmanager.ui.adapters.BaseHolder;

public class BookingHolder extends BaseHolder<ItemBookingBinding, Booking> {
    public BookingHolder(@NonNull View itemView, Context context, BaseAdapter<ItemBookingBinding, Booking> adapter, ItemBookingBinding binding) {
        super(itemView, context, adapter, binding);
    }

    @Override
    public void load(Booking obj) {
        Room room = obj.getRoom();
        Floor floor = room.getFloor();
        Hotel hotel = floor.getHotel();
        this.binding.clientName.setText(obj.clientName);
        this.binding.roomName.setText(room.name);
        this.binding.floorName.setText(floor.name);
        this.binding.hotelName.setText(hotel.name);
        this.binding.fromTime.setText(obj.getTimeString("HH:mm:ss dd-MM-yyyy"));
    }
}
