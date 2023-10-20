package com.haiprj.apps.hotelmanager.ui.dialogs;

import android.content.Context;

import androidx.annotation.NonNull;

import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.DialogListBookingBinding;
import com.haiprj.apps.hotelmanager.interfaces.OnItemClick;
import com.haiprj.apps.hotelmanager.models.Booking;
import com.haiprj.apps.hotelmanager.ui.adapters.BookingAdapter;

import java.util.List;

public class ListBookingDialog extends BaseDialog<DialogListBookingBinding>{

    private static ListBookingDialog instance;

    public static ListBookingDialog getInstance(Context context, OnActionDialogCallback callback) {
        if (ListBookingDialog.instance == null) ListBookingDialog.instance = new ListBookingDialog(context, callback);
        return instance;
    }

    private BookingAdapter bookingAdapter;

    public ListBookingDialog(@NonNull Context context, OnActionDialogCallback onActionDialogCallback) {
        super(context, onActionDialogCallback);
    }

    @Override
    protected void addEvent() {
        bookingAdapter.setOnItemClick(new OnItemClick<Booking>() {
            @Override
            public void onClick(Booking arg) {
                ListBookingDialog.this.onActionDialogCallback.callback("clickClient", arg);
            }
        });
    }

    @Override
    protected void initView() {
        bookingAdapter = new BookingAdapter(this.getContext());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_list_booking;
    }

    public void showData(List<Booking> list) {
        showDialog();
        bookingAdapter.getList().clear();
        bookingAdapter.addData(list);
    }
    @Override
    public void showDialog() {
        super.showDialog();

    }
}
