package com.haiprj.apps.hotelmanager.ui.holders;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.haiprj.apps.hotelmanager.databinding.ItemMenuBinding;
import com.haiprj.apps.hotelmanager.models.MenuItem;
import com.haiprj.apps.hotelmanager.ui.adapters.BaseAdapter;
import com.haiprj.apps.hotelmanager.ui.adapters.BaseHolder;
import com.haiprj.apps.hotelmanager.utils.AnimationUtils;

public class MenuHolder extends BaseHolder<ItemMenuBinding, MenuItem> {
    public MenuHolder(@NonNull View itemView, Context context, BaseAdapter<ItemMenuBinding, MenuItem> adapter, ItemMenuBinding binding) {
        super(itemView, context, adapter, binding);
    }

    @Override
    public void load(MenuItem obj) {
        binding.image.setImageResource(obj.getResId());
        binding.text.setText(obj.getText());
        binding.getRoot().setOnClickListener(v -> {
            AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
                @Override
                public void end() {
                    if (adapter.getOnItemClick() != null) {
                        adapter.getOnItemClick().onClick(obj);
                    }
                }
            });

        });
    }
}
