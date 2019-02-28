package com.zhiyun88.www.module_main.community.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * author:LIENLIN
 * date:2019/2/26
 */
public class MyGridLayoutManagerr extends GridLayoutManager {
    private boolean isScrollEnabled = true;

    public MyGridLayoutManagerr(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyGridLayoutManagerr(Context context, int spanCount) {
        super(context, spanCount);
    }

    public MyGridLayoutManagerr(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled &&super.canScrollVertically();
    }
}
