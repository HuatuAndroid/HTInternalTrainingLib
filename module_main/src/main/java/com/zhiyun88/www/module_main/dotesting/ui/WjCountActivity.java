package com.zhiyun88.www.module_main.dotesting.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.dotesting.bean.WjBean;
import com.zhiyun88.www.module_main.dotesting.mvp.contranct.WjCountContranct;
import com.zhiyun88.www.module_main.dotesting.mvp.presenter.WjCountPresenter;

public class WjCountActivity  extends MvpActivity<WjCountPresenter> implements WjCountContranct.WjCountView{
    private TopBarView wj_tb;
    private TextView title_tv;
    private TextView tvPoint;
    private String reportId;
    private MultipleStatusView multipleStatusView;
    @Override
    protected WjCountPresenter onCreatePresenter() {
        return new WjCountPresenter(this);
    }

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.main_wjcount_layout);
        multipleStatusView=getViewById(R.id.multiplestatusview);
        multipleStatusView.showContent();
        multipleStatusView.showLoading();
        reportId=getIntent().getStringExtra("reportId");
        wj_tb=getViewById(R.id.wj_tb);
        title_tv=getViewById(R.id.title_tv);
        tvPoint = getViewById(R.id.tvPoint);
        mPresenter.getWjCountData(reportId);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle bundle) {
            wj_tb.setListener(new TopBarView.OnTitleBarListener() {
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

    @Override
    public void showErrorMsg(String s) {
        showLongToast(s);
    }

    @Override
    public void showLoadV(String s) {

    }

    @Override
    public void closeLoadV() {

    }

    @Override
    public void SuccessData(Object o) {
        WjBean wjBean= (WjBean) o;
        title_tv.setText(wjBean.getV());
        tvPoint.setText(wjBean.getPoint());
        multipleStatusView.showContent();
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }
}
