package com.zhiyun88.www.module_main.course.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by desin on 2017/1/11.
 */

public class MyExpandableListView extends ExpandableListView{

    public MyExpandableListView(Context context) {
        this(context,null);
    }

    public MyExpandableListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expand=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expand);
    }

}
