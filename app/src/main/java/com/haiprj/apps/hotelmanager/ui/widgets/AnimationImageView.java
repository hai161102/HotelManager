package com.haiprj.apps.hotelmanager.ui.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.haiprj.apps.hotelmanager.interfaces.OnViewClicked;
import com.haiprj.apps.hotelmanager.utils.AnimationUtils;

public class AnimationImageView extends androidx.appcompat.widget.AppCompatImageView implements View.OnTouchListener {
    public float duration = 0.1f * 1000f;
    private boolean isClick = false;
    private OnViewClicked viewClicked;

    public AnimationImageView(@NonNull Context context) {
        super(context);
        init();
    }

    public AnimationImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public AnimationImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        this.setOnTouchListener(this);
        viewClicked = view -> {
            AnimationUtils.scale(view, 0.9f, this.duration, new AnimationUtils.AnimationListener() {
                @Override
                public void end() {
                }
            });
        };
    }
//    @Override
//    public void setOnClickListener(@Nullable OnClickListener l) {
//        AnimationUtils.scale(this, 0.9f, this.duration, new AnimationUtils.AnimationListener() {
//            @Override
//            public void end() {
//                AnimationImageView.super.setOnClickListener(l);
//            }
//        });
//    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.viewClicked.onClick(v);
        return true;
    }



}
