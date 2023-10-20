package com.haiprj.apps.hotelmanager.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.haiprj.apps.hotelmanager.App;
import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ActivityListBillsBinding;
import com.haiprj.apps.hotelmanager.models.Bills;
import com.haiprj.apps.hotelmanager.ui.adapters.BillAdapter;

import java.util.Collections;
import java.util.List;

public class BillsActivity extends BaseActivity<ActivityListBillsBinding>{

    public static final String ALL = "ALL";
    public static final String BY_ROOM = "BY_ROOM";
    public static final String SHOW_TYPE = "SHOW_TYPE";
    private BillAdapter billAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, BillsActivity.class);
        context.startActivity(starter);
    }

    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, BillsActivity.class);
        starter.putExtra("data", bundle);
        context.startActivity(starter);
    }

    @Override
    protected void initUI() {

        Bundle bundle = getIntent().getBundleExtra("data");
        String showType = ALL;
        if (bundle != null) showType = bundle.getString(SHOW_TYPE, BillsActivity.ALL);

        billAdapter = new BillAdapter(this);
        this.binding.rcvBills.setAdapter(billAdapter);
        if (showType.equals(ALL)) {
            List<Bills> list = App.database.getDataBills().getAll();
            Collections.reverse(list);
            billAdapter.addData(list);
        }
        if (showType.equals(BY_ROOM)) {
            int roomId = bundle.getInt("roomId", 1);
            List<Bills> list = App.database.getDataBills().getByRoomId(roomId);
            Collections.reverse(list);
            billAdapter.addData(list);
        }
    }

    @Override
    protected void addEvents() {
        binding.header.back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_list_bills;
    }
}
