package com.example.module_employees_world.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.adapter.CommunityDiscussAdapter;
import com.example.module_employees_world.bean.DiscussListBean;
import com.example.module_employees_world.contranct.CommunityDiscussContranct;
import com.example.module_employees_world.presenter.CommunityDiscussPresenter;
import com.example.module_employees_world.ui.PostsDetailActivity;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.wb.baselib.base.fragment.MvpFragment;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.MyListView;
import com.wb.rxbus.taskBean.RxBus;
import com.wb.rxbus.taskBean.RxMessageBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 员工天地/小组详情-热门、最新列表
 */
public class CommunityDiscussFragment extends MvpFragment<CommunityDiscussPresenter> implements CommunityDiscussContranct.CommunityDiscussView {

    private MultipleStatusView multipleStatusView;
    private SmartRefreshLayout smartRefreshLayout;
    private MyListView listView;
    private CommunityDiscussAdapter discussAdapter;
    private String type;
    private String groupId;
    private int page = 1;
    private List<DiscussListBean> discussListBeans;
    private boolean isRefresh;
    private int index;

    public static Fragment newInstance(String type, String groupId, boolean isRefresh) {
        CommunityDiscussFragment discussFragment = new CommunityDiscussFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("groupId", groupId);
        bundle.putBoolean("isRefresh", isRefresh);
        discussFragment.setArguments(bundle);
        return discussFragment;
    }


    @Override
    public boolean isLazyFragment() {
        return true;
    }


    @Override
    protected CommunityDiscussPresenter onCreatePresenter() {
        return new CommunityDiscussPresenter(this);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_fragment_community);
        type = getArguments().getString("type");
        groupId = getArguments().getString("groupId","");
        isRefresh = getArguments().getBoolean("isRefresh");
        multipleStatusView = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        listView = getViewById(R.id.p_lv);
        discussListBeans = new ArrayList<>();
        discussAdapter = new CommunityDiscussAdapter(getActivity(),discussListBeans);
        listView.setAdapter(discussAdapter);
        RefreshUtils.getInstance(smartRefreshLayout,getActivity() ).defaultRefreSh();
        smartRefreshLayout.setEnableRefresh(isRefresh);
        smartRefreshLayout.setEnableLoadMore(true);
        multipleStatusView.showLoading();
        if ("".equals(groupId)) {
            mPresenter.getDiscussData(type,page);
        }else {
            mPresenter.getGroupTypeData(type,groupId,page);
        }
        RxBus.getIntanceBus().registerRxBus(RxMessageBean.class, new Consumer<RxMessageBean>() {
            @Override
            public void accept(RxMessageBean rxMessageBean) throws Exception {
                if (rxMessageBean.getMessageType() == 852) {
                    page = 1;
                    mPresenter.getGroupTypeData(type,groupId,page);
                }else if (rxMessageBean.getMessageType() == 486) {
                    String total = rxMessageBean.getMessage();
                    discussListBeans.get(index).setComment_count(total);
                    discussAdapter.notifyDataSetChanged();
                }else if (rxMessageBean.getMessageType() == 487) {
                    String likeCount = rxMessageBean.getMessage();
                    discussListBeans.get(index).setLike_count(likeCount);
                    discussAdapter.notifyDataSetChanged();
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
                if ("".equals(groupId)) {
                    mPresenter.getDiscussData(type,page);
                }else {
                    mPresenter.getGroupTypeData(type,groupId,page);
                }

            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                if ("".equals(groupId)) {
                    mPresenter.getDiscussData(type,page);
                }else {
                    mPresenter.getGroupTypeData(type,groupId,page);
                }
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if ("".equals(groupId)) {
                    mPresenter.getDiscussData(type,page);
                }else {
                    mPresenter.getGroupTypeData(type,groupId,page);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                //社区详情
                DiscussListBean discussListBean = discussListBeans.get(position);
                int readCount = Integer.parseInt(discussListBean.getRead_count())+1;
                discussListBean.setRead_count(readCount+"");
                discussAdapter.notifyDataSetChanged();
                Intent intent = new Intent(getActivity(), PostsDetailActivity.class);
                intent.putExtra("question_id", discussListBeans.get(position).getId());
//                intent.putExtra("h5", discussListBeans.get(position).getH5_detail());
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
            discussListBeans.clear();
        }
        discussListBeans.addAll((Collection<? extends DiscussListBean>) o);
        discussAdapter.notifyDataSetChanged();
        multipleStatusView.showContent();
        page++;
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void isLoadMore(boolean b) {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
////        smartRefreshLayout.setEnableLoadMore(b);
//        RefreshUtils.getInstance(smartRefreshLayout,getActivity() ).isLoadData(b);
    }
}
