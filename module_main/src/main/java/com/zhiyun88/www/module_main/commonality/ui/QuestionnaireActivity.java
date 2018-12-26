package com.zhiyun88.www.module_main.commonality.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.bean.Result;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.wb.rxbus.taskBean.RxBus;
import com.wb.rxbus.taskBean.RxMessageBean;
import com.wb.rxbus.taskBean.RxTaskBean;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.call.LoginStatusCall;
import com.zhiyun88.www.module_main.commonality.bean.InfoBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.QuestionnaireContranct;
import com.zhiyun88.www.module_main.commonality.mvp.presenter.QuestionnairePresenter;
import com.zhiyun88.www.module_main.course.view.MyRatingBar;
import com.zhiyun88.www.module_main.hApp;

public class QuestionnaireActivity extends MvpActivity<QuestionnairePresenter> implements QuestionnaireContranct.QuestionnaireView,View.OnTouchListener{

    private TopBarView topBarView;
    private MultipleStatusView multipleStatusView;
    private InfoBean infoBean;
    private TextView commit;
    private MyRatingBar myRatingBar;
    private TextView course_name;
    private EditText merit;
    private EditText insufficient;
    private int ratingNum = 5;
    private int courseId=-1;
    @Override
    protected QuestionnairePresenter onCreatePresenter() {
        return new QuestionnairePresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_commonality_questiondes);
        multipleStatusView = getViewById(R.id.multiplestatusview);
        multipleStatusView.showContent();
        multipleStatusView.showLoading();
        topBarView = getViewById(R.id.topbarview);
        course_name = getViewById(R.id.questionnaire_course);
        myRatingBar = getViewById(R.id.ratingbar);
        merit = getViewById(R.id.questionnaire_merit);
        insufficient = getViewById(R.id.questionnaire_insufficient);
        commit = getViewById(R.id.questionnaire_commit);
        insufficient.setOnTouchListener(this);
        merit.setOnTouchListener(this);
        try {
            courseId=Integer.parseInt(getIntent().getData().getLastPathSegment());
//            String uid = getIntent().getData().getQueryParameter("uid");
//            String token=getIntent().getData().getQueryParameter("token");
//            courseId =Integer.parseInt(getIntent().getData().getQueryParameter("id"));
//            hApp.newInstance().toMainActivity(uid, token, new LoginStatusCall() {
//                @Override
//                public void LoginError(String msg, int code) {
//                    if(code==1040){
//                        mPresenter.getQuestionData(courseId);
//                    }else {
//                        ErrorData();
//                    }
//                }
//            });
            mPresenter.getQuestionData(courseId);
        }catch (Exception e){
            courseId= getIntent().getIntExtra("courseId", -1);
            mPresenter.getQuestionData(courseId);
        }
    }
    @Override
    protected void setListener() {
        topBarView.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == TopBarView.ACTION_LEFT_BUTTON) {
                    finish();
                }
            }
        });
        myRatingBar.setOnRatingChangeListener(new MyRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                ratingNum = (int) ratingCount;
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int basis_id = infoBean.getBasis_id();
                int chapter_id = infoBean.getChapter_id();
                String nice = merit.getText().toString().trim();
                String negative = insufficient.getText().toString().trim();
                mPresenter.setQuestionData(basis_id,chapter_id ,ratingNum ,nice ,negative );
                showLoadV("提交中...");
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        topBarView.getCenterTextView().setText("问卷调查");
    }

    @Override
    public void showErrorMsg(String msg) {
        showShortToast(msg);
    }

    @Override
    public void showLoadV(String msg) {
        showLoadDiaLog(msg);
    }

    @Override
    public void closeLoadV() {
        hidLoadDiaLog();
    }

    @Override
    public void SuccessData(Object o) {
        infoBean = (InfoBean) o;
        topBarView.getCenterTextView().setText(infoBean.getBasis_title());
        course_name.setText(infoBean.getChapter_title()+"  "+infoBean.getTeacher());
        multipleStatusView.showContent();
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }
    @Override
    public void setSuccess(Result result) {
        closeLoadV();
        showErrorMsg(result.getMsg());

        if (result.getStatus() == 200) {
            RxBus.getIntanceBus().post(new RxTaskBean(1));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getIntanceBus().unSubscribe(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (((view.getId() == R.id.questionnaire_insufficient && canVerticalScroll(insufficient)))||(((view.getId() == R.id.questionnaire_merit && canVerticalScroll(merit))))) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return false;
    }
    /**
     * EditText竖直方向是否可以滚动
     * @param editText  需要判断的EditText
     * @return  true：可以滚动   false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if(scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

    @Override
    public void ShowLoadView() {
        multipleStatusView.showLoading();
    }

    @Override
    public void NoNetWork() {
            multipleStatusView.showNoNetwork();
    }

    @Override
    public void NoData() {
        multipleStatusView.showEmpty();
    }

    @Override
    public void ErrorData() {
        multipleStatusView.showError();
    }
}
