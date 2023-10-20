package com.haiprj.apps.hotelmanager.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;

import com.haiprj.apps.hotelmanager.App;
import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ActivityBookingBinding;
import com.haiprj.apps.hotelmanager.models.Booking;
import com.haiprj.apps.hotelmanager.ui.adapters.BookingAdapter;
import com.haiprj.apps.hotelmanager.utils.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends BaseActivity<ActivityBookingBinding>{

    private BookingAdapter bookingAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, BookingActivity.class);
        context.startActivity(starter);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initUI() {
        bookingAdapter = new BookingAdapter(this);
        this.binding.bookingList.setAdapter(bookingAdapter);
        bookingAdapter.addData(App.database.getDataBooking().getAll());
        this.binding.header.title.setText(R.string.booking);
    }

    @Override
    protected void addEvents() {
        this.binding.header.back.setOnClickListener(v -> {
            AnimationUtils.scale(v, 0.9f, 100, new AnimationUtils.AnimationListener() {
                @Override
                public void end() {
                    BookingActivity.this.onBackPressed();
                }
            });
        });
        this.binding.findClientName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null) return;
                String nameMember = s.toString().toLowerCase().trim();
                if (nameMember.length() <= 0) {
                    BookingActivity.this.bookingAdapter.setToDefault();
                    return;
                }
                List<Booking> list = App.database.getDataBooking().getAll();
                List<Booking> term = new ArrayList<>();
                for (Booking booking : list) {
                    if (booking.clientName.contains(nameMember)) {
                        term.add(booking);
                    }
                }
                BookingActivity.this.bookingAdapter.addTermData(term);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_booking;
    }
}
