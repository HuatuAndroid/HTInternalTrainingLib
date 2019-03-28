package com.example.module_employees_world.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.module_employees_world.R;

/**
 * author:LIENLIN
 * date:2019/3/27
 */
public class PostsDetailPopw extends PopupWindow implements View.OnClickListener {

    private View mMenuView;
    private Activity context;
    /**
     * @param context
     */
    public PostsDetailPopw(final Activity context) {
        super(context);
        this.context=context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popw_posts_detail_layout, null);
        initView(mMenuView);

        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(context,0.6f);
        this.setContentView(mMenuView);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(context,1f);
            }
        });
        this.showAtLocation(context.getWindow().getDecorView().findViewById(android.R.id.content), Gravity.BOTTOM,0,0);
    }

    private void initView(View mMenuView) {
        TextView tv_update_type= (TextView) mMenuView.findViewById(R.id.tv_update_type);
        TextView tv_del= (TextView) mMenuView.findViewById(R.id.tv_del);
        TextView tv_cancel= (TextView) mMenuView.findViewById(R.id.tv_cancel);
        TextView tv_edit= (TextView) mMenuView.findViewById(R.id.tv_edit);
        tv_update_type.setOnClickListener(this);
        tv_del.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_edit.setOnClickListener(this);
    }


    public void myDismiss(){
        dismiss();
        backgroundAlpha(context,1.0f);
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha)
    {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_update_type) {
            myDismiss();
            new PostsTypePopw(context);
        }else if (i == R.id.tv_del){
            myDismiss();
        }else if (i == R.id.tv_cancel){
            myDismiss();
        }else if (i == R.id.tv_edit){
            myDismiss();
        }
    }
}
