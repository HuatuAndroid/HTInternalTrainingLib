package com.zhiyun88.www.module_main.community.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
import com.zhiyun88.www.module_main.DialogUtils;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.adapter.CommunityMyGroupAdapter;
import com.zhiyun88.www.module_main.community.bean.MyItemListBean;
import com.zhiyun88.www.module_main.community.config.CommunityConfig;
import com.zhiyun88.www.module_main.community.mvp.contranct.CommunityMyGroupContranct;
import com.zhiyun88.www.module_main.community.mvp.presenter.CommunityMyGroupPresenter;
import com.zhiyun88.www.module_main.community.ui.GroupDetailsActivity;
import com.zhiyun88.www.module_main.dotesting.ui.CommonTestActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.functions.Consumer;

public class CommunityMyGroupFragment extends MvpFragment<CommunityMyGroupPresenter> implements CommunityMyGroupContranct.CommunityMyGroupView {


    private MultipleStatusView multipleStatusView;
    private SmartRefreshLayout smartRefreshLayout;
    private ListView listView;
    private CommunityMyGroupAdapter mAdapter;
    private int page = 1;
    private List<MyItemListBean> myItemListBeans;
    private int clickIndex;


    @Override
    public boolean isLazyFragment() {
        return true;
    }


    @Override
    protected CommunityMyGroupPresenter onCreatePresenter() {
        return new CommunityMyGroupPresenter(this);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_fragment_information);
        multipleStatusView = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        listView = getViewById(R.id.p_lv);
        myItemListBeans = new ArrayList<>();
        mAdapter = new CommunityMyGroupAdapter(getActivity(), myItemListBeans);
        listView.setAdapter(mAdapter);
        RefreshUtils.getInstance(smartRefreshLayout, getActivity()).defaultRefreSh();
        multipleStatusView.showLoading();
        mPresenter.getMyGroupData(page);
        RxBus.getIntanceBus().registerRxBus(RxMessageBean.class, new Consumer<RxMessageBean>() {
            @Override
            public void accept(RxMessageBean rxMessageBean) throws Exception {
                if (rxMessageBean.getMessageType() == 592) {
                    page = 1;
                    mPresenter.getMyGroupData(page);
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
                mPresenter.getMyGroupData(page);
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getMyGroupData(page);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getMyGroupData(page);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //小组详情
                Intent intent = new Intent(getActivity(), GroupDetailsActivity.class);
                intent.putExtra("groupId", myItemListBeans.get(position).getGroup_id());
                startActivity(intent);
            }
        });
        mAdapter.setOnItemOutListener(new CommunityConfig.OnItemOutListener() {
            @Override
            public void setOutItem(final String id, int position) {
                clickIndex = position;
                DialogUtils dialogUtils = new DialogUtils(getActivity());
                dialogUtils.setTitle("提示")
                        .setContent("确定要退出小组?")
                        .setOnClickListenter(new DialogUtils.OnClickListener() {
                            @Override
                            public void setYesClickListener() {
                                mPresenter.setGroup(id, "0");
                            }

                            @Override
                            public void setNoClickListener() {

                            }
                        });
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
            myItemListBeans.clear();
        }
        myItemListBeans.addAll((Collection<? extends MyItemListBean>) o);
        mAdapter.notifyDataSetChanged();
        multipleStatusView.showContent();
        page++;
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void joinGroup() {
        page = 1;
        mPresenter.getMyGroupData(page);
        Log.e("joinGroup: ", myItemListBeans.get(clickIndex).getGroup_id());
        RxBus.getIntanceBus().post(new RxMessageBean(593,myItemListBeans.get(clickIndex).getGroup_id(),""));
    }

    @Override
    public void isLoadMore(boolean isLoadMore) {
        RefreshUtils.getInstance(smartRefreshLayout, getActivity()).isLoadData(isLoadMore);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getIntanceBus().unSubscribe(this);
    }
}
