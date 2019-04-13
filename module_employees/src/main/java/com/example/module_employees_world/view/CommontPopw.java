package com.example.module_employees_world.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.module_employees_world.R;

/**
 * author:LIENLIN
 * date:2019/1/7
 */
public class CommontPopw extends PopupWindow {

    private View mMenuView;
    private Activity context;
    /**
     * @param context
     */
    public CommontPopw(final Activity context, String content, View.OnClickListener sureLisener) {
        super(context);
        this.context=context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popw_commont_layout, null);
        TextView tv_canael= (TextView) mMenuView.findViewById(R.id.tv_canael);
        TextView tv_sure= (TextView) mMenuView.findViewById(R.id.tv_sure);
        TextView tv_text= (TextView) mMenuView.findViewById(R.id.tv_text);
//        LinearLayout comont_root= mMenuView.findViewById(R.id.rl_comont_root);
        //确定
        tv_text.setText(content);
        tv_sure.setOnClickListener(sureLisener);
        tv_canael.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDismiss();
            }
        });
//        comont_root.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(false);
        this.setOutsideTouchable(false);
        this.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(context,0.6f);
        this.setContentView(mMenuView);
        /*this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(context,1f);
            }
        });*/
        this.showAtLocation(context.getWindow().getDecorView().findViewById(android.R.id.content), Gravity.CENTER,0,0);
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
}
