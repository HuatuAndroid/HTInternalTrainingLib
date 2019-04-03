package com.example.module_employees_world.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.ui.PostsDetailActivity;
import com.example.module_employees_world.utils.RxBusMessageBean;

/**
 * author:LIENLIN
 * date:2019/3/27
 */
public class PostsTypePopw extends PopupWindow implements View.OnClickListener {

    private View mMenuView;
    private Activity context;
    private PostsDetailActivity.MyHandler myHandler;

    /**
     * @param context
     * @param myHandler
     */
    public PostsTypePopw(final Activity context, PostsDetailActivity.MyHandler myHandler) {
        super(context);
        this.context=context;
        this.myHandler=myHandler;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popw_posts_type_layout, null);
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
        TextView tv_communication= (TextView) mMenuView.findViewById(R.id.tv_communication);
        TextView tv_answers= (TextView) mMenuView.findViewById(R.id.tv_answers);
        TextView tv_question= (TextView) mMenuView.findViewById(R.id.tv_question);
        TextView tv_cancel= (TextView) mMenuView.findViewById(R.id.tv_cancel);
        tv_communication.setOnClickListener(this);
        tv_answers.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_question.setOnClickListener(this);
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
        if (i == R.id.tv_communication) {
            Message message = new Message();
            message.what= RxBusMessageBean.MessageType.POST_113;
            message.arg1=1;
            myHandler.handleMessage(message);
            myDismiss();
        }else if (i == R.id.tv_answers){
            Message message = new Message();
            message.what= RxBusMessageBean.MessageType.POST_113;
            message.arg1=2;
            myHandler.handleMessage(message);
            myDismiss();
        }else if (i == R.id.tv_question){
            Message message = new Message();
            message.what= RxBusMessageBean.MessageType.POST_113;
            message.arg1=3;
            myHandler.handleMessage(message);
            myDismiss();
        }else if (i == R.id.tv_cancel){
            myDismiss();
        }
    }
}
