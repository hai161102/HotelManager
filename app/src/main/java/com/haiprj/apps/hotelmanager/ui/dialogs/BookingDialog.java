package com.haiprj.apps.hotelmanager.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;

import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.DialogAddBookingBinding;
import com.haiprj.apps.hotelmanager.models.Booking;
import com.haiprj.apps.hotelmanager.utils.AnimationUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BookingDialog extends BaseDialog<DialogAddBookingBinding>{

    private static BookingDialog instance;
    public static BookingDialog getInstance(Context context, OnActionDialogCallback onActionDialogCallback) {
        if (BookingDialog.instance == null) BookingDialog.instance = new BookingDialog(context, onActionDialogCallback);
        return BookingDialog.instance;
    }

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
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault());
                    try {
                        Date date = simpleDateFormat.parse(BookingDialog.this.binding.timeCheckIn.getText().toString());
                        assert date != null;
                        Log.d("TAG_BookingDialog", "end: " + date.getTime());
                        BookingDialog.this.onActionDialogCallback.callback("apply", BookingDialog.this.binding.clientName.getText().toString(),
                                date.getTime()
                                );
                    } catch (ParseException e) {
                        System.out.println("Error parse date time in booking: " + e.getMessage());
                    }

                    BookingDialog.this.dismiss();
                }
            });

        });
        this.binding.timeCheckIn.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext());
            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                dateString = dayOfMonth + "-" + (month + 1) + "-" + year;
                Log.d("TAG_BookingDialog", "date: " + dateString);
                @SuppressLint("SetTextI18n") TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), (view1, hourOfDay, minute) -> {
                    timeString = hourOfDay + ":" + minute;
                    Log.d("TAG_BookingDialog", "hours: " + timeString);
                    this.binding.timeCheckIn.setText(timeString + " " + dateString);
                }, 0, 0, true);
                timePickerDialog.show();
            });
            datePickerDialog.show();
        });
        this.binding.cancel.setOnClickListener(v -> AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
            @Override
            public void end() {
                BookingDialog.this.dismiss();
            }
        }));

    }

    @Override
    public void showDialog() {
        super.showDialog();
        this.binding.clientName.setText("");
        this.binding.timeCheckIn.setText("");
    }

    @Override
    protected void initView() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_add_booking;
    }
}
