package com.haiprj.apps.hotelmanager.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ItemHotelBinding;
import com.haiprj.apps.hotelmanager.models.Hotel;
import com.haiprj.apps.hotelmanager.ui.holders.ItemHotelHolder;

public class ItemHotelAdapter extends BaseAdapter<ItemHotelBinding, Hotel>{

    public ItemHotelAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseHolder<ItemHotelBinding, Hotel> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_hotel, parent, false);
        return new ItemHotelHolder(this.binding.getRoot(), context, this, this.binding);
    }

}
