package com.jungan.www.module_blackplay.viewsupport;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Created by wangkangfei on 17/8/15.
 */

public class PBDragFrameLayout extends FrameLayout {

    private int lastX = 0;
    private int lastY = 0;

    private int screenWidth = 10;
    private int screenHeight = 10;
    private int left, right, top, bottom;

    public PBDragFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScreenParam(context);
    }

    private void initScreenParam(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;
        screenHeight = metric.heightPixels;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                lastX = (int) event.getRawX();
//                lastY = (int) event.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int dx = (int) event.getRawX() - lastX;
//                int dy = (int) event.getRawY() - lastY;
//
//                left = getLeft() + dx;
//                top = getTop() + dy;
//                right = getRight() + dx;
//                bottom = getBottom() + dy;
//                if (left < 0) {
//                    left = 0;
//                    right = left + getWidth();
//                }
//                if (right > screenWidth) {
//                    right = screenWidth;
//                    left = right - getWidth();
//                }
//                if (top < 0) {
//                    top = 0;
//                    bottom = top + getHeight();
//                }
//                if (bottom > screenHeight) {
//                    bottom = screenHeight;
//                    top = screenHeight - getHeight();
//                }
//                layout(left, top, right, bottom);
//                lastX = (int) event.getRawX();
//                lastY = (int) event.getRawY();
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            default:
//                break;
//        }
//        return true;
//    }
}
