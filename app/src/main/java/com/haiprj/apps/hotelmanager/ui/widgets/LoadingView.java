package com.haiprj.apps.hotelmanager.ui.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.haiprj.apps.hotelmanager.R;

public class LoadingView extends FrameLayout {

    private ImageView loadingImage;
    private Handler handler;

    private final float FPS = 1000 / 30f;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            LoadingView.this.postInvalidate();
            if (LoadingView.this.handler != null) {
                LoadingView.this.handler.postDelayed(this, (long) LoadingView.this.FPS);
            }
        }
    };

    public LoadingView(Context context) {
        super(context);
        this.init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init();
    }

    private void init() {
        this.setBackgroundColor(this.getContext().getColor(R.color.black_30));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT,
                Gravity.CENTER
        );
        this.setLayoutParams(layoutParams);
        this.loadingImage = new ImageView(this.getContext());
        DisplayMetrics displaymetrics = this.getContext().getResources().getDisplayMetrics();
        LayoutParams params = new FrameLayout.LayoutParams((int) (displaymetrics.widthPixels / 6f), (int) (displaymetrics.widthPixels / 6f));
        params.gravity = Gravity.CENTER;
        this.loadingImage.setLayoutParams(params);

        this.addView(this.loadingImage);
    }

    public void setImage(int resId) {
        this.loadingImage.setImageResource(resId);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
        Log.d("TAG_LoadingView", "draw: " + this.loadingImage.getRotation());
        this.loadingImage.setRotation(this.loadingImage.getRotation() + this.FPS * 0.2f);
    }

    public void load(Handler handler) {
        this.handler = handler;
        this.handler.post(this.runnable);
    }

    public void end(Handler handler) {
        if (this.handler.equals(handler)) {
            this.handler.removeCallbacks(this.runnable);
        }
    }
}
