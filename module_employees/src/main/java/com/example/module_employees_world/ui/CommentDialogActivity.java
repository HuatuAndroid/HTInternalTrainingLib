package com.example.module_employees_world.ui;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.module_employees_world.R;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.utils.StatusBarUtil;

/**阿薩法
 * author:LIENLIN
 * date:2019/3/20
 * 评论窗口
 */
public class CommentDialogActivity extends MvpActivity {

    private ImageView ivEditArea;
    private LinearLayout llRoot;
    private int initHeight;
    private int screenHeight;

    @Override
    protected BasePreaenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //关闭基类状态栏设置
        setStatusBarEnable(false);
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusLayout(this, Color.parseColor("#007AFF"));
        StatusBarUtil.StatusBarDarkMode(this, StatusBarUtil.StatusBarLightMode(this));

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comment_dialog);
        ivEditArea = findViewById(R.id.iv_edit_area);
        llRoot = findViewById(R.id.ll_edit_root);
        llRoot.post(new Runnable() {
            @Override
            public void run() {
                initHeight = llRoot.getHeight();
                screenHeight = CommentDialogActivity.this.getWindow().getDecorView().getRootView().getHeight();
            }
        });

        llRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                int heightDifference = screenHeight - (r.bottom - r.top);
                boolean isKeyboardShowing = heightDifference > screenHeight/3;
                // TODO: 2019/3/25
            }
        });

    }

    @Override
    protected void setListener() {
        ivEditArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int stateBarHeight = StatusBarUtil.getStateBar(CommentDialogActivity.this);
                int targetHight = screenHeight - stateBarHeight;
                ValueAnimator valueAnimator;
                if (llRoot.getHeight()>initHeight){
                    valueAnimator = ValueAnimator.ofInt(targetHight , initHeight);
                }else {
                    valueAnimator = ValueAnimator.ofInt(initHeight, targetHight);
                }
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int h =(Integer) animation.getAnimatedValue();
                        llRoot.getLayoutParams().height=h;
                        llRoot.requestLayout();
                    }
                });
                valueAnimator.setDuration(100);
                valueAnimator.start();
            }
        });


    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
