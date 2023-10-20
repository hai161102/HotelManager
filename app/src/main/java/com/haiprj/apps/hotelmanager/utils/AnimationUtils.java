package com.haiprj.apps.hotelmanager.utils;

import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;

public class AnimationUtils {

    public static void rotation(View view, float toDegree, float duration, @Nullable AnimationListener listener) {
        Handler handler = new Handler();
        float changeDegree = toDegree - view.getRotation();
        float step = (changeDegree / duration) * (1000 / 60f);
        final float[] current = {0f};
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                view.setRotation(view.getRotation() + step);
                if (changeDegree >= 0) {
                    if (view.getRotation() >= toDegree) {
                        view.setRotation(toDegree);
                        if (listener != null) listener.end();
                        handler.removeCallbacks(this);
                        return;
                    }
                }
                else {
                    if (view.getRotation() <= toDegree) {
                        view.setRotation(toDegree);
                        if (listener != null) listener.end();
                        handler.removeCallbacks(this);
                        return;
                    }
                }

                if (listener != null) {
                    current[0] += (1000 / 60f);
                    listener.progress(view.getRotation(), current[0]);
                }
                handler.postDelayed(this, (long) (1000 / 60f));
            }
        };
        if (listener != null) {
            listener.start();
        }
        handler.post(runnable);
    }

    public static void scale(View view, float scale, float duration, @Nullable AnimationListener listener) {
        Handler handler = new Handler();
        float currentScaleX = view.getScaleX();
        float currentScaleY = view.getScaleY();
        float changeScale = scale - view.getScaleX();
        float step = (changeScale / duration) * (1000 / 60f);
        final float[] current = {0f};
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                view.setScaleX(view.getScaleX() + step);
                view.setScaleY(view.getScaleY() + step);
                if (changeScale >= 0) {
                    if (view.getScaleX() >= scale) {
                        view.setScaleX(currentScaleX);
                        view.setScaleY(currentScaleY);
                        if (listener != null) listener.end();

                        handler.removeCallbacks(this);
                        return;
                    }
                }
                else {
                    if (view.getScaleX() <= scale) {
                        view.setScaleX(currentScaleX);
                        view.setScaleY(currentScaleY);
                        if (listener != null) listener.end();
                        handler.removeCallbacks(this);
                        return;
                    }
                }

                if (listener != null) {
                    current[0] += (1000 / 60f);
                    listener.progress(view.getScaleX(), current[0]);
                }
                handler.postDelayed(this, (long) (1000 / 60f));
            }
        };
        if (listener != null) {
            listener.start();
        }
        handler.post(runnable);
    }

    public static void timeOut(float duration, @Nullable AnimationListener listener) {
        Handler handler = new Handler();
        float deltaTime = 1000 / 60f;
        final float[] timeCount = {0};
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                assert listener != null;
                listener.progress(null, timeCount[0]);
                timeCount[0] += deltaTime;
                handler.postDelayed(this, (long) deltaTime);
                if (timeCount[0] >= duration) {
                    listener.end();
                    handler.removeCallbacks(this);
                }
            }
        };
        if (listener != null) {
            listener.start();
        }
        handler.post(runnable);
    }

    public interface AnimationListener {
        default void start(){}
        default void progress(Object target, float duration){}
        default void end(){}
    }
}
