package com.example.module_employees_world.ui.search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.adapter.SearchCommentAdapter;
import com.example.module_employees_world.bean.SearchCommenBean;
import com.example.module_employees_world.bean.SearchPostBean;
import com.example.module_employees_world.contranct.SearchContranct;
import com.example.module_employees_world.presenter.SearchPresenter;
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

public class CommentFragment extends MvpFragment<SearchPresenter> implements SearchContranct.SearchView {

    private MultipleStatusView multipleStatusView;
    private SmartRefreshLayout smartRefreshLayout;
    private MyListView listView;
    private String type;
    private String keyword;
    private int page = 1;
    private List<SearchCommenBean> searchCommenBeans;
    private boolean isRefresh;
    private int index;
    private SearchCommentAdapter searchCommentAdapter;

    public static Fragment newInstance(String type, String keyword, boolean isRefresh) {
        CommentFragment commentFragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("keyword", keyword);
        bundle.putBoolean("isRefresh", isRefresh);
        commentFragment.setArguments(bundle);
        return commentFragment;
    }


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_post_message);
        type = getArguments().getString("type");
        keyword = getArguments().getString("keyword");
        isRefresh = getArguments().getBoolean("isRefresh");
        multipleStatusView = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        listView = getViewById(R.id.p_lv);
        searchCommenBeans = new ArrayList<>();
        searchCommentAdapter = new SearchCommentAdapter(getActivity(),searchCommenBeans);
        listView.setAdapter(searchCommentAdapter);
        RefreshUtils.getInstance(smartRefreshLayout,getActivity() ).defaultRefreSh();
        smartRefreshLayout.setEnableRefresh(isRefresh);
        smartRefreshLayout.setEnableLoadMore(true);
        multipleStatusView.showLoading();
        mPresenter.getSearchCommnet(type,keyword,page);
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
                mPresenter.getSearchCommnet(type, keyword, page);
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getSearchCommnet(type, keyword, page);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getSearchCommnet(type, keyword, page);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                //社区详情
                Intent intent = new Intent(getActivity(), PostsDetailActivity.class);
                intent.putExtra("question_id", searchCommenBeans.get(position).getId());
                startActivity(intent);
            }
        });
    }


    @Override
    public void isLoadMore(boolean b) {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
    }

    @Override
    protected SearchPresenter onCreatePresenter() {
        return new SearchPresenter(this);
    }

    @Override
    public boolean isLazyFragment() {
        return true;
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
            searchCommenBeans.clear();
        }
        searchCommenBeans.addAll((Collection<? extends SearchCommenBean>) o);
        searchCommentAdapter.notifyDataSetChanged();
        multipleStatusView.showContent();
        page++;
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }
}
