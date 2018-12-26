package com.zhiyun88.www.module_main.commonality.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.FeedbackContranct;
import com.zhiyun88.www.module_main.commonality.mvp.presenter.FeedbackPresenter;

public class FeedBackActivity extends MvpActivity<FeedbackPresenter> implements FeedbackContranct.FeedbackView{

    private TextView commit;
    private TopBarView topBarView;
    private EditText content;

    @Override
    protected FeedbackPresenter onCreatePresenter() {
        return new FeedbackPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity_feedback);
        topBarView = getViewById(R.id.topbarview);
        content = getViewById(R.id.feedback_content);
        commit = getViewById(R.id.feedback_commit);
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
        content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                content.setHint("");
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = content.getText().toString().trim();
                if (msg.equals("")) {
                    showShortToast(getString(R.string.main_content_cannot_be_empty));
                }else {
                    mPresenter.getFeedback(msg);
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        topBarView.getCenterTextView().setText(R.string.main_problem_feedback);
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
        String result = (String) o;
        showShortToast(result);
        finish();
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }
}
