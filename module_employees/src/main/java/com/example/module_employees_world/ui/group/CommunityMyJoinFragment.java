package com.example.module_employees_world.ui.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.adapter.CommunityMyPartAdapter;
import com.example.module_employees_world.bean.MyPartListBean;
import com.example.module_employees_world.contranct.CommunityMyJoinContranct;
import com.example.module_employees_world.presenter.CommunityMyJoinPresenter;
import com.example.module_employees_world.ui.PostsDetailActivity;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.wb.baselib.base.fragment.MvpFragment;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.rxbus.taskBean.RxBus;
import com.wb.rxbus.taskBean.RxMessageBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 我的小组-我的参与
 */
public class CommunityMyJoinFragment extends MvpFragment<CommunityMyJoinPresenter> implements CommunityMyJoinContranct.CommunityMyJoinView {

    private MultipleStatusView multipleStatusView;
    private SmartRefreshLayout smartRefreshLayout;
    private ListView listView;
    private CommunityMyPartAdapter mAdapter;
    private int page = 1;
    private List<MyPartListBean> myPartListBeans;

    @Override
    protected CommunityMyJoinPresenter onCreatePresenter() {
        return new CommunityMyJoinPresenter(this);
    }

    @Override
    public boolean isLazyFragment() {
        return true;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_fragment_myjoin);
        multipleStatusView = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        listView = getViewById(R.id.p_lv);
        myPartListBeans = new ArrayList<>();
        mAdapter = new CommunityMyPartAdapter(getActivity(), myPartListBeans);
        listView.setAdapter(mAdapter);
        RefreshUtils.getInstance(smartRefreshLayout, getActivity()).defaultRefreSh();
        multipleStatusView.showLoading();
        mPresenter.getMyPartData(page);
        RxBus.getIntanceBus().registerRxBus(RxMessageBean.class, new Consumer<RxMessageBean>() {
            @Override
            public void accept(RxMessageBean rxMessageBean) throws Exception {
                if (rxMessageBean.getMessageType() == 852) {
                    page = 1;
                    mPresenter.getMyPartData(page);
                }
            }
        });
        setListener();
    }

    @Override
    protected void setListener() {
        super.setListener();
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleStatusView.showLoading();
                page = 1;
                mPresenter.getMyPartData(page);
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getMyPartData(page);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getMyPartData(page);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //参与详情
                Intent intent = new Intent(getActivity(), PostsDetailActivity.class);
                intent.putExtra("question_id",myPartListBeans.get(position).getId());
                startActivity(intent);
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
            myPartListBeans.clear();
        }
        myPartListBeans.addAll((Collection<? extends MyPartListBean>) o);
        mAdapter.notifyDataSetChanged();
        multipleStatusView.showContent();
        page++;
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void isLoadMore(boolean isLoadMore) {
        RefreshUtils.getInstance(smartRefreshLayout, getActivity()).isLoadData(isLoadMore);
    }

}
