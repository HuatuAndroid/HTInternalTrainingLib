package com.example.module_employees_world.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author liuzhe
 * @date 2019/3/28
 *
 * 重写ViewPager触屏操作，修正了系统ViewPager与Activity触摸屏事件冲突
 */
public class JSViewPager extends ViewPager {

    public JSViewPager(Context context) {
        super(context);
    }

    public JSViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        return false;
    }
}