package com.haiprj.apps.hotelmanager.ui.adapters;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseHolder<T extends ViewDataBinding, T1> extends RecyclerView.ViewHolder {

    protected final Context context;
    protected final BaseAdapter<T, T1> adapter;
    protected T binding;
    public BaseHolder(@NonNull View itemView, Context context, BaseAdapter<T, T1> adapter, T binding) {
        super(itemView);
        this.context = context;
        this.adapter = adapter;
        this.binding = binding;
    }

    public abstract void load(T1 obj);

    public BaseAdapter<T, T1> getAdapter() {
        return adapter;
    }

}
