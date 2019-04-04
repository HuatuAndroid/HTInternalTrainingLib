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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.ui.PostsDetailActivity;
import com.example.module_employees_world.utils.RxBusMessageBean;
import com.wb.rxbus.taskBean.RxBus;

/**
 * author:LIENLIN
 * date:2019/3/27
 */
public class PostsDetailPopw extends PopupWindow implements View.OnClickListener {

    private View mMenuView;
    private Activity context;
    private PostsDetailActivity.MyHandler myHandler;

    /**
     * @param commentType 帖子类型
     * @param adoptCode 当commentType=2时有用，采纳状态值
     * @param myHandler
     */
    public PostsDetailPopw(final Activity context, int commentType, int adoptCode, PostsDetailActivity.MyHandler myHandler) {
        super(context);
        this.context=context;
        this.myHandler=myHandler;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popw_posts_detail_layout, null);
        initView(mMenuView,commentType,adoptCode);

        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setContentView(mMenuView);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(context,1f);
            }
        });

    }

    private void initView(View mMenuView, int commentType, int adoptCode) {
        TextView tv_update_type= (TextView) mMenuView.findViewById(R.id.tv_update_type);
        TextView tv_del= (TextView) mMenuView.findViewById(R.id.tv_del);
        TextView tv_cancel= (TextView) mMenuView.findViewById(R.id.tv_cancel);
        TextView tv_edit= (TextView) mMenuView.findViewById(R.id.tv_edit);
        TextView tv_adopt= (TextView) mMenuView.findViewById(R.id.tv_adopt);
        TextView tv_invite= (TextView) mMenuView.findViewById(R.id.tv_invite);
        tv_update_type.setOnClickListener(this);
        tv_del.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_edit.setOnClickListener(this);
        tv_adopt.setOnClickListener(this);
        tv_invite.setOnClickListener(this);

        switch (commentType){
            case 1://交流
                tv_adopt.setVisibility(View.GONE);
                tv_invite.setVisibility(View.GONE);
                break;
            case 2://建议
                tv_invite.setVisibility(View.GONE);
                tv_adopt.setVisibility(View.VISIBLE);
                break;
            case 3://提问
                tv_adopt.setVisibility(View.GONE);
                tv_invite.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void myShow(){
        backgroundAlpha(context,0.6f);
        this.showAtLocation(context.getWindow().getDecorView().findViewById(android.R.id.content), Gravity.BOTTOM,0,0);
    }

    public void myDismiss(){
        dismiss();
        backgroundAlpha(context,1.0f);
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
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
            new PostsTypePopw(context,myHandler);
        }else if (i == R.id.tv_del){
//            RxBus.getIntanceBus().post(new RxBusMessageBean(RxBusMessageBean.MessageType.POST_107,null));
            Message message = new Message();
            message.what=RxBusMessageBean.MessageType.POST_107;
            myHandler.handleMessage(message);
            myDismiss();
        }else if (i == R.id.tv_cancel){
            myDismiss();
        }else if (i == R.id.tv_edit){
            myDismiss();
        }else if (i == R.id.tv_adopt){
//            RxBus.getIntanceBus().post(new RxBusMessageBean(RxBusMessageBean.MessageType.POST_104,null));
            Message message = new Message();
            message.what=RxBusMessageBean.MessageType.POST_104;
            myHandler.handleMessage(message);
            myDismiss();
        }else if (i == R.id.tv_invite){
//            RxBus.getIntanceBus().post(new RxBusMessageBean(RxBusMessageBean.MessageType.POST_105,null));
            Message message = new Message();
            message.what=RxBusMessageBean.MessageType.POST_105;
            myHandler.handleMessage(message);
            myDismiss();
        }
    }
}
