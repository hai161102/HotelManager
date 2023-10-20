package com.haiprj.apps.hotelmanager.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.ui.widgets.LoadingView;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected T binding;
    protected final float FPS = 1000 / 60f;

    protected Handler handler;
    protected Thread thread;
    protected LoadingView loadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this),this.getLayoutID(), null, false);
        this.setContentView(binding.getRoot());
        this.handler = new Handler();
        this.loadingView = new LoadingView(this);
//        this.loadingView.setBackgroundColor(this.getColor(R.color.black_30));
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT,
//                FrameLayout.LayoutParams.MATCH_PARENT,
//                Gravity.CENTER
//        );
//        this.loadingView.setLayoutParams(layoutParams);
        this.loadingView.setImage(R.drawable.loading);
        ((ViewGroup) this.binding.getRoot()).addView(loadingView);
        this.loadingView.setVisibility(View.GONE);
        this.initUI();
        this.addEvents();
    }


    protected abstract void initUI();
    protected abstract void addEvents();

    protected abstract int getLayoutID();

    public void showLoading() {
        this.loadingView.setVisibility(View.VISIBLE);
        this.loadingView.load(this.handler);
    }

    public void hideLoading() {
        this.loadingView.end(this.handler);
        this.loadingView.setVisibility(View.GONE);
    }


    protected void scaleAnimation(View view, float scale) {
        view.setScaleX(scale);
        view.setScaleY(scale);
        handler.postDelayed(() -> {
            view.setScaleX(1f);
            view.setScaleY(1f);
        }, 100);
    }

    protected void setAnimationRotation(View v, float angle, boolean isMinus){
        float duration = 100;
        final float addAngle = isMinus ? - duration / FPS : duration / FPS;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                v.setRotation(v.getRotation() + addAngle);
                handler.postDelayed(this, (long) FPS);
                if (v.getRotation() >= angle) {
                    v.setRotation(angle);
                    handler.removeCallbacks(this);
                }
            }
        };

        this.handler.post(runnable);
    }
}
