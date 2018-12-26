package com.zhiyun88.www.module_main.train.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.train.adapter.TrainListAdapter;
import com.zhiyun88.www.module_main.train.bean.TrainListBean;
import com.zhiyun88.www.module_main.train.bean.TrainListData;
import com.zhiyun88.www.module_main.train.mvp.contranct.TrainListContranct;
import com.zhiyun88.www.module_main.train.mvp.presenter.TrainListPresenter;

import java.util.ArrayList;
import java.util.List;

public class TrainListActivity extends MvpActivity<TrainListPresenter> implements TrainListContranct.TrainListView {
    private MultipleStatusView multipleStatusView;
    private SmartRefreshLayout smartRefreshLayout;
    private ListView mListView;
    private List<TrainListData> trainListDataLists;
    private TrainListAdapter mAdapter;
    private TopBarView mTopView;
    private int page=1;
    @Override
    protected TrainListPresenter onCreatePresenter() {
        return new TrainListPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_train_trainlist_layout);
        multipleStatusView=getViewById(R.id.multiplestatusview);
        multipleStatusView.showContent();
        multipleStatusView.showLoading();
        mTopView=getViewById(R.id.train_tb);
        smartRefreshLayout=getViewById(R.id.refreshLayout);
        RefreshUtils.getInstance(smartRefreshLayout,this).defaultRefreSh();
        mListView=getViewById(R.id.p_lv);
        trainListDataLists=new ArrayList<>();
        mAdapter=new TrainListAdapter(trainListDataLists,this);
        mListView.setAdapter(mAdapter);
        mPresenter.getTrainListData("1",page);
    }

    @Override
    protected void setListener() {
        mTopView.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == TopBarView.ACTION_LEFT_BUTTON) {
                    finish();
                }
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getTrainListData("1",page);
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                mPresenter.getTrainListData("1",page);
            }
        });
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page=1;
                mPresenter.getTrainListData("1",page);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mTopView.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if(action==TopBarView.ACTION_LEFT_BUTTON){
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
    public void showErrorMsg(String msg) {
            showLongToast(msg);
    }

    @Override
    public void showLoadV(String msg) {

    }

    @Override
    public void closeLoadV() {

    }

    @Override
    public void SuccessData(Object o) {
        if(page==1){
            trainListDataLists.clear();
        }
        TrainListBean trainListBean= (TrainListBean) o;
        trainListDataLists.addAll(trainListBean.getList());
        mAdapter.notifyDataSetChanged();
        multipleStatusView.showContent();
        page++;
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void isLoadMore(boolean b) {
        RefreshUtils.getInstance(smartRefreshLayout,this).isLoadData(b);
    }

}
