package com.haiprj.apps.hotelmanager.ui.dialogs;

import android.content.Context;

import androidx.annotation.NonNull;

import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.DialogRenameBinding;

public class RenameDialog extends BaseDialog<DialogRenameBinding>{
    public RenameDialog(@NonNull Context context, OnActionDialogCallback onActionDialogCallback) {
        super(context, onActionDialogCallback);
    }

    @Override
    protected void addEvent() {
        this.binding.apply.setOnClickListener(v -> {
            if (this.onActionDialogCallback != null)
                this.onActionDialogCallback.callback("apply", this.binding.hotelName.getText().toString());
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
        return R.layout.dialog_rename;
    }

    @Override
    public void showDialog() {
        super.showDialog();
        this.binding.hotelName.setText("");
    }
}
