package com.haiprj.apps.hotelmanager.ui.activities;

import android.content.Intent;

import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ActivityMainBinding;
import com.haiprj.apps.hotelmanager.interfaces.OnItemClick;
import com.haiprj.apps.hotelmanager.models.MenuItem;
import com.haiprj.apps.hotelmanager.models.MenuType;
import com.haiprj.apps.hotelmanager.ui.adapters.MenuAdapter;
import com.haiprj.apps.hotelmanager.utils.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private MenuAdapter menuAdapter;

    @Override
    protected void initUI() {
//        Hotel hotel = new Hotel();
//        hotel.name = "Five stars";
//        hotel.address = "Ha Noi, Viet Nam";
//        App.database.getDataHotel().insertAll(hotel);



        menuAdapter = new MenuAdapter(this);
        binding.rcvMenu.setAdapter(menuAdapter);
        menuAdapter.addData(menuList());
    }

    @Override
    protected void addEvents() {
        this.binding.settings.setOnClickListener(v -> {
            AnimationUtils.scale(v, 1.2f, 100, new AnimationUtils.AnimationListener() {
                @Override
                public void end() {
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                }
            });
        });
        this.menuAdapter.setOnItemClick(new OnItemClick<MenuItem>() {
            @Override
            public void onClick(MenuItem arg) {
//                if (arg.getMenuType().equals(MenuType.DIAGRAM)) {
//                    startActivity(new Intent(MainActivity.this, RoomDiagramActivity.class));
//                }
                switch (arg.getMenuType()) {
                    case DIAGRAM:
                        RoomDiagramActivity.start(MainActivity.this);
                        break;
                    case BILLS:
                        BillsActivity.start(MainActivity.this);
                        break;
                    case BOOKING:
                        BookingActivity.start(MainActivity.this);
                        break;
                }
            }
        });
    }

    private List<MenuItem> menuList() {
        List<MenuItem> list = new ArrayList<>();
        list.add(new MenuItem(R.drawable.diagram, "Diagram", MenuType.DIAGRAM));
        list.add(new MenuItem(R.drawable.bill, "Bills", MenuType.BILLS));
//        list.add(new MenuItem(R.drawable.order, "Products", MenuType.PRODUCTS));
//        list.add(new MenuItem(R.drawable.customer, "Clients", MenuType.CLIENTS));
        list.add(new MenuItem(R.drawable.booking, "Booking", MenuType.BOOKING));
//        list.add(new MenuItem(R.drawable.report, "Reports", MenuType.REPORTS));

        return list;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }
}