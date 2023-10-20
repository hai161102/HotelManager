package com.haiprj.apps.hotelmanager.ui.dialogs;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;

import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.DialogUpdateBinding;
import com.haiprj.apps.hotelmanager.models.Room;
import com.haiprj.apps.hotelmanager.models.RoomState;

public class UpdateRoomDialog extends BaseDialog<DialogUpdateBinding> {

    private Room currentRoom;

    public UpdateRoomDialog(@NonNull Context context, Room currentRoom, OnActionDialogCallback onActionDialogCallback) {
        super(context, onActionDialogCallback);
        this.currentRoom = currentRoom;
    }

    private void cancelAction() {
        this.cancel();
    }

    @Override
    protected void addEvent() {
        binding.apply.setOnClickListener(v -> {

            currentRoom.name = this.binding.editName.getText().toString();
            currentRoom.setNumberPeoples(Integer.parseInt(this.binding.editPeople.getText().toString()));
            currentRoom.setHours(Float.parseFloat(this.binding.editHours.getText().toString()));
            currentRoom.setRoomState(this.binding.checkboxEmpty.isChecked() ? RoomState.EMPTY : RoomState.NOT_EMPTY);
            if (this.onActionDialogCallback != null) {
                this.onActionDialogCallback.callback("apply", currentRoom);
                cancel();
            }
        });

        binding.cancel.setOnClickListener(v -> {
            this.cancelAction();
        });
        binding.editPeople.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0) return;
                int num = Integer.parseInt(s.toString());
                if (num < 0) {
                    num = 0;
                }
                UpdateRoomDialog.this.binding.checkboxEmpty.setChecked(num <= 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initView() {
        this.binding.editName.setText(currentRoom.name);
        this.binding.editPeople.setText(String.valueOf(currentRoom.getNumberPeoples()));
        this.binding.editHours.setText(String.valueOf(currentRoom.getHours()));
        this.binding.checkboxEmpty.setChecked(currentRoom.getRoomState() == RoomState.EMPTY);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_update;
    }
}
