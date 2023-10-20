package com.haiprj.apps.hotelmanager.ui.holders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.haiprj.apps.hotelmanager.databinding.ItemBillBinding;
import com.haiprj.apps.hotelmanager.models.Bills;
import com.haiprj.apps.hotelmanager.models.Floor;
import com.haiprj.apps.hotelmanager.models.Hotel;
import com.haiprj.apps.hotelmanager.models.Room;
import com.haiprj.apps.hotelmanager.ui.adapters.BaseAdapter;
import com.haiprj.apps.hotelmanager.ui.adapters.BaseHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BillHolder extends BaseHolder<ItemBillBinding, Bills> {
    public BillHolder(@NonNull View itemView, Context context, BaseAdapter<ItemBillBinding, Bills> adapter, ItemBillBinding binding) {
        super(itemView, context, adapter, binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void load(Bills obj) {
        Room room = obj.getRoom();
        Floor floor = room.getFloor();
        Hotel hotel = floor.getHotel();
        binding.roomName.setText(room.name);
        binding.floorName.setText(floor.name);
        binding.hotelName.setText(hotel.name);
        Date date = new Date();
        date.setTime(obj.time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault());
        String time = simpleDateFormat.format(date);
        binding.payTime.setText(time);
        binding.total.setText(obj.totalMoney + "$");
    }
}
