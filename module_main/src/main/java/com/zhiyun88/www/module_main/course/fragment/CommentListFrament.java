package com.zhiyun88.www.module_main.course.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.fragment.LazyFragment;
import com.wb.baselib.base.fragment.MvpFragment;
import com.wb.baselib.log.LogTools;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.MyListView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.adapter.CommentListAdapter;
import com.zhiyun88.www.module_main.course.bean.CommentListBean;
import com.zhiyun88.www.module_main.course.bean.CommentListData;
import com.zhiyun88.www.module_main.course.mvp.contranct.CommentListContranct;
import com.zhiyun88.www.module_main.course.mvp.presenter.CommentListPresenter;
import com.zhiyun88.www.module_main.course.view.NestedScrollListView;

import java.util.ArrayList;
import java.util.List;

public class CommentListFrament extends MvpFragment<CommentListPresenter> implements CommentListContranct.CommentListView {
    private SmartRefreshLayout refreshLayout;
    private NestedScrollListView mListView;
    private CommentListAdapter mAdapter;
    private List<CommentListData> commentListDataLists;
    private int currentPage=1;
    private String id;
    private NestedScrollView empty_view;
    @Override
    public boolean isLazyFragment() {
        return true;
    }
    public static CommentListFrament newInstance(String id){
        CommentListFrament commentListFrament=new CommentListFrament();
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        commentListFrament.setArguments(bundle);
        return commentListFrament;
    }
    @Override
    protected CommentListPresenter onCreatePresenter() {
        return new CommentListPresenter(this);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_courseinfo_commentlist);
        id=getArguments().getString("id");
        refreshLayout=getViewById(R.id.refreshLayout);
        empty_view=getViewById(R.id.empty_view);
        RefreshUtils.getInstance(refreshLayout,getActivity()).defaultRefreSh();
        commentListDataLists=new ArrayList<>();
        mListView=getViewById(R.id.p_mlv);
        mListView.setDivider(null);
        refreshLayout.setEnableRefresh(false);
        mAdapter=new CommentListAdapter(commentListDataLists,getActivity());
        mListView.setAdapter(mAdapter);
        mPresenter.getCommentListData(id,currentPage);
        setListener();
    }

    @Override
    public void ShowLoadView() {
    }

    @Override
    public void NoNetWork() {
    }

    @Override
    public void NoData() {
        empty_view.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void ErrorData() {
    }

    @Override
    public void showErrorMsg(String msg) {
        showShortToast(msg);
    }

    @Override
    public void showLoadV(String msg) {
        showLongToast(msg);
    }

    @Override
    public void closeLoadV() {

    }

    @Override
    public void SuccessData(Object o) {
        CommentListBean commentListBean= (CommentListBean) o;
        if(currentPage==1){
            commentListDataLists.clear();
        }
        commentListDataLists.addAll(commentListBean.getList());
        LogTools.e("commentListDataLists"+commentListDataLists.size()+"----");
        mAdapter.notifyDataSetChanged();
        currentPage++;
        refreshLayout.setVisibility(View.VISIBLE);
        empty_view.setVisibility(View.GONE);
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void isLoadMore(boolean b) {
        RefreshUtils.getInstance(refreshLayout,getActivity()).isLoadData(b);
    }

    @Override
    protected void setListener() {
        super.setListener();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage=1;
                mPresenter.getCommentListData(id,currentPage);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getCommentListData(id,currentPage);
            }
        });
    }
}
