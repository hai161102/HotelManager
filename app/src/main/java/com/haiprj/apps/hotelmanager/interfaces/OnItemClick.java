package com.haiprj.apps.hotelmanager.interfaces;

import com.haiprj.apps.hotelmanager.ui.adapters.BaseAdapter;

public interface OnItemClick<T> {
    default void onClick(T arg){}
    default void onClick(T arg, BaseAdapter adapter){}
}
