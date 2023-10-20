package com.haiprj.apps.hotelmanager.ui.holders;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.haiprj.apps.hotelmanager.App;
import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ItemHotelBinding;
import com.haiprj.apps.hotelmanager.databinding.PopupCrudBinding;
import com.haiprj.apps.hotelmanager.models.Bills;
import com.haiprj.apps.hotelmanager.models.Booking;
import com.haiprj.apps.hotelmanager.models.Client;
import com.haiprj.apps.hotelmanager.models.Floor;
import com.haiprj.apps.hotelmanager.models.Hotel;
import com.haiprj.apps.hotelmanager.models.Room;
import com.haiprj.apps.hotelmanager.ui.adapters.BaseAdapter;
import com.haiprj.apps.hotelmanager.ui.adapters.BaseHolder;
import com.haiprj.apps.hotelmanager.ui.dialogs.DialogUpdateHotel;
import com.haiprj.apps.hotelmanager.utils.AnimationUtils;
import com.haiprj.apps.hotelmanager.utils.PopupCustom;

import java.util.List;

public class ItemHotelHolder extends BaseHolder<ItemHotelBinding, Hotel> {
    public ItemHotelHolder(@NonNull View itemView, Context context, BaseAdapter<ItemHotelBinding, Hotel> adapter, ItemHotelBinding binding) {
        super(itemView, context, adapter, binding);
    }

    @Override
    public void load(Hotel obj) {
        this.binding.hotelName.setText(obj.name);
        this.binding.hotelAddress.setText(obj.address);
        this.binding.more.setOnClickListener(v -> {
            AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
                @Override
                public void end() {
                    PopupCustom<PopupCrudBinding> popupCustom = new PopupCustom<>(context, R.layout.popup_crud);
                    popupCustom.binding.update.setOnClickListener(v1 -> {
                        DialogUpdateHotel.getInstance(context, (key, objects) -> {
                            if (key.equals("apply")) {
                                String name = (String) objects[0];
                                String address = (String) objects[1];
                                obj.name = name;
                                obj.address = address;
                                ItemHotelHolder.this.adapter.notifyItemChanged(getAdapterPosition());
                                App.database.getDataHotel().update(obj);

                            }
                        }).showUpdate(obj);
                        popupCustom.dismiss();
                    });
                    popupCustom.binding.delete.setOnClickListener(v1 -> {
                        ItemHotelHolder.this.adapter.getList().remove(obj);
                        obj.deleteSelf();
                        ItemHotelHolder.this.adapter.notifyItemRemoved(getAdapterPosition());
                        popupCustom.dismiss();
                    });

                    popupCustom.show(v);
                }
            });
        });
    }
}
