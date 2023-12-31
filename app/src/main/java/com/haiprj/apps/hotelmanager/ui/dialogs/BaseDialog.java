package com.haiprj.apps.hotelmanager.ui.dialogs;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseDialog<T> extends AppCompatDialog {

    protected T binding;


    protected OnActionDialogCallback onActionDialogCallback;

    public BaseDialog(@NonNull Context context, OnActionDialogCallback onActionDialogCallback) {
        super(context);
        this.onActionDialogCallback = onActionDialogCallback;
    }

    public BaseDialog(@NonNull Context context, int themeResId, OnActionDialogCallback onActionDialogCallback) {
        super(context, themeResId);
        this.onActionDialogCallback = onActionDialogCallback;
    }

    public BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, OnActionDialogCallback onActionDialogCallback) {
        super(context, cancelable, cancelListener);
        this.onActionDialogCallback = onActionDialogCallback;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = (T) DataBindingUtil.inflate(LayoutInflater.from(getContext()),getLayoutId(), null, false);
        setContentView(((ViewDataBinding)binding).getRoot());
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setOnDismissListener(dialogInterface -> BaseDialog.this.onDismiss());
        setOnCancelListener(dialogInterface -> BaseDialog.this.onCancel());
        initView();
        addEvent();
    }

    protected abstract void addEvent();

    protected abstract void initView();

    protected abstract int getLayoutId();



    protected void onDismiss() {

    }

    protected void onCancel() {

    }

    public void showDialog(){
        try {
            show();
        } catch (Exception ignored){

        }
    }

    public interface OnActionDialogCallback{
        void callback(String key, Object... objects);
    }


}
