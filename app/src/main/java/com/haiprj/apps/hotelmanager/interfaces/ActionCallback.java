package com.haiprj.apps.hotelmanager.interfaces;

public interface ActionCallback<T extends Object> {
    @SuppressWarnings("unchecked")
    void callback(String key, T... args);
}
