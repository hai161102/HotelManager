package com.haiprj.apps.hotelmanager.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.models.Hotel;

import java.util.List;

public class HotelSpinnerAdapter extends ArrayAdapter<Hotel> {
    public HotelSpinnerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    public HotelSpinnerAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public HotelSpinnerAdapter(@NonNull Context context, int resource, @NonNull Hotel[] objects) {
        super(context, resource, objects);
    }

    public HotelSpinnerAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Hotel[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public HotelSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Hotel> objects) {
        super(context, resource, objects);
    }
    public HotelSpinnerAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Hotel> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // It is used to set our custom view.
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    protected View initView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_hotel_picker, parent, false);
        }

        TextView text = convertView.findViewById(R.id.text);
        text.setText(getItem(position).name);
        return convertView;
    }
}
