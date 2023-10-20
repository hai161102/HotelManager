package com.haiprj.apps.hotelmanager.ui.dialogs;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;

import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.DialogAddBookingBinding;
import com.haiprj.apps.hotelmanager.models.Booking;
import com.haiprj.apps.hotelmanager.utils.AnimationUtils;

public class BookingDialog extends BaseDialog<DialogAddBookingBinding>{

    private static BookingDialog instance;
    public static BookingDialog getInstance(Context context, OnActionDialogCallback onActionDialogCallback) {
        if (BookingDialog.instance == null) BookingDialog.instance = new BookingDialog(context, onActionDialogCallback);
        return BookingDialog.instance;
    }

    private DatePickerDialog datePickerDialog;
//    private final TimePickerDialog timePickerDialog;
    private String dateString;
    private String timeString;

    public BookingDialog(@NonNull Context context, OnActionDialogCallback onActionDialogCallback) {
        super(context, onActionDialogCallback);

    }

    @Override
    protected void addEvent() {
        this.binding.oke.setOnClickListener(v -> {
            AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
                @Override
                public void end() {
                    if (BookingDialog.this.binding.clientName.getText().toString().length() <= 6) {
                        WarningDialog.getInstance(BookingDialog.this.getContext()).showMessage("Client name length must greater than 6");
                        return;
                    }
                    BookingDialog.this.onActionDialogCallback.callback("apply", BookingDialog.this.binding.clientName.getText().toString());
                    BookingDialog.this.dismiss();
                }
            });

        });
        this.binding.timeCheckIn.setOnClickListener(v -> {
            datePickerDialog.show();
        });
        this.binding.cancel.setOnClickListener(v -> AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
            @Override
            public void end() {
                BookingDialog.this.dismiss();
            }
        }));

        datePickerDialog.getButton(datePickerDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            dateString = datePickerDialog.getDatePicker().getDayOfMonth() + "-"
                    + datePickerDialog.getDatePicker().getMonth() + "-"
                    + datePickerDialog.getDatePicker().getYear();
//            datePickerDialog.dismiss();
//            timePickerDialog.show();
        });
    }

    @Override
    protected void initView() {
        datePickerDialog = new DatePickerDialog(this.getContext());
//        timePickerDialog = new TimePickerDialog(this.getContext(), (view, hourOfDay, minute) -> {
//
//        }, 12, 10, false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_add_booking;
    }
}
