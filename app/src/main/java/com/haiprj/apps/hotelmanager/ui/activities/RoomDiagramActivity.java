package com.haiprj.apps.hotelmanager.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.haiprj.apps.hotelmanager.App;
import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ActivityRoomDiagramBinding;
import com.haiprj.apps.hotelmanager.databinding.PopupOptionsBinding;
import com.haiprj.apps.hotelmanager.models.Bills;
import com.haiprj.apps.hotelmanager.models.Booking;
import com.haiprj.apps.hotelmanager.models.Floor;
import com.haiprj.apps.hotelmanager.models.Room;
import com.haiprj.apps.hotelmanager.models.RoomState;
import com.haiprj.apps.hotelmanager.ui.adapters.FloorAdapter;
import com.haiprj.apps.hotelmanager.ui.dialogs.BaseDialog;
import com.haiprj.apps.hotelmanager.ui.dialogs.BookingDialog;
import com.haiprj.apps.hotelmanager.ui.dialogs.ListBookingDialog;
import com.haiprj.apps.hotelmanager.ui.dialogs.UpdateRoomDialog;
import com.haiprj.apps.hotelmanager.ui.dialogs.WarningDialog;
import com.haiprj.apps.hotelmanager.ui.holders.RoomHolder;
import com.haiprj.apps.hotelmanager.utils.AnimationUtils;
import com.haiprj.apps.hotelmanager.utils.PopupCustom;

import java.util.Date;
import java.util.List;

public class RoomDiagramActivity extends BaseActivity<ActivityRoomDiagramBinding> {

    protected FloorAdapter floorAdapter;

    protected PopupCustom<PopupOptionsBinding> popupOptions;
    private Room currentHolderRoom;
    private RoomHolder currentRoomHolder;
    public static void start(Context context) {
        Intent starter = new Intent(context, RoomDiagramActivity.class);
        context.startActivity(starter);
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void initUI() {
        popupOptions = new PopupCustom<>(this, R.layout.popup_options);

        floorAdapter = new FloorAdapter(this);

        this.binding.rcvFloor.setAdapter(floorAdapter);
        List<Floor> floors = App.database.getDataFloor().getAll(App.account.getStoreCode());
        floorAdapter.addData(floors);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void addEvents() {
        this.binding.back.setOnClickListener(v -> {
            AnimationUtils.scale(v, 1.2f, 100, new AnimationUtils.AnimationListener() {
                @Override
                public void end() {
                    RoomDiagramActivity.this.finish();
                }
            });

        });
        this.binding.refreshBtn.setOnClickListener(v -> {
            AnimationUtils.scale(v, 0.9f, 100, null);
            for (Floor floor : RoomDiagramActivity.this.floorAdapter.getList()) {
                RoomDiagramActivity.this.floorAdapter.notifyItemChanged(RoomDiagramActivity.this.floorAdapter.getList().indexOf(floor));
            }
        });
        this.binding.refreshView.setOnRefreshListener(() -> {
            Log.d("TAG_RoomDiagram", "onRefresh: ");
            AnimationUtils.timeOut(500, new AnimationUtils.AnimationListener() {
                @Override
                public void end() {
                    RoomDiagramActivity.this.binding.refreshView.setRefreshing(false);
                    RoomDiagramActivity.this.floorAdapter.notifyDataSetChanged();
                }
            });
        });
        this.floorAdapter.setOnRoomLongClick((v, holder,room) -> {
            this.currentHolderRoom = room;
            this.currentRoomHolder = holder;

            popupOptions.binding.checkIn.setFocusable(true);
            popupOptions.binding.checkIn.setClickable(true);

            popupOptions.binding.checkOut.setFocusable(true);
            popupOptions.binding.checkOut.setClickable(true);

            popupOptions.binding.checkIn.setAlpha(1f);
            popupOptions.binding.checkOut.setAlpha(1f);
            switch (currentHolderRoom.getRoomState()) {
                case EMPTY:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        popupOptions.binding.checkOut.setFocusable(false);
                        popupOptions.binding.checkOut.setClickable(false);
                    }
                    popupOptions.binding.checkOut.setAlpha(0.2f);
                    break;
                case NOT_EMPTY:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        popupOptions.binding.checkIn.setFocusable(false);
                        popupOptions.binding.checkIn.setClickable(false);
                    }
                    popupOptions.binding.checkIn.setAlpha(0.2f);
                    break;
            }
//            switch (currentHolderRoom.getRoomType()) {
//                case VIP:
//                    binding.vipFlag.setVisibility(View.VISIBLE);
//                    break;
//                case NORMAL:
//                case MEDIUM:
//                    binding.vipFlag.setVisibility(View.GONE);
//                    break;
//            }
            this.popupOptions.show(v);
        });
        this.popupOptions.popupWindow.setOnDismissListener(() -> {
            this.currentHolderRoom = null;
            this.currentRoomHolder = null;
        });
        popupOptions.binding.update.setOnClickListener(view -> {
            UpdateRoomDialog updateRoomDialog = new UpdateRoomDialog(
                    this,
                    currentHolderRoom,
                    (key, objects) -> {
                        if (key.equals("apply")) {
                            App.database.getDataRoom().update(currentHolderRoom);
                            this.currentRoomHolder.getAdapter().notifyItemChanged(this.currentRoomHolder.getPosition());
                        }
                    }
            );
            updateRoomDialog.showDialog();
            popupOptions.dismiss();
        });

        popupOptions.binding.delete.setOnClickListener(v1 -> {
            App.database.getDataRoom().delete(currentHolderRoom);
            this.currentRoomHolder.getAdapter().getList().remove(currentHolderRoom);
            this.currentRoomHolder.getAdapter().notifyItemRemoved(this.currentRoomHolder.getPosition());
            popupOptions.dismiss();
        });

        popupOptions.binding.checkIn.setOnClickListener(v1 -> {
            if (currentHolderRoom.getRoomState() == RoomState.NOT_EMPTY){
                WarningDialog.getInstance(this).showMessage("Room has client!");
                return;
            }

//            List<Booking> ls = App.database
//                    .getDataBooking()
//                    .getAllBetweenTime(
//                            currentTime - thirtyMinutes,
//                            currentTime + thirtyMinutes
//                    );
//            for (Booking l : ls) {
//                if (l.roomId == currentHolderRoom.id) {
//                    ListBookingDialog.getInstance(this, (key, objects) -> {
//                        if (key.equals("clickClient")) {
//                            Booking b = (Booking) objects[0];
//
//                        }
//                    }).showData(App.database.getDataBooking().getAllByRoomId(l.roomId));
//                }
//            }

            currentHolderRoom.setFrom(new Date().getTime());
            currentHolderRoom.setTo(currentHolderRoom.getFrom());
            currentHolderRoom.updateHours();
            currentHolderRoom.setRoomState(RoomState.NOT_EMPTY);
            App.database.getDataRoom().update(currentHolderRoom);
            this.currentRoomHolder.getAdapter().notifyItemChanged(this.currentRoomHolder.getPosition());
            popupOptions.dismiss();
        });

        popupOptions.binding.checkOut.setOnClickListener(v1 -> {
            if (currentHolderRoom.getRoomState() == RoomState.EMPTY){
                WarningDialog.getInstance(this).showMessage("Cannot checkout when no client use room!");
                return;
            }
            currentHolderRoom.setTo(new Date().getTime());
            currentHolderRoom.updateHours();

            Bills bills = new Bills();
            bills.roomId = currentHolderRoom.id;
            bills.time = currentHolderRoom.getTo();
            bills.totalMoney = currentHolderRoom.getPrice() * currentHolderRoom.getHours();
            App.database.getDataBills().insert(bills);
            currentHolderRoom.setFrom(0);
            currentHolderRoom.setTo(0);
            currentHolderRoom.updateHours();
            currentHolderRoom.setRoomState(RoomState.EMPTY);
            App.database.getDataRoom().update(currentHolderRoom);
            BillsActivity.start(this);
            for (Booking booking : App.database.getDataBooking().getAllByRoomId(currentHolderRoom.id)) {
                booking.deleteSelf();
            }
            this.floorAdapter.notifyDataSetChanged();
            popupOptions.dismiss();
        });

        popupOptions.binding.showBills.setOnClickListener(v1 -> {
            Bundle bundle = new Bundle();
            bundle.putString(BillsActivity.SHOW_TYPE, BillsActivity.BY_ROOM);
            bundle.putInt("roomId", currentHolderRoom.id);
            BillsActivity.start(this, bundle);
            popupOptions.dismiss();
        });

        popupOptions.binding.booking.setOnClickListener(v1 -> {
            if (currentHolderRoom.getRoomState() == RoomState.NOT_EMPTY) {
                WarningDialog.getInstance(this).showMessage(this.getString(R.string.this_room_has_client));
                return;
            }
            Room term = currentHolderRoom;
            RoomHolder termHolder = currentRoomHolder;
            BookingDialog.getInstance(this, (key, objects) -> {
                if (key.equals("apply")) {
                    Booking booking = new Booking();
                    booking.clientName = (String) objects[0];
                    booking.roomId = term.id;
                    booking.time = (long) objects[1];
                    App.database.getDataBooking().insert(booking);
                    termHolder.getAdapter().notifyItemChanged(termHolder.getAdapterPosition());
                }
            }).showDialog();
            popupOptions.dismiss();
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_room_diagram;
    }
}
