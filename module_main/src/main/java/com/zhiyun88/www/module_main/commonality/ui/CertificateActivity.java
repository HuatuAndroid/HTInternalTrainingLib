package com.zhiyun88.www.module_main.commonality.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.adapter.CertificateAdapter;
import com.zhiyun88.www.module_main.commonality.bean.CertificateListBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.CertificateContranct;
import com.zhiyun88.www.module_main.commonality.mvp.presenter.CertificatePresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CertificateActivity extends MvpActivity<CertificatePresenter> implements CertificateContranct.CertificateView{

    private TopBarView topBarView;
    private SmartRefreshLayout smartRefreshLayout;
    private MultipleStatusView multipleStatusView;
    private int page = 1;
    private List<CertificateListBean> certificateListBeans;
    private CertificateAdapter certificateAdapter;

    @Override
    protected CertificatePresenter onCreatePresenter() {
        return new CertificatePresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity_credential);
        topBarView = getViewById(R.id.topbarview);
        multipleStatusView = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        RefreshUtils.getInstance(smartRefreshLayout,this ).defaultRefreSh();
        ListView listView = getViewById(R.id.p_lv);
        certificateListBeans = new ArrayList<>();
        certificateAdapter = new CertificateAdapter(this, certificateListBeans);
        listView.setAdapter(certificateAdapter);
        multipleStatusView.showLoading();
        topBarView.getCenterTextView().setText(R.string.main_my_certificate);
        mPresenter.getCertificateData(page);
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
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getCertificateData(page);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getCertificateData(page);
            }
        });
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleStatusView.showLoading();
                page=1;
                mPresenter.getCertificateData(page);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

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
        if (page == 1 ) {
            certificateListBeans.clear();
        }
        certificateListBeans.addAll((Collection<? extends CertificateListBean>) o);
        certificateAdapter.notifyDataSetChanged();
        multipleStatusView.showContent();
        page++;
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
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
    public void isLoadMore(boolean isLoadMore) {
        RefreshUtils.getInstance(smartRefreshLayout,this ).isLoadData(isLoadMore);
    }
}
