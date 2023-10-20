package com.haiprj.apps.hotelmanager.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.haiprj.apps.hotelmanager.utils.AnimationUtils;

public class AnimationTextView extends androidx.appcompat.widget.AppCompatTextView {

    public float duration = 0.1f;

    public AnimationTextView(@NonNull Context context) {
        super(context);
    }

    public AnimationTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimationTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        AnimationUtils.scale(this, 0.9f, this.duration, new AnimationUtils.AnimationListener() {
            @Override
            public void end() {
                AnimationTextView.super.setOnClickListener(l);
            }
        });
    }
}
