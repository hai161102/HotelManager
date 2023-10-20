package com.haiprj.apps.hotelmanager.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ItemMenuBinding;
import com.haiprj.apps.hotelmanager.models.MenuItem;
import com.haiprj.apps.hotelmanager.ui.holders.MenuHolder;

public class MenuAdapter extends BaseAdapter<ItemMenuBinding, MenuItem> {


    public MenuAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.binding = DataBindingUtil.bind(LayoutInflater.from(this.context).inflate(R.layout.item_menu, parent, false));
        assert this.binding != null;
        return new MenuHolder(this.binding.getRoot(), this.context, this, this.binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        //noinspection unchecked
        holder.load(this.list.get(position));
    }

//    public class MenuHolder extends BaseHolder<ItemMenuBinding,MenuItem>{
//        public MenuHolder(@NonNull View itemView, ItemMenuBinding binding) {
//            super(itemView, context, adapter, binding);
//        }
//
//        @Override
//        public void load(MenuItem obj) {
//            binding.image.setImageResource(obj.getResId());
//            binding.text.setText(obj.getText());
//            binding.image.setOnClickListener(v -> {
//                AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
//                    @Override
//                    public void end() {
//                        if (onItemClick != null) {
//                            MenuAdapter.this.onItemClick.onClick(obj);
//                        }
//                    }
//                });
//
//            });
//        }
//    }
}
