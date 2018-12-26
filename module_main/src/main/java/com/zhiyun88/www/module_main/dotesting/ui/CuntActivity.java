package com.zhiyun88.www.module_main.dotesting.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.utils.ToActivityUtil;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.dotesting.adapter.CuntAdapter;
import com.zhiyun88.www.module_main.dotesting.bean.CountBean;
import com.zhiyun88.www.module_main.dotesting.mvp.contranct.CuntContranct;
import com.zhiyun88.www.module_main.dotesting.mvp.presenter.CuntPresenter;
import com.zhiyun88.www.module_main.task.ui.TaskInfoActivity;

public class CuntActivity extends MvpActivity<CuntPresenter> implements CuntContranct.CuntView {
    private MultipleStatusView multiplestatusview;
    private ListView mListView;
    private TopBarView topBarView;
    private TextView all_jx_tv,error_jx_tv;
    private String reportId,testId,taskId,testName;
    @Override
    protected CuntPresenter onCreatePresenter() {
        return new CuntPresenter(this);
    }

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.dotest_cunt_layout);
        multiplestatusview=getViewById(R.id.multiplestatusview);
        reportId=getIntent().getStringExtra("reportId");
        taskId=getIntent().getStringExtra("taskId");
        testId=getIntent().getStringExtra("testId");
        testName=getIntent().getStringExtra("testName");
        mListView=getViewById(R.id.p_lv);
        topBarView=getViewById(R.id.cunt_tb);
        multiplestatusview.showContent();
        multiplestatusview.showLoading();
        all_jx_tv=getViewById(R.id.all_jx_tv);
        error_jx_tv=getViewById(R.id.error_jx_tv);
        mPresenter.getCuntData(reportId);
    }

    @Override
    protected void setListener() {
        all_jx_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToActivityUtil.newInsance().toNextActivity(CuntActivity.this, CommonTestActivity.class,new String[][]{{"testId",reportId},{"taskId",reportId},{"testType","3"},{"testName",testName}});
            }
        });
        error_jx_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToActivityUtil.newInsance().toNextActivity(CuntActivity.this, CommonTestActivity.class,new String[][]{{"testId",reportId},{"taskId",reportId},{"testType","4"},{"testName",testName}});
            }
        });
    }

    @Override
    protected void processLogic(Bundle bundle) {
        topBarView.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View view, int i, String s) {
                if(TopBarView.ACTION_LEFT_BUTTON==i){
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
    public void showErrorMsg(String s) {

    }

    @Override
    public void showLoadV(String s) {

    }

    @Override
    public void closeLoadV() {

    }

    @Override
    public void SuccessData(Object o) {
        mListView.setAdapter(new CuntAdapter((CountBean) o,CuntActivity.this));
        multiplestatusview.showContent();
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }
}
