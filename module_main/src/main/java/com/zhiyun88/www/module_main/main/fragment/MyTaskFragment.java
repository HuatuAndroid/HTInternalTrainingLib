package com.zhiyun88.www.module_main.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.zhiyun88.www.module_main.main.adapter.MyTaskAdapter;
import com.zhiyun88.www.module_main.main.bean.MyTaskListBean;
import com.zhiyun88.www.module_main.main.mvp.contranct.MyTaskContranct;
import com.zhiyun88.www.module_main.main.mvp.presenter.MyTaskPresenter;

import java.util.ArrayList;
import java.util.Collection;

public class MyTaskFragment extends MvpFragment<MyTaskPresenter> implements MyTaskContranct.MyTaskView{

    private ListView listView;
    private int task_type;
    private MyTaskAdapter myTaskAdapter;
    private SmartRefreshLayout smartRefreshLayout;
    private int page = 1;
    private MultipleStatusView multipleStatusView;
    private ArrayList<MyTaskListBean> myTaskListBeans;

    @Override
    protected MyTaskPresenter onCreatePresenter() {
        return new MyTaskPresenter(this);
    }

    @Override
    public boolean isLazyFragment() {
        return true;
    }
    public static MyTaskFragment newInstance(int type) {
        MyTaskFragment taskProgressFragment = new MyTaskFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("task_type", type);
        taskProgressFragment.setArguments(bundle);
        return taskProgressFragment;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_taskmain_layout);
        task_type = getArguments().getInt("task_type", -1);
        multipleStatusView = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        RefreshUtils.getInstance(smartRefreshLayout,getActivity()).defaultRefreSh();
        listView = getViewById(R.id.p_lv);

//        if (task_type == -1) {
//            multipleStatusView.showError();
//        }else {
//            multipleStatusView.showLoading();
//            mPresenter.getMyTaskData(task_type+"", "", page);
//        }
        myTaskListBeans = new ArrayList<>();
        myTaskAdapter = new MyTaskAdapter(getActivity(),myTaskListBeans);
        listView.setAdapter(myTaskAdapter);
        setListener();
        smartRefreshLayout.autoRefresh();
    }
    @Override
    protected void setListener() {
        super.setListener();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ToActivityUtil.newInsance().toNextActivity(getActivity(),MessageDetailActivity.class);
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getMyTaskData(task_type+"", "", page);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getMyTaskData(task_type+"", "", page);
            }
        });
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleStatusView.showLoading();
                page = 1;
                mPresenter.getMyTaskData(task_type+"", "", page);
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
        if (page == 1) {
            myTaskListBeans.clear();
        }
        myTaskListBeans.addAll((Collection<? extends MyTaskListBean>) o);
        multipleStatusView.showContent();
        myTaskAdapter.notifyDataSetChanged();
        page++;
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
    protected void onResumeLazy() {
        super.onResumeLazy();
        if(myTaskListBeans==null)
            return;
        page=1;
        mPresenter.getMyTaskData(task_type+"", "", page);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(myTaskListBeans==null){
                return;
            }
            smartRefreshLayout.autoRefresh();
        }
    }
}
