package com.haiprj.apps.hotelmanager.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.haiprj.apps.hotelmanager.interfaces.OnItemClick;
import com.haiprj.apps.hotelmanager.models.Booking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseAdapter<T extends ViewDataBinding, T1> extends RecyclerView.Adapter<BaseHolder<T, T1>> {
    protected final Context context;
    protected final List<T1> list = new ArrayList<>();

    protected OnItemClick<T1> onItemClick;
    protected OnItemClick<T1> onItemLongClick;
    protected final List<T1> cacheList = new ArrayList<>();




    public OnItemClick<T1> getOnItemLongClick() {
        return onItemLongClick;
    }


    public void setOnItemLongClick(OnItemClick<T1> onItemLongClick) {
        this.onItemLongClick = onItemLongClick;
    }

    public void setOnItemClick(OnItemClick<T1> onItemClick) {
        this.onItemClick = onItemClick;
    }

    public OnItemClick<T1> getOnItemClick() {
        return onItemClick;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addData(T1 item) {
        if (this.list.contains(item)) return;
        this.list.add(item);
        this.cacheList.clear();
        this.cacheList.addAll(this.list);
        this.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addData(List<T1> items) {
        List<T1> l = new ArrayList<>();
        for (T1 item : items) {
            if (!this.list.contains(item)) l.add(item);
        }
        this.list.addAll(l);
        this.cacheList.clear();
        this.cacheList.addAll(this.list);
        this.notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull BaseHolder<T, T1> holder, int position) {
        holder.load(this.list.get(position));
    }

    public List<T1> getList() {
        return list;
    }

    public T binding;
    public BaseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void addTermData(List<T1> term) {
        this.list.clear();
        this.list.addAll(term);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setToDefault() {
        this.list.clear();
        this.list.addAll(this.cacheList);
        notifyDataSetChanged();
    }
}
