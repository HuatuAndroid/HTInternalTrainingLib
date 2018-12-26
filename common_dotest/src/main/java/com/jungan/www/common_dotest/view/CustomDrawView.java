package com.jungan.www.common_dotest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.jungan.www.common_dotest.R;


public class CustomDrawView extends RelativeLayout {
    private View mView,bt_v;
    private int bottomView;
    private int topView;
    private ScrollView top_src;
    private LayoutInflater  mInflater;
    private LinearLayout bottom_ll;
    private ImageButton handler;
    private SplitView splitView;
    public CustomDrawView(Context context) {
        this(context,null);
    }

    public CustomDrawView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }
    private void initView(Context context,AttributeSet att){
        mView= LayoutInflater.from(context).inflate(R.layout.content_main,this);
        top_src=mView.findViewById(R.id.top_src);
        bottom_ll=mView.findViewById(R.id.bottom_ll);
        handler=mView.findViewById(R.id.handler);
        splitView=mView.findViewById(R.id.spacer);
        bt_v=mView.findViewById(R.id.bt_v);
        TypedArray typedArray=context.obtainStyledAttributes(att,R.styleable.CustomDrawView);
        bottomView=typedArray.getResourceId(R.styleable.CustomDrawView_bottom_view,R.layout.layout_bottom_view);
        topView=typedArray.getResourceId(R.styleable.CustomDrawView_top_view,R.layout.layout_bottom_view);
        int top=typedArray.getInt(R.styleable.CustomDrawView_draw_top,100);
        int bottom=typedArray.getInt(R.styleable.CustomDrawView_draw_bottom,100);
        float retion=typedArray.getFloat(R.styleable.CustomDrawView_draw_retion,0.3f);
        int bg=typedArray.getResourceId(R.styleable.CustomDrawView_draw_img,R.drawable.problem_top_img);
        int fgx=typedArray.getColor(R.styleable.CustomDrawView_fgx_bg, Color.BLACK);
        typedArray.recycle();
        mInflater = LayoutInflater.from(getContext());
        top_src.addView(inflateView(bottomView));
        bottom_ll.addView(inflateView(topView));
        setDrawBottom(bottom);
        setDrawTop(top);
        setfgLineColor(fgx);
        setImageRes(bg);
        setspRetion(retion);
    }

    /**
     * 设置拖把的图片
     * @param imageRes
     */
    public void setImageRes(int imageRes){
        handler.setImageResource(imageRes);
    }

    /**
     * 设置分割线的颜色
     * @param lineC
     */
    public void setfgLineColor(int lineC){
        bt_v.setBackgroundColor(lineC);
    }

    /**
     * 通过id获取到view
     * @param layoutId
     * @return
     */
    private View inflateView(int layoutId) {
        return mInflater.inflate(layoutId, null);
    }

    /**
     * 设置距离顶部位置
     * @param fg
     */
    public void setspRetion(float fg){
        splitView.setSplitRatio(fg);
    }

    /**
     * 设置滑动到距离顶部的距离
     * @param top
     */
    public void setDrawTop(int top){
        splitView.setMinSplitTop(top);
    }

    /**
     * 设置距离底部的距离
     * @param bt
     */
    public void setDrawBottom(int bt){
        splitView.setMinSplitBottom(bt);
    }
}
