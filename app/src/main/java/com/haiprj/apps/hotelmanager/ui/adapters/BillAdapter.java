package com.haiprj.apps.hotelmanager.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ItemBillBinding;
import com.haiprj.apps.hotelmanager.models.Bills;
import com.haiprj.apps.hotelmanager.ui.holders.BillHolder;

public class BillAdapter extends BaseAdapter<ItemBillBinding, Bills>{

    public BillAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseHolder<ItemBillBinding, Bills> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_bill, parent, false);
        return new BillHolder(binding.getRoot(), this.context, this, this.binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder<ItemBillBinding,Bills> holder, int position) {
        holder.load(list.get(position));
    }

//    public class BillHolder extends BaseHolder<ItemBillBinding, Bills> {
//
//        public BillHolder(@NonNull View itemView, ItemBillBinding binding) {
//            super(itemView, context, adapter, binding);
//        }
//
//        @SuppressLint("SetTextI18n")
//        @Override
//        public void load(Bills obj) {
//            Room room = obj.getRoom();
//            Floor floor = room.getFloor();
//            Hotel hotel = floor.getHotel();
//            binding.roomName.setText(room.name);
//            binding.floorName.setText(floor.name);
//            binding.hotelName.setText(hotel.name);
//            Date date = new Date();
//            date.setTime(obj.time);
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault());
//            String time = simpleDateFormat.format(date);
//            binding.payTime.setText(time);
//            binding.total.setText(String.valueOf(obj.totalMoney) + "$");
//
//        }
//    }
}
