package com.zhiyun88.www.module_main.sign.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.image.GlideManager;
import com.wb.baselib.prase.GsonUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.sign.bean.SignBean;
import com.zhiyun88.www.module_main.sign.mvp.contranct.UserSignContranct;
import com.zhiyun88.www.module_main.sign.mvp.presenter.UserSignPresenter;

public class UserSignActivity extends MvpActivity<UserSignPresenter> implements UserSignContranct.UserSignView {
    private TopBarView topBarView;
    private TextView    name_tv,name_s_tv,time_tv,teacher_tv,postsign_tv;
    private ImageView course_img;
    private SignBean signBean;
    private MultipleStatusView multiplestatusview;
    @Override
    protected UserSignPresenter onCreatePresenter() {
        return new UserSignPresenter(this);
    }

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.main_usersign_layout);
        String signJson=getIntent().getStringExtra("signJson");
        multiplestatusview=getViewById(R.id.multiplestatusview);
        multiplestatusview.showContent();
        multiplestatusview.showLoading();
        name_tv=getViewById(R.id.name_tv);
        name_s_tv=getViewById(R.id.name_s_tv);
        time_tv=getViewById(R.id.time_tv);
        teacher_tv=getViewById(R.id.teacher_tv);
        postsign_tv=getViewById(R.id.postsign_tv);
        course_img=getViewById(R.id.course_img);
        topBarView=getViewById(R.id.sign_tb);
        mPresenter.getUserSign(signJson.replace("http://test-px.huatu.com/api/app/attendanceCourse/id=",""));
    }
    @Override
    protected void setListener() {
        postsign_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.userSign(signBean.getList().getBasis_id(),signBean.getList().getChapter_id());
            }
        });
    }

    @Override
    protected void processLogic(Bundle bundle) {
        topBarView.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View view, int i, String s) {
                if(i==TopBarView.ACTION_LEFT_BUTTON){
                    finish();
                }
            }
        });
    }

    @Override
    public void ShowLoadView() {
        multiplestatusview.showLoading();
    }

    @Override
    public void NoNetWork() {
        multiplestatusview.showNoNetwork();
    }

    @Override
    public void NoData() {
        multiplestatusview.showEmpty();
    }

    @Override
    public void ErrorData() {
        multiplestatusview.showError();
    }

    @Override
    public void showErrorMsg(String s, final boolean isclose,boolean isSign) {
        View mView= LayoutInflater.from(UserSignActivity.this).inflate(R.layout.main_sign_msg_layout,null);
        final Dialog dialog=StyledDialog.buildCustom(mView,Gravity.CENTER).setCancelable(false,false).show();
        ImageView imageView=mView.findViewById(R.id.close_img);
        TextView msg_tv=mView.findViewById(R.id.msg_tv);
        ImageView sign_state_img=mView.findViewById(R.id.sign_state_img);
        msg_tv.setText(s);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isclose){
                    finish();
                }else {
                    dialog.dismiss();
                }
            }
        });
        if(isSign){
            //成功
            sign_state_img.setImageResource(R.drawable.sign_success_img);
            postsign_tv.setEnabled(false);
            postsign_tv.setText("已签到");
            postsign_tv.setBackgroundResource(R.drawable.main_sign_yes_bg);
        }else {
            sign_state_img.setImageResource(R.drawable.sign_fail_img);
        }
    }


    @Override
    public void showErrorMsg(String s) {

    }

    @Override
    public void showLoadV(String s) {
        showLoadDiaLog(s);
    }

    @Override
    public void closeLoadV() {
        hidLoadDiaLog();
    }

    @Override
    public void SuccessData(Object o) {
        signBean= (SignBean) o;
        try {
            GlideManager.getInstance().setCommonPhoto(course_img,R.drawable.course_image,UserSignActivity.this,signBean.getList().getCover(),false);
        }catch (Exception e){
            GlideManager.getInstance().setCommonPhoto(course_img,R.drawable.course_image,UserSignActivity.this,"http://www.baidu.com",false);
        }

        name_tv.setText(signBean.getList().getBasis_title());
        name_s_tv.setText(signBean.getList().getChapter_title());
        time_tv.setText(signBean.getList().getStart_end_date());
        teacher_tv.setText(signBean.getList().getTeacher());
        multiplestatusview.showContent();
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

}
