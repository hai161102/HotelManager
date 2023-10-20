package com.haiprj.apps.hotelmanager.ui.dialogs;

import android.content.Context;

import androidx.annotation.NonNull;

import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.DialogUpdateHotelBinding;
import com.haiprj.apps.hotelmanager.models.Hotel;

public class DialogUpdateHotel extends BaseDialog<DialogUpdateHotelBinding>{

    private static DialogUpdateHotel instance;

    public static DialogUpdateHotel getInstance(Context context, OnActionDialogCallback callback) {
        if (DialogUpdateHotel.instance == null) DialogUpdateHotel.instance = new DialogUpdateHotel(context, callback);
        return instance;
    }

    public DialogUpdateHotel(@NonNull Context context, OnActionDialogCallback onActionDialogCallback) {
        super(context, onActionDialogCallback);
    }

    @Override
    protected void addEvent() {
        this.binding.apply.setOnClickListener(v -> {
            if (this.onActionDialogCallback !=null)
                this.onActionDialogCallback
                        .callback("apply", this.binding.hotelName.getText().toString(),
                                this.binding.hotelAddress.getText().toString());
            dismiss();
        });
        this.binding.cancel.setOnClickListener(v -> {
            dismiss();
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_update_hotel;
    }

    public void showUpdate(Hotel hotel) {
        this.showDialog();
        this.binding.hotelName.setText(hotel.name);
        this.binding.hotelAddress.setText(hotel.address);
    }
}
