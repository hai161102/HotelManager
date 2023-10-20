package com.haiprj.apps.hotelmanager.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public class PopupCustom<T extends ViewDataBinding> {

    public T binding;
    public PopupWindow popupWindow;
    private final Context context;



    @SuppressLint("ClickableViewAccessibility")
    public PopupCustom(Context context, int layoutId) {
        this.context = context;
        this.binding  = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, null, false);
        this.binding.getRoot().measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = this.binding.getRoot().getMeasuredHeight();
        this.popupWindow = new PopupWindow(this.binding.getRoot(), width, height, true);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setElevation(20);
//        this.binding.getRoot().setOnTouchListener((v, event) -> {
//            this.dismiss();
//            return true;
//        });


    }

    public void show(View anchor) {
        if (this.popupWindow.isShowing()) return;
        this.popupWindow.showAsDropDown(anchor);
    }

    public void dismiss() {
        popupWindow.dismiss();
    }


}