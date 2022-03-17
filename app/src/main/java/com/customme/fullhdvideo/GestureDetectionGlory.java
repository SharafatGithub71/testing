package com.customme.fullhdvideo;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class GestureDetectionGlory extends SimpleOnGestureListener {
    private static final int ACTION_FAKE = -13;
    public static final int MODE_DYNAMIC = 2;
    public static final int MODE_SOLID = 1;
    public static final int SWIPE_DOWN = 2;
    public static final int SWIPE_LEFT = 3;
    public static final int SWIPE_RIGHT = 4;
    public static final int SWIPE_UP = 1;
    private Activity context;
    private GestureDetector detector;
    private SimpleGestureListener listener;
    private int mode = 2;
    private boolean running = true;
    private int swipe_Max_Distance = 350;
    private int swipe_Min_Distance = 50;
    private int swipe_Min_Velocity = 100;
    private boolean tapIndicator = false;

    interface SimpleGestureListener {
        void onStart(Boolean bool);

        void onSwipe(int i);
    }

    public GestureDetectionGlory(Activity context, SimpleGestureListener sgl) {
        this.context = context;
        this.detector = new GestureDetector(context, this);
        this.listener = sgl;
    }

    public void onTouchEvent(MotionEvent event) {
        if (this.running) {
            boolean result = this.detector.onTouchEvent(event);
            if (this.mode == 1) {
                event.setAction(3);
            } else if (this.mode != 2) {
            } else {
                if (event.getAction() == ACTION_FAKE) {
                    event.setAction(1);
                } else if (result) {
                    event.setAction(3);
                } else if (this.tapIndicator) {
                    event.setAction(0);
                    this.tapIndicator = false;
                }
            }
        }
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float xDistance = Math.abs(e1.getX() - e2.getX());
        float yDistance = Math.abs(e1.getY() - e2.getY());
        if (xDistance > ((float) this.swipe_Max_Distance) || yDistance > ((float) this.swipe_Max_Distance)) {
            return false;
        }
        velocityX = Math.abs(velocityX);
        velocityY = Math.abs(velocityY);
        if (velocityX > ((float) this.swipe_Min_Velocity) && xDistance > ((float) this.swipe_Min_Distance)) {
            if (e1.getX() > e2.getX()) {
                this.listener.onSwipe(4);
            } else {
                this.listener.onSwipe(3);
            }
            return true;
        } else if (velocityY <= ((float) this.swipe_Min_Velocity) || yDistance <= ((float) this.swipe_Min_Distance)) {
            return false;
        } else {
            if (e1.getY() > e2.getY()) {
                this.listener.onSwipe(1);
            } else {
                this.listener.onSwipe(2);
            }
            return true;
        }
    }

    public boolean onSingleTapConfirmed(MotionEvent arg) {
        if (this.mode == 2) {
            arg.setAction(ACTION_FAKE);
            this.context.dispatchTouchEvent(arg);
        }
        return false;
    }
}
