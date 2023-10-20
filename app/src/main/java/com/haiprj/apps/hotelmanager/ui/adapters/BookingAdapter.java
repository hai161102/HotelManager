package com.haiprj.apps.hotelmanager.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ItemBookingBinding;
import com.haiprj.apps.hotelmanager.models.Booking;
import com.haiprj.apps.hotelmanager.ui.holders.BookingHolder;

public class BookingAdapter extends BaseAdapter<ItemBookingBinding, Booking>{

    public BookingAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseHolder<ItemBookingBinding, Booking> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_booking, parent, false);
        return new BookingHolder(binding.getRoot(), context, this, this.binding);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseHolder<ItemBookingBinding, Booking> holder, int position) {
        holder.load(this.list.get(position));
    }
}
