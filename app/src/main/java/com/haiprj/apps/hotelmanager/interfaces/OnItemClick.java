package com.haiprj.apps.hotelmanager.interfaces;

import android.view.View;

import com.haiprj.apps.hotelmanager.ui.adapters.BaseAdapter;

public interface OnItemClick<T> {
    default void onClick(View v, T arg){}
    default void onClick(View v, T arg, BaseAdapter<?, T> adapter){}
}
