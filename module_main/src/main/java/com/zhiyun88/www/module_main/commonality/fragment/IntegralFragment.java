package com.zhiyun88.www.module_main.commonality.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;

import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.fragment.MvpFragment;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.adapter.RankingAdapter;
import com.zhiyun88.www.module_main.commonality.adapter.RecordAdapter;
import com.zhiyun88.www.module_main.commonality.bean.RankingListBean;
import com.zhiyun88.www.module_main.commonality.bean.RecordListBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.IntegralFragmentContranct;
import com.zhiyun88.www.module_main.commonality.mvp.presenter.IntegralFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

public class IntegralFragment extends MvpFragment<IntegralFragmentPresenter> implements IntegralFragmentContranct.IntegralFragmentView{

    private RecordAdapter recordAdapter;
    private RankingAdapter rankingAdapter;
    private int page = 1;
    private MultipleStatusView multipleStatusView;
    private SmartRefreshLayout smartRefreshLayout;
    private String id;
    private ArrayList<RecordListBean> recordBeans;
    private ArrayList<RankingListBean> rankingBeans;

    @Override
    protected IntegralFragmentPresenter onCreatePresenter() {
        return new IntegralFragmentPresenter(this);
    }

    @Override
    public boolean isLazyFragment() {
        return true;
    }

    public static Fragment newInstance(String id,int integral_type) {
        IntegralFragment integralFragment = new IntegralFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putInt("integral_type", integral_type);
        integralFragment.setArguments(bundle);
        return integralFragment;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_mu_resh_listview);
        multipleStatusView = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        RefreshUtils.getInstance(smartRefreshLayout,getActivity()).defaultRefreSh();
        ListView listView = getViewById(R.id.p_lv);
        id = getArguments().getString("id", "");
        int integral_type = getArguments().getInt("integral_type", -1);

        if(integral_type == 0) {
            multipleStatusView.showLoading();
            mPresenter.getRecord(id,page);
            recordBeans = new ArrayList<>();
            recordAdapter = new RecordAdapter(getActivity(), recordBeans);
            listView.setAdapter(recordAdapter);
            setListener();
        }else if (integral_type == 1){
            smartRefreshLayout.setEnableLoadMore(false);
            smartRefreshLayout.setEnableRefresh(false);
            multipleStatusView.showLoading();
            mPresenter.getRanking(id);
            rankingBeans = new ArrayList<>();
            rankingAdapter = new RankingAdapter(getActivity(), rankingBeans);
            listView.setAdapter(rankingAdapter);
            multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    multipleStatusView.showLoading();
                    mPresenter.getRanking(id);
                }
            });
        }else {
            multipleStatusView.showError();
        }
    }
    @Override
    protected void setListener() {
        super.setListener();
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getRecord(id, page);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getRecord(id, page);
            }
        });
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleStatusView.showLoading();
                page = 1;
                mPresenter.getRecord(id, page);
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

    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void loadMore(boolean isLoadMore) {
        RefreshUtils.getInstance(smartRefreshLayout,getActivity()).isLoadData(isLoadMore);
    }

    @Override
    public void SuccessRecordData(List<RecordListBean> recordListBeans) {
        if (page == 1) {
            recordBeans.clear();
        }
        recordBeans.addAll(recordListBeans);
        multipleStatusView.showContent();
        recordAdapter.notifyDataSetChanged();
        page++;
    }

    @Override
    public void SuccessRankingData(List<RankingListBean> rankingListBeans) {
        rankingBeans.addAll(rankingListBeans);
        multipleStatusView.showContent();
        rankingAdapter.notifyDataSetChanged();
    }

    @Override
    public void isLoadMore(boolean isLoadMore) {
        RefreshUtils.getInstance(smartRefreshLayout,getActivity()).isLoadData(isLoadMore);
    }
}
